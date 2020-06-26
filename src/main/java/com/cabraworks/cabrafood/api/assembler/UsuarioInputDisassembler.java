package com.cabraworks.cabrafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.input.UsuarioInput;
import com.cabraworks.cabrafood.api.model.input.UsuarioSemSenhaInput;
import com.cabraworks.cabrafood.domain.model.usuario.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario toDomainObject(UsuarioInput userInput) {

		return modelMapper.map(userInput, Usuario.class);
	}

	public void copyToDomainObject(UsuarioInput userInput, Usuario user) {

		modelMapper.map(userInput, user);
	}

	public void copyToDomainObject(UsuarioSemSenhaInput userSemSenhaInput, Usuario user) {

		modelMapper.map(userSemSenhaInput, user);
	}
}
