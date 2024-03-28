package br.com.pedrocpacheco.gestao_vagas.modules.company.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.ComapanyEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.pedrocpacheco.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {

  @Autowired
  private CreateCompanyUseCase createCompanyUseCase;

  @Autowired
  private CompanyRepository repository;

  @GetMapping("")
  public List<ComapanyEntity> findAll() {
    return repository.findAll();
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody ComapanyEntity entity) {
    try {
      var result = createCompanyUseCase.execute(entity);
      return new ResponseEntity<>(result, HttpStatus.CREATED);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
