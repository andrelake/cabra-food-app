package com.cabraworks.cabrafood.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
import com.cabraworks.cabrafood.domain.exception.ProdutoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.exception.RestauranteNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.produto.Produto;
import com.cabraworks.cabrafood.domain.repository.ProdutoRepository;
import com.cabraworks.cabrafood.domain.repository.RestauranteRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Transactional
	public Produto salvar(Produto produto) {
		
		Long restauranteId = produto.getRestaurante().getId();
		
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if(restaurante.isEmpty()) {
			throw new RestauranteNaoEncontradoException(restauranteId);
		}
		
		produto.setRestaurante(restaurante.get());
				
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		try {
			buscarOuFalhar(id);
			produtoRepository.deleteById(id);
			produtoRepository.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Produto com o código %d não pode ser deletado, pois está em uso", id));
		}
	}
	
	public Produto buscarOuFalhar(Long id) {
		
		return produtoRepository.findById(id)
				.orElseThrow(()-> new ProdutoNaoEncontradoException(id));
	}
}
