package com.votacao.domain.repository;

import com.votacao.domain.Pauta;
import com.votacao.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaAndAssociadoId(Pauta pauta, Long associadoId);
}
