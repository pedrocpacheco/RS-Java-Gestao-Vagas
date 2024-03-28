package br.com.pedrocpacheco.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyDto;
import br.com.pedrocpacheco.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  // * Informa Usuario e Senha de Company e recebe o Token criado
  @PostMapping("/company")
  public ResponseEntity<Object> create(@RequestBody AuthCompanyDto dto) {
    try {
      var token = authCompanyUseCase.execute(dto);
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
