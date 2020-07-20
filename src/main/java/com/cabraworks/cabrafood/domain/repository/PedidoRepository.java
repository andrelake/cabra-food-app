package com.cabraworks.cabrafood.domain.repository;

import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.pedido.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

}
