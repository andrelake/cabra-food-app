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

import com.cabraworks.cabrafood.api.assembler.GrupoDTOAssembler;
import com.cabraworks.cabrafood.api.model.GrupoDTO;
import com.cabraworks.cabrafood.domain.model.usuario.Usuario;
import com.cabraworks.cabrafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	@Autowired
	private CadastroUsuarioService userService;
	
	@Autowired
	private GrupoDTOAssembler grupoAssembler;
	
	@GetMapping
	public List<GrupoDTO> getAllGrupos(@PathVariable Long usuarioId) {
		
		Usuario usuario = userService.buscarOuFalhar(usuarioId);
		
		return grupoAssembler.toCollectionModel(usuario.getGrupos());
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		
		userService.associarGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		
		userService.desassociarGrupo(usuarioId, grupoId);
	}
}
