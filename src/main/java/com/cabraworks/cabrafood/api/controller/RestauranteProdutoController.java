package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.ProdutoDTOAssembler;
import com.cabraworks.cabrafood.api.model.ProdutoDTO;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{id}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroRestauranteService cadRestauranteService;
	
	@Autowired
	private ProdutoDTOAssembler produtoAssembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long id){
		
		Restaurante restaurante = cadRestauranteService.buscarOuFalhar(id);
		
		return produtoAssembler.toCollectionModel(restaurante.getProdutos());
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
		
		cadRestauranteService.associarFormaPagamento(id, formaPagamentoId);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long id, @PathVariable Long formaPagamentoId) {
		
		cadRestauranteService.desassociarFormaPagamento(id, formaPagamentoId);
	}
}
