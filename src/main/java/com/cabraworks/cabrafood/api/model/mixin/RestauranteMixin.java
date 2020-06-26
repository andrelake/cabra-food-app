package com.cabraworks.cabrafood.api.model.mixin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cabraworks.cabrafood.domain.model.Cozinha;
import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;
import com.cabraworks.cabrafood.domain.model.produto.Produto;
import com.cabraworks.cabrafood.domain.model.restaurante.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RestauranteMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<FormaPagamento> formasDePagamento= new ArrayList<>();
	
	@JsonIgnore
	private List<Produto> produtos = new ArrayList<>();

}
