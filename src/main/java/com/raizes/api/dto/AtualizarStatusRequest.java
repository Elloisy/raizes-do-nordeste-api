package com.raizes.api.dto;

import com.raizes.api.domain.enums.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusRequest(
        @NotNull StatusPedido novoStatus
) {
	
}
