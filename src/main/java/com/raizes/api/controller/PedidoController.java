package com.raizes.api.controller;

import com.raizes.api.domain.PagamentoMock;
import com.raizes.api.domain.Pedido;
import com.raizes.api.dto.AtualizarStatusRequest;
import com.raizes.api.dto.CriarPedidoRequest;
import com.raizes.api.dto.PagamentoRequest;
import com.raizes.api.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.raizes.api.domain.enums.CanalPedido;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido criar(@RequestBody @Valid CriarPedidoRequest request) {
        return pedidoService.criarPedido(request);
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping("/{id}/pagamento")
    public PagamentoMock pagar(@PathVariable Long id, @RequestBody @Valid PagamentoRequest request) {
        return pedidoService.processarPagamentoMock(id, request);
    }

    @PatchMapping("/{id}/status")
    public Pedido atualizarStatus(@PathVariable Long id, @RequestBody @Valid AtualizarStatusRequest request) {
        return pedidoService.atualizarStatus(id, request.novoStatus());
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> listar(@RequestParam(required = false) CanalPedido canalPedido) {
        return ResponseEntity.ok(pedidoService.listarPedidos(canalPedido));
    }
}
