package com.cabraworks.cabrafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.input.GrupoInput;
import com.cabraworks.cabrafood.domain.model.usuario.Grupo;

@Component
public class GrupoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInput grupoInput) {
		
		return modelMapper.map(grupoInput, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
		
		modelMapper.map(grupoInput, grupo);
	}
}
