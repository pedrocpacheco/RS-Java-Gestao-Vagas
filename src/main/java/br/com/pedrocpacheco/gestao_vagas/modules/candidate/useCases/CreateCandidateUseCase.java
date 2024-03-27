package br.com.pedrocpacheco.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.exceptions.UserFoundException;
import br.com.pedrocpacheco.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.candidate.CandidateRepository;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository repository;

  public CandidateEntity execute(CandidateEntity entity) {
    repository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });
    return repository.save(entity);
  }

}
