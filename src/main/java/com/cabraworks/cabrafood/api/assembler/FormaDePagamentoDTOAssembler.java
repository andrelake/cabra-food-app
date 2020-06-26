package com.cabraworks.cabrafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.FormaDePagamentoDTO;
import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;

@Component
public class FormaDePagamentoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaDePagamentoDTO toModel(FormaPagamento formapagamento) {
		
		return modelMapper.map(formapagamento, FormaDePagamentoDTO.class);
	}
	
	public List<FormaDePagamentoDTO> toCollectionModel(List<FormaPagamento> formas) {
		
		return formas.stream()
				.map(forma -> toModel(forma))
				.collect(Collectors.toList());
	}
}
