package br.com.pedrocpacheco.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyDto;
import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO.AuthCompanyResponseDTOBuilder;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCompanyResponseDTO execute(AuthCompanyDto dto) throws AuthenticationException {
    // * Ve se o usuario esta errado
    var company = repository.findByUsername(dto.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Username/password incorrect");
        });

    // * Ve se a senha esta errada
    var passwordMatches = passwordEncoder.matches(dto.password(), company.getPassword());
    if (!passwordMatches)
      throw new AuthenticationException("Username/password incorrect");

    // * Cria o token caso ambos acima certos
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
        .withSubject(company.getId().toString())
        .withClaim("roles", Arrays.asList("COMPANY"))
        .sign(algorithm);

    var authCompanyResponseDto = AuthCompanyResponseDTO.builder()
        .acess_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

    // * Retorna token criado
    return authCompanyResponseDto;
  }

}
