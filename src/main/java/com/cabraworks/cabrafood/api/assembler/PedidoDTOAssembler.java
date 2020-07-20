package com.cabraworks.cabrafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.PedidoDTO;
import com.cabraworks.cabrafood.domain.model.pedido.Pedido;

@Component
public class PedidoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoDTO toModel(Pedido pedido) {
		
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
		
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
