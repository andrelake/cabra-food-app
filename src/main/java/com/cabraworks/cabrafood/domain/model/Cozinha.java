package com.cabraworks.cabrafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cabraworks.cabrafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data  //Lombok - getters, setters, equals and hashcode(por padrao usa todos os atributos), to string
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  // para fazer atributo para equals
@Entity
public class Cozinha {

	@NotNull(groups = Groups.CozinhaId.class)
	@EqualsAndHashCode.Include      //usa o atributo id pra equals
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore           //indica quem vai ser ignorado na hora de serialização do bd - a cozinha não vai serializar restaurantes
	@OneToMany(mappedBy = "cozinha")        //terminação MANY indica que é uma coleção/  mapped indica onde a associação foi feita na classe mãe
	private List<Restaurante> restaurantes = new ArrayList<>();

}
