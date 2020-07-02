package com.cabraworks.cabrafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.ProdutoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.produto.Produto;
import com.cabraworks.cabrafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto salvar(Produto produto) {
		
		return produtoRepository.save(produto);
	}
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
		
		return produtoRepository.findById(restauranteId, produtoId)
				.orElseThrow(()-> new ProdutoNaoEncontradoException(produtoId, restauranteId));
	}
}
