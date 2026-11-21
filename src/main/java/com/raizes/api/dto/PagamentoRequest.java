package com.raizes.api.dto;

import com.raizes.api.domain.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(
        @NotNull(message = "A forma de pagamento é obrigatória")
        FormaPagamento formaPagamento,
        Boolean simularRecusa
) {
	
}

