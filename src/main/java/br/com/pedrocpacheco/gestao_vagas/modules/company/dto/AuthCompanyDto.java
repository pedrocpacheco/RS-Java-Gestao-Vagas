package br.com.pedrocpacheco.gestao_vagas.modules.company.dto;

// * DTO para o "login": so precisa username e senha
public record AuthCompanyDto(String username, String password) {
}
