package com.cabraworks.cabrafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradoException(Long id) {
		
		this(String.format("Não existe um cadastro de estado com o código %d", id));
	}
}
