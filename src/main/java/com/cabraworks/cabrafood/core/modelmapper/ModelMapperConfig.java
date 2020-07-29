package com.cabraworks.cabrafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cabraworks.cabrafood.api.model.EnderecoDTO;
import com.cabraworks.cabrafood.api.model.input.ItemPedidoInput;
import com.cabraworks.cabrafood.domain.model.pedido.ItemPedido;
import com.cabraworks.cabrafood.domain.model.restaurante.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
					.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToModelTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
											(enderecoDTODest, value) -> enderecoDTODest.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
