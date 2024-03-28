package br.com.pedrocpacheco.gestao_vagas.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyDto;
import br.com.pedrocpacheco.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

  @Autowired
  private AuthCompanyUseCase authCompanyUseCase;

  @PostMapping("/company")
  public String create(@RequestBody AuthCompanyDto dto) throws AuthenticationException {
    return authCompanyUseCase.execute(dto);
  }

}
