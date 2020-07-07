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

import com.cabraworks.cabrafood.api.assembler.PermissaoDTOAssembler;
import com.cabraworks.cabrafood.api.model.PermissaoDTO;
import com.cabraworks.cabrafood.domain.model.usuario.Grupo;
import com.cabraworks.cabrafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissoesController {

	@Autowired
	private CadastroGrupoService grupoService;
	
	@Autowired
	private PermissaoDTOAssembler permissaoAssembler;
	
	@GetMapping
	public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
		
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		return permissaoAssembler.toCollectionModel(grupo.getPermissoes());
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		grupoService.associarPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		
		grupoService.desassociarPermissao(grupoId, permissaoId);
	}
}
