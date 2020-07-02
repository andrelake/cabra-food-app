package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.ProdutoDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.ProdutoInputDisassembler;
import com.cabraworks.cabrafood.api.model.ProdutoDTO;
import com.cabraworks.cabrafood.api.model.input.ProdutoInput;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.produto.Produto;
import com.cabraworks.cabrafood.domain.repository.ProdutoRepository;
import com.cabraworks.cabrafood.domain.service.CadastroProdutoService;
import com.cabraworks.cabrafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{id}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService cadRestauranteService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private ProdutoDTOAssembler produtoAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoDisassembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(@PathVariable Long id){
		
		Restaurante restaurante = cadRestauranteService.buscarOuFalhar(id);
		
		List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
		
		return produtoAssembler.toCollectionModel(produtos);
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO listar(@PathVariable Long id, @PathVariable Long produtoId){
		
		Produto produto = produtoService.buscarOuFalhar(id, produtoId);
		
		return produtoAssembler.toModel(produto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Restaurante restaurante = cadRestauranteService.buscarOuFalhar(id);
		
		Produto produto = produtoDisassembler.toDomainObject(produtoInput);
		produto.setRestaurante(restaurante);
		
		return produtoAssembler.toModel(produtoService.salvar(produto));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long id, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
		
		Produto produtoAtual = produtoService.buscarOuFalhar(id, produtoId);
		
		produtoDisassembler.copyToDomainObject(produtoInput, produtoAtual);
		
		produtoAtual = produtoService.salvar(produtoAtual);
		
		return produtoAssembler.toModel(produtoAtual);
	}
}
