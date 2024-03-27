package br.com.pedrocpacheco.gestao_vagas.modules.company;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pedrocpacheco.gestao_vagas.modules.company.entities.ComapanyEntity;

public interface CompanyRepository extends JpaRepository<ComapanyEntity, UUID> {

  Optional<ComapanyEntity> findByUsernameOrEmail(String username, String email);

}
