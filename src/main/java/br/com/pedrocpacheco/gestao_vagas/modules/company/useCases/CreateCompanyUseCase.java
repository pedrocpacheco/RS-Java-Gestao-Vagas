package br.com.pedrocpacheco.gestao_vagas.modules.company.useCases;

import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.exceptions.UserFoundException;
import br.com.pedrocpacheco.gestao_vagas.modules.company.CompanyRepository;
import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.ComapanyEntity;

@Service
public class CreateCompanyUseCase {

  private CompanyRepository repository;

  public void execute(ComapanyEntity entity) {
    repository.findByUsernameOrEmail(entity.getUsername(), entity.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    repository.save(entity);
  }

}
