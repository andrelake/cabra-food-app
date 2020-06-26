//package com.cabraworks.cabrafood.api.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
//import com.cabraworks.cabrafood.domain.exception.EntidadeNaoEncontradaException;
//import com.cabraworks.cabrafood.domain.model.produto.Produto;
//import com.cabraworks.cabrafood.domain.repository.ProdutoRepository;
//import com.cabraworks.cabrafood.domain.service.CadastroProdutoService;
//
//@RestController
//@RequestMapping("/produtos")
//public class ProdutoController {
//
//	@Autowired
//	private ProdutoRepository produtoRepository;
//	
//	@Autowired
//	private CadastroProdutoService produtoService;
//	
//	@GetMapping
//	public List<Produto> listar(){
//		
//		return produtoRepository.findAll();
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<Produto> buscar(@PathVariable Long id) {
//		
//		Optional<Produto> produto = produtoRepository.findById(id);
//		
//		if(produto.isEmpty()) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(produto.get());
//	}
//	
//	@PostMapping
//	public ResponseEntity<?> adicionar(@RequestBody Produto produto) {
//		
//		try {
//			produto = produtoService.salvar(produto);
//			
//			return ResponseEntity.status(HttpStatus.CREATED).body(produto);
//		}
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
//		
//		try {
//			Optional<Produto> produtoAtual = produtoRepository.findById(id);
//			
//			if(produtoAtual.isPresent()) {
//				
//				BeanUtils.copyProperties(produto, produtoAtual.get(), "id");
//				
//				Produto produtoSalvo = produtoService.salvar(produtoAtual.get());
//				
//				return ResponseEntity.ok(produtoSalvo);
//			}
//			return ResponseEntity.notFound().build();
//		}
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Produto> remover(@PathVariable Long id) {
//		
//		try {
//			produtoService.excluir(id);
//			return ResponseEntity.noContent().build();
//		}
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//		catch(EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
//}
