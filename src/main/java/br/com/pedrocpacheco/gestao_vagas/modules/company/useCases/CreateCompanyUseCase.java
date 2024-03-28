package br.com.pedrocpacheco.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.exceptions.UserFoundException;
import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CompanyEntity execute(CompanyEntity entity) {
    repository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    // * Codifica a senha digitada na criacao da company
    var passwordEncripted = passwordEncoder.encode(entity.getPassword());
    entity.setPassword(passwordEncripted);

    // * Atualiza a senha para ela mesma codificada
    return repository.save(entity);
  }

}
