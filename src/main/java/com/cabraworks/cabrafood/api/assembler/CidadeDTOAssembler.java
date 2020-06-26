package com.cabraworks.cabrafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.CidadeDTO;
import com.cabraworks.cabrafood.domain.model.restaurante.Cidade;

@Component
public class CidadeDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTO toModel(Cidade cidade) {
		
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toCollectionModel(List<Cidade> cidades) {
		
		return cidades.stream()
				.map(forma -> toModel(forma))
				.collect(Collectors.toList());
	}
}
