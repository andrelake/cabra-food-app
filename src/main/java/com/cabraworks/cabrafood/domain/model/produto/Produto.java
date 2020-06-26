package com.cabraworks.cabrafood.domain.model.produto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.cabraworks.cabrafood.domain.model.Restaurante;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "produto_nome", nullable = false)
	private String nome;
	
	@Column(name = "produto_descricao", nullable = false)
	private String descricao;
	
	@Column(name = "produto_preco", nullable = false)
	private BigDecimal preco;
	
	@Column(name = "produto_ativo", nullable = false)
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
}
