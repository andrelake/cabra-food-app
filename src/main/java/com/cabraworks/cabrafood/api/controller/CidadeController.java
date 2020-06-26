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

import com.cabraworks.cabrafood.api.assembler.CidadeDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.CidadeInputDisassembler;
import com.cabraworks.cabrafood.api.model.CidadeDTO;
import com.cabraworks.cabrafood.api.model.input.CidadeInput;
import com.cabraworks.cabrafood.domain.exception.EstadoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.exception.NegocioException;
import com.cabraworks.cabrafood.domain.model.restaurante.Cidade;
import com.cabraworks.cabrafood.domain.repository.CidadeRepository;
import com.cabraworks.cabrafood.domain.service.CadastroCidadeService;
 
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadCidadeService;
	
	@Autowired
	private CidadeDTOAssembler cidadeAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar(){
		
		return cidadeAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public CidadeDTO buscar(@PathVariable Long id) {
		
		return cidadeAssembler.toModel(cadCidadeService.buscaOuFalha(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput){
		
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			return cidadeAssembler.toModel(cadCidadeService.salvar(cidade)); 
		}
		catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@PathVariable Long id, @RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadCidadeService.buscaOuFalha(id);
			
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);
			
			return cidadeAssembler.toModel(cadCidadeService.salvar(cidadeAtual));
		}
		catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
	
		cadCidadeService.excluir(id);
	}
}
