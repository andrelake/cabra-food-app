package com.cabraworks.cabrafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private Double preco;
	private Boolean ativo;
}
