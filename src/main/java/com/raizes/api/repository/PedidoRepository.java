package com.raizes.api.repository;

import com.raizes.api.domain.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.raizes.api.domain.enums.CanalPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByCanalPedido(CanalPedido canalPedido);
}