package br.com.pedrocpacheco.gestao_vagas.modules.company.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.JobEntity;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.JobRepository;
import br.com.pedrocpacheco.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @Autowired
  private JobRepository repository;

  @GetMapping
  public List<JobEntity> findAll() {
    return repository.findAll();
  }

  @PostMapping("/")
  public ResponseEntity<JobEntity> create(@Valid @RequestBody CreateJobDTO dto, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    var entity = JobEntity.builder()
        .id(UUID.fromString(companyId.toString()))
        .benefits(dto.benefits())
        .description(dto.description())
        .level(dto.level())
        .build();

    var result = createJobUseCase.execute(entity);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
