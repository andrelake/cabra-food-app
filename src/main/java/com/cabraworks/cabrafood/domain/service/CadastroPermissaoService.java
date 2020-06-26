//package com.cabraworks.cabrafood.domain.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Service;
//
//import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
//import com.cabraworks.cabrafood.domain.exception.EntidadeNaoEncontradaException;
//import com.cabraworks.cabrafood.domain.model.usuario.Permissao;
//import com.cabraworks.cabrafood.domain.repository.PermissaoRepository;
//
//@Service
//public class CadastroPermissaoService {
//
//	@Autowired
//	private PermissaoRepository permissaoRepository;
//	
//	public Permissao salvar(Permissao permissao) {
//		
//			return permissaoRepository.save(permissao);
//	}
//	
//	public void excluir(Long id) {
//		
//		try {
//			permissaoRepository.deleteById(id);
//		}
//		catch(EmptyResultDataAccessException e) {
//			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de permissão com o código %d", id));
//		}
//		catch(DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(String.format("Permissão de código %d não pode removida, pois está em uso", id));
//		}
//	}
//}
