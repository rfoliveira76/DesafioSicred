package com.votacao.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pauta pauta;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public boolean isAberta() {
        return LocalDateTime.now().isBefore(dataFim);
    }
}

