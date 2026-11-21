package com.raizes.api.controller;

import com.raizes.api.domain.EstoqueUnidade;
import com.raizes.api.domain.Unidade;
import com.raizes.api.repository.EstoqueUnidadeRepository;
import com.raizes.api.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeRepository unidadeRepository;
    private final EstoqueUnidadeRepository estoqueRepository;

    @GetMapping
    public List<Unidade> listarTodas() {
        return unidadeRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Unidade criar(@RequestBody Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    @GetMapping("/{unidadeId}/estoque")
    public List<EstoqueUnidade> consultarEstoque(@PathVariable Long unidadeId) {
        return estoqueRepository.findByUnidadeId(unidadeId);
    }

    @PostMapping("/estoque")
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueUnidade cadastrarEstoque(@RequestBody EstoqueUnidade estoque) {
        return estoqueRepository.save(estoque);
    }
}