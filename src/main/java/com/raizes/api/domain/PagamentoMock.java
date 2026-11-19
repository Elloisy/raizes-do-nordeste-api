package com.raizes.api.domain;

import com.raizes.api.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pagamento_mock")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoMock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPagamento statusPagamento;

    @Column(length = 100)
    private String transacaoExternalId;

    private LocalDateTime dataPagamento;

    public void confirmarPagamento(String transacaoId) {
        this.statusPagamento = StatusPagamento.APROVADO;
        this.transacaoExternalId = transacaoId;
        this.dataPagamento = LocalDateTime.now();
    }

    public void recusarPagamento() {
        this.statusPagamento = StatusPagamento.RECUSADO;
        this.dataPagamento = LocalDateTime.now();
    }
}