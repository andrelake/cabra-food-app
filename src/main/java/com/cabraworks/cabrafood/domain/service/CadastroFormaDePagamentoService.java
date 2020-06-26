package com.cabraworks.cabrafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
import com.cabraworks.cabrafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;
import com.cabraworks.cabrafood.domain.repository.FormaDePagamentoRepository;

@Service
public class CadastroFormaDePagamentoService {

	@Autowired
	private FormaDePagamentoRepository formaDePagamentoRepository;
	
	public FormaPagamento salvar(FormaPagamento forma) {
		return formaDePagamentoRepository.save(forma);
	}
	
	public void excluir(Long id) {
		
		try {
			buscarOuFalhar(id);
			formaDePagamentoRepository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Forma de pagamento com o código %d não pode ser excluida, pois está em uso", id));
		}
	}
	
	public FormaPagamento buscarOuFalhar(Long id) {
		
		return formaDePagamentoRepository.findById(id)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
	}
}
