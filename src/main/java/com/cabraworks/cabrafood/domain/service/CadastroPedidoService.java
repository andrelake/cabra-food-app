package com.cabraworks.cabrafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.PedidoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.pedido.Pedido;
import com.cabraworks.cabrafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
