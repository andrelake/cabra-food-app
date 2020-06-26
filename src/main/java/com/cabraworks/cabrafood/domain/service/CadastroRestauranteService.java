package com.cabraworks.cabrafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabraworks.cabrafood.domain.exception.RestauranteNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.Cozinha;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.restaurante.Cidade;
import com.cabraworks.cabrafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cozinhaService;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cidadeService.buscaOuFalha(cidadeId);
	
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante); 
	}
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
	}

	public Restaurante buscarOuFalhar(Long id) {
		
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}
}
