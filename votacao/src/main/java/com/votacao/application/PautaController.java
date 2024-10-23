package com.votacao.application;

import com.votacao.domain.Pauta;
import com.votacao.domain.Sessao;
import com.votacao.services.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public Pauta criarPauta(@RequestBody Pauta pauta) {
        return pautaService.criarPauta(pauta);
    }

    @PostMapping("/{id}/abrir-sessao")
    public Sessao abrirSessao(@PathVariable Long id, @RequestParam(required = false) Integer minutos) {
        return pautaService.abrirSessao(id, minutos);
    }

    @PostMapping("/{id}/votar")
    public void votar(@PathVariable Long id, @RequestParam Long associadoId, @RequestParam Boolean voto) {
        pautaService.votar(id, associadoId, voto);
    }

    @GetMapping("/{id}/resultado")
    public String resultado(@PathVariable Long id) {
        return pautaService.resultadoVotacao(id);
    }
}
