package br.com.pedrocpacheco.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private SecurityFilter securityFilter;

  @Autowired
  private SecurityCandidateFilter securityCandidateFilter;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // * Define quais rotas estaram disponiveis sem autorização e as com
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/candidate/").permitAll()
              .requestMatchers("/company").permitAll()
              .requestMatchers("/company/").permitAll()
              .requestMatchers("/company/auth").permitAll()
              .requestMatchers("/candidate/auth").permitAll();
          auth.anyRequest().authenticated();
        })
        // * Define o filtro que sera usado
        .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
        .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEnconder() {
    return new BCryptPasswordEncoder();
  }

}
