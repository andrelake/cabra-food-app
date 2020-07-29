package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.PedidoDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.PedidoInputDisassembler;
import com.cabraworks.cabrafood.api.assembler.PedidoResumoDTOAssembler;
import com.cabraworks.cabrafood.api.model.PedidoDTO;
import com.cabraworks.cabrafood.api.model.PedidoResumoDTO;
import com.cabraworks.cabrafood.api.model.input.PedidoInput;
import com.cabraworks.cabrafood.domain.exception.EntidadeNaoEncontradaException;
import com.cabraworks.cabrafood.domain.exception.NegocioException;
import com.cabraworks.cabrafood.domain.model.pedido.Pedido;
import com.cabraworks.cabrafood.domain.model.usuario.Usuario;
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
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	@GetMapping
	public List<PedidoResumoDTO> getAllPedidos() {
		
		List<Pedido> pedidos = pedidoRepository.findAll();
		
		return pedidoResumoAssembler.toCollectionModel(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long pedidoId) {
		
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		return ResponseEntity.ok(pedidoAssembler.toModel(pedido));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO insert(@RequestBody @Valid PedidoInput pedidoInput) {
		
		try {
			Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
			
			//TODO pegar usuario autenticado (mais pra frente)
			novoPedido.setCliente(new Usuario());
			novoPedido.getCliente().setId(1L);
			
			novoPedido = pedidoService.emitir(novoPedido);
			
			return pedidoAssembler.toModel(novoPedido);
		}
		catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
