package com.raizes.api.dto;

import com.raizes.api.domain.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record PagamentoRequest(
        @NotNull FormaPagamento formaPagamento
) {
	
}

