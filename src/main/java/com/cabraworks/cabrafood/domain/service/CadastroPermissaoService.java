package com.cabraworks.cabrafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
import com.cabraworks.cabrafood.domain.exception.PermissaoNaoEncontradaException;
import com.cabraworks.cabrafood.domain.model.usuario.Permissao;
import com.cabraworks.cabrafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Transactional
	public Permissao salvar(Permissao permissao) {
		
			return permissaoRepository.save(permissao);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		try {
			buscarOuFalhar(id);
			permissaoRepository.deleteById(id);
			permissaoRepository.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Permissão de código %d não pode removida, pois está em uso", id));
		}
	}
	
	public Permissao buscarOuFalhar(Long id) {
		
		return permissaoRepository.findById(id)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(id));
	}
}
