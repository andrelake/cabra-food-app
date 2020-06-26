package com.cabraworks.cabrafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public UsuarioNaoEncontradoException(Long id) {
		
		this(String.format("Não existe um cadastro de usuario com o código %d", id));
	}
}
