package com.raizes.api.service;

import com.raizes.api.domain.*;

import com.raizes.api.domain.enums.*;
import com.raizes.api.dto.*;
import com.raizes.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import com.raizes.api.domain.enums.CanalPedido;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueUnidadeRepository estoqueRepository;
    private final PagamentoMockRepository pagamentoRepository;

    @Transactional
    public Pedido criarPedido(CriarPedidoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Unidade unidade = unidadeRepository.findById(request.unidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unidade não encontrada"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setUnidade(unidade);
        pedido.setCanalPedido(request.canalPedido());
        pedido.setStatus(StatusPedido.RECEBIDO);

        for (ItemPedidoRequest itemReq : request.itens()) {
            Produto produto = produtoRepository.findById(itemReq.produtoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + itemReq.produtoId()));

            EstoqueUnidade estoque = estoqueRepository
                    .findByUnidadeIdAndProdutoId(unidade.getId(), produto.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "Produto sem estoque cadastrado nesta unidade"));

            if (!estoque.temSaldo(itemReq.quantidade())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Estoque insuficiente para o produto: " + produto.getNome());
            }

            estoque.decrementarEstoque(itemReq.quantidade());
            estoqueRepository.save(estoque);

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setQuantidade(itemReq.quantidade());
            item.setPrecoUnitario(produto.getPrecoPadrao());
            pedido.getItens().add(item);
        }

        pedido.calcularTotal();
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    @Transactional
    public PagamentoMock processarPagamentoMock(Long pedidoId, PagamentoRequest request) {
        Pedido pedido = buscarPorId(pedidoId);

        if (Boolean.TRUE.equals(request.simularRecusa())) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_CONTENT,
                    "Pagamento recusado pela operadora mock."
            );
        }

        PagamentoMock pagamento = new PagamentoMock();
        pagamento.setPedido(pedido);
        pagamento.setFormaPagamento(request.formaPagamento());
        pagamento.confirmarPagamento("MOCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        pedido.setStatus(StatusPedido.EM_PREPARO);
        Integer pontosAtuais = pedido.getUsuario().getPontosFidelidade() != null ? pedido.getUsuario().getPontosFidelidade() : 0;
        pedido.getUsuario().setPontosFidelidade(pontosAtuais + 10);

        pedidoRepository.save(pedido);
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public Pedido atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = buscarPorId(pedidoId);
        pedido.setStatus(novoStatus);
        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> listarPedidos(CanalPedido canalPedido) {
        if (canalPedido != null) {
            return pedidoRepository.findByCanalPedido(canalPedido);
        }
        return pedidoRepository.findAll();
    }
}
