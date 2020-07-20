package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.PedidoDTOAssembler;
import com.cabraworks.cabrafood.api.model.PedidoDTO;
import com.cabraworks.cabrafood.domain.model.pedido.Pedido;
import com.cabraworks.cabrafood.domain.repository.PedidoRepository;
import com.cabraworks.cabrafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroPedidoService pedidoService;
	
	@Autowired
	private PedidoDTOAssembler pedidoAssembler;
	
	@GetMapping
	public List<PedidoDTO> getAllPedidos() {
		
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		return pedidoAssembler.toCollectionModel(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long pedidoId) {
		
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		return ResponseEntity.ok(pedidoAssembler.toModel(pedido));
	}
}
