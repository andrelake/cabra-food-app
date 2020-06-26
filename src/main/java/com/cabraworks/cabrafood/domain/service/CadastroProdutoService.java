//package com.cabraworks.cabrafood.domain.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Service;
//
//import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
//import com.cabraworks.cabrafood.domain.exception.EntidadeNaoEncontradaException;
//import com.cabraworks.cabrafood.domain.model.Restaurante;
//import com.cabraworks.cabrafood.domain.model.produto.Produto;
//import com.cabraworks.cabrafood.domain.repository.ProdutoRepository;
//import com.cabraworks.cabrafood.domain.repository.RestauranteRepository;
//
//@Service
//public class CadastroProdutoService {
//
//	@Autowired
//	private ProdutoRepository produtoRepository;
//	
//	@Autowired
//	private RestauranteRepository restauranteRepository;
//	
//	public Produto salvar(Produto produto) {
//		
//		Long restauranteId = produto.getRestaurante().getId();
//		
//		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
//		
//		if(restaurante.isEmpty()) {
//			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de restaurante com o código %d", restauranteId));
//		}
//		
//		produto.setRestaurante(restaurante.get());
//				
//		return produtoRepository.save(produto);
//	}
//	
//	public void excluir(Long id) {
//		
//		try {
//			produtoRepository.deleteById(id);
//		}
//		catch(EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhum produto com o código %d", id));
//		}
//		catch(DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(String.format("Produto com o código %d não pode ser deletado, pois está em uso", id));
//		}
//	}
//}
