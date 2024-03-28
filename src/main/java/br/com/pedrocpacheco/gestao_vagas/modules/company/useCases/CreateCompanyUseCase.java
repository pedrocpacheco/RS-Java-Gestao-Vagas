package br.com.pedrocpacheco.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.exceptions.UserFoundException;
import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.ComapanyEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

  @Autowired
  private CompanyRepository repository;

  public ComapanyEntity execute(ComapanyEntity entity) {
    repository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    return repository.save(entity);
  }

}
