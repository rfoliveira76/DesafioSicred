package com.votacao.services;

import com.votacao.domain.Pauta;
import com.votacao.domain.Sessao;
import com.votacao.domain.Voto;
import com.votacao.domain.repository.PautaRepository;
import com.votacao.domain.repository.SessaoRepository;
import com.votacao.domain.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private VotoRepository votoRepository;

    public Pauta criarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public Sessao abrirSessao(Long pautaId, Integer minutos) {
        Optional<Pauta> pautaOpt = pautaRepository.findById(pautaId);
        if (pautaOpt.isPresent()) {
            Sessao sessao = new Sessao();
            sessao.setPauta(pautaOpt.get());
            sessao.setDataInicio(LocalDateTime.now());
            sessao.setDataFim(LocalDateTime.now().plusMinutes(minutos == null ? 1 : minutos));
            return sessaoRepository.save(sessao);
        }
        throw new IllegalArgumentException("Pauta não encontrada");
    }

    public void votar(Long pautaId, Long associadoId, Boolean voto) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));

        if (votoRepository.existsByPautaAndAssociadoId(pauta, associadoId)) {
            throw new IllegalArgumentException("Associado já votou nesta pauta");
        }

        Voto novoVoto = new Voto();
        novoVoto.setPauta(pauta);
        novoVoto.setAssociadoId(associadoId);
        novoVoto.setVoto(voto);
        votoRepository.save(novoVoto);
    }

    public String resultadoVotacao(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new IllegalArgumentException("Pauta não encontrada"));

        long votosSim = votoRepository.findAll()
                .stream().filter(v -> v.getPauta().equals(pauta) && v.getVoto()).count();
        long votosNao = votoRepository.findAll()
                .stream().filter(v -> v.getPauta().equals(pauta) && !v.getVoto()).count();

        return "Resultado: " + votosSim + " Sim, " + votosNao + " Não";
    }
}

