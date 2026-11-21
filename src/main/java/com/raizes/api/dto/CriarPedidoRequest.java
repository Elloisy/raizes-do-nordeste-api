package com.raizes.api.dto;

import com.raizes.api.domain.enums.CanalPedido;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CriarPedidoRequest(
        @NotNull Long usuarioId,
        @NotNull Long unidadeId,
        @NotNull CanalPedido canalPedido,
        @NotEmpty List<ItemPedidoRequest> itens
) {
	
}
