package com.cabraworks.cabrafood.api.model.mixin;

import com.cabraworks.cabrafood.domain.model.restaurante.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado; 
}
