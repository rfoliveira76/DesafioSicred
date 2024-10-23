package com.votacao.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
    @Data
    public class Voto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        private Pauta pauta;
        private Long associadoId;
        private Boolean voto; // true para Sim, false para Não
    }
