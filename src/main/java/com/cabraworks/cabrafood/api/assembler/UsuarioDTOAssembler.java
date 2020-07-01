package com.cabraworks.cabrafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.UsuarioDTO;
import com.cabraworks.cabrafood.domain.model.usuario.Produto;

@Component
public class UsuarioDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toModel(Produto user) {
		
		return modelMapper.map(user, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionModel(List<Produto> users) {
		
		return users.stream()
				.map(user -> toModel(user))
				.collect(Collectors.toList());
	}
}
