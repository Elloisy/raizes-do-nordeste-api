package com.raizes.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estoque_unidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueUnidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unidade_id", nullable = false)
    private Unidade unidade;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    public Boolean temSaldo(Integer qtd) {
        return this.quantidade >= qtd;
    }

    public void decrementarEstoque(Integer qtd) {
        if (temSaldo(qtd)) {
            this.quantidade -= qtd;
        } else {
            throw new RuntimeException("Saldo insuficiente no estoque"); 
        }
    }
}
