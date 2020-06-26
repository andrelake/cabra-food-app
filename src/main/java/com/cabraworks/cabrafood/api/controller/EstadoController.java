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

import com.cabraworks.cabrafood.api.assembler.EstadoDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.EstadoInputDisassembler;
import com.cabraworks.cabrafood.api.model.EstadoDTO;
import com.cabraworks.cabrafood.api.model.input.EstadoInput;
import com.cabraworks.cabrafood.domain.model.restaurante.Estado;
import com.cabraworks.cabrafood.domain.repository.EstadoRepository;
import com.cabraworks.cabrafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadEstadoService;
	
	@Autowired
	private EstadoDTOAssembler estadoAssembler;
	
	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;
	
	@GetMapping
	public List<EstadoDTO> listar(){
		return estadoAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoDTO buscar(@PathVariable Long id) {
		
		return estadoAssembler.toModel(cadEstadoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
		
		return estadoAssembler.toModel(cadEstadoService.salvar(estado));
	}
	
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoInput estadoInput){
		
		Estado estadoAtual = cadEstadoService.buscarOuFalhar(id);
		
		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);
			
		return estadoAssembler.toModel(cadEstadoService.salvar(estadoAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		
		cadEstadoService.excluir(id);
	}
}
