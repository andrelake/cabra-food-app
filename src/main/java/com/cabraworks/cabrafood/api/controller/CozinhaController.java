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

import com.cabraworks.cabrafood.api.assembler.CozinhaDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.CozinhaInputDisassembler;
import com.cabraworks.cabrafood.api.model.CozinhaDTO;
import com.cabraworks.cabrafood.api.model.input.CozinhaInput;
import com.cabraworks.cabrafood.domain.model.Cozinha;
import com.cabraworks.cabrafood.domain.repository.CozinhaRepository;
import com.cabraworks.cabrafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadCozinhaService;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	@GetMapping
	public List<CozinhaDTO> listar(){
		return cozinhaDTOAssembler.toCollectionModel(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
		
		return cozinhaDTOAssembler.toModel(cadCozinhaService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		return cozinhaDTOAssembler.toModel(cadCozinhaService.salvar(cozinha));
	}
	
	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput){
		
		Cozinha cozinhaAtual = cadCozinhaService.buscarOuFalhar(id);
		
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
			
		return cozinhaDTOAssembler.toModel(cadCozinhaService.salvar(cozinhaAtual));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		
		cadCozinhaService.excluir(id);
	}
}
