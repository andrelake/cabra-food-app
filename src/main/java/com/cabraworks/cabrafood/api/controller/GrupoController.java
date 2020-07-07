package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.GrupoDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.GrupoInputDisassembler;
import com.cabraworks.cabrafood.api.model.GrupoDTO;
import com.cabraworks.cabrafood.api.model.input.GrupoInput;
import com.cabraworks.cabrafood.domain.model.usuario.Grupo;
import com.cabraworks.cabrafood.domain.repository.GrupoRepository;
import com.cabraworks.cabrafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService grupoService;
	
	@Autowired
	private GrupoDTOAssembler grupoAssembler;
	
	@Autowired GrupoInputDisassembler grupoDisassembler;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		
		return grupoAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public GrupoDTO buscar(@PathVariable Long id) {
		
		return grupoAssembler.toModel(grupoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupo = grupoDisassembler.toDomainObject(grupoInput);
		
		return grupoAssembler.toModel(grupoService.salvar(grupo));
	}
	
	@PutMapping("/{id}")
	public GrupoDTO atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupoAtual = grupoService.buscarOuFalhar(id);
		
		grupoDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		return grupoAssembler.toModel(grupoService.salvar(grupoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		
		grupoService.excluir(id);
	}
}
