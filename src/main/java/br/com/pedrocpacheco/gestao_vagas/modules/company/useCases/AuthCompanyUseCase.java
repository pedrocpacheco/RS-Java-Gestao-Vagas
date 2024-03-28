package br.com.pedrocpacheco.gestao_vagas.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.pedrocpacheco.gestao_vagas.modules.company.dto.AuthCompanyDto;
import br.com.pedrocpacheco.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public String execute(AuthCompanyDto dto) throws AuthenticationException {
    var company = repository.findByUsername(dto.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Company not found");
        });

    var passwordMatches = passwordEncoder.matches(dto.password(), company.getPassword());
    if (!passwordMatches)
      throw new AuthenticationException();

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create().withIssuer("javagas")
        .withSubject(company.getId().toString())
        .sign(algorithm);

    return token;
  }

}
