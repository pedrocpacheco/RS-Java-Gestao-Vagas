package br.com.pedrocpacheco.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.exceptions.UserFoundException;
import br.com.pedrocpacheco.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity entity) {
    repository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    var passwordEncripted = passwordEncoder.encode(entity.getPassword());
    entity.setPassword(passwordEncripted);

    return repository.save(entity);
  }

}
