package com.raizes.api.controller;

import com.raizes.api.domain.Produto;
import com.raizes.api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listarAtivos() {
        return produtoRepository.findByAtivoTrue();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }
}
