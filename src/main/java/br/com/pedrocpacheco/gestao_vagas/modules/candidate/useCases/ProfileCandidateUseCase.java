package br.com.pedrocpacheco.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pedrocpacheco.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.pedrocpacheco.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository repository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = repository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found");
        });

    var candidateDTO = ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .id(candidate.getId())
        .name(candidate.getName())
        .build();

    return candidateDTO;
  }

}
