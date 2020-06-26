package com.cabraworks.cabrafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.RestauranteDTO;
import com.cabraworks.cabrafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
		
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}
}
