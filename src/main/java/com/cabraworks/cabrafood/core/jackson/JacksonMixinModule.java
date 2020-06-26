package com.cabraworks.cabrafood.core.jackson;

import org.springframework.stereotype.Component;

import com.cabraworks.cabrafood.api.model.mixin.CidadeMixin;
import com.cabraworks.cabrafood.api.model.mixin.CozinhaMixin;
import com.cabraworks.cabrafood.api.model.mixin.RestauranteMixin;
import com.cabraworks.cabrafood.domain.model.Cozinha;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.restaurante.Cidade;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule{

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
}
