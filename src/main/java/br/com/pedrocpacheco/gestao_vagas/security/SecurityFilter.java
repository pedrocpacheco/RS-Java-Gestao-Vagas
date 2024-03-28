package br.com.pedrocpacheco.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pedrocpacheco.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    SecurityContextHolder.getContext().setAuthentication(null);

    // * Recebe o token enviado na requisicao
    String token = request.getHeader("Authorization");

    // * Valida o token
    if (token != null) {
      var subjectToken = jwtProvider.validateToken(token); // * Pega o token
      if (subjectToken.isEmpty()) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
      request.setAttribute("company_id", subjectToken); // * Seta o token como id
      var auth = new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
  }

}
