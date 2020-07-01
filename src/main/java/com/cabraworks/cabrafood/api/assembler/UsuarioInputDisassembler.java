package com.cabraworks.cabrafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.input.UsuarioInput;
import com.cabraworks.cabrafood.api.model.input.UsuarioSemSenhaInput;
import com.cabraworks.cabrafood.domain.model.usuario.Produto;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Produto toDomainObject(UsuarioInput userInput) {

		return modelMapper.map(userInput, Produto.class);
	}

	public void copyToDomainObject(UsuarioInput userInput, Produto user) {

		modelMapper.map(userInput, user);
	}

	public void copyToDomainObject(UsuarioSemSenhaInput userSemSenhaInput, Produto user) {

		modelMapper.map(userSemSenhaInput, user);
	}
}
