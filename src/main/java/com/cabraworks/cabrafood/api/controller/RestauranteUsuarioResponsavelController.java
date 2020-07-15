package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.UsuarioDTOAssembler;
import com.cabraworks.cabrafood.api.model.UsuarioDTO;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private UsuarioDTOAssembler usuarioAssembler;
	
	@GetMapping
	public List<UsuarioDTO> getAllResponsaveis(@PathVariable Long restauranteId) {
		
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		return usuarioAssembler.toCollectionModel(restaurante.getResponsaveis());
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteService.associarResponsavel(restauranteId, usuarioId);
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}
}
