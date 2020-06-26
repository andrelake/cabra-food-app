package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.FormaDePagamentoDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.FormaPagamentoInputDisassembler;
import com.cabraworks.cabrafood.api.model.FormaDePagamentoDTO;
import com.cabraworks.cabrafood.api.model.input.FormaPagamentoInput;
import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;
import com.cabraworks.cabrafood.domain.repository.FormaDePagamentoRepository;
import com.cabraworks.cabrafood.domain.service.CadastroFormaDePagamentoService;

@RestController
@RequestMapping("/formasdepagamento")
public class FormaDePagamentoController {

	@Autowired
	private FormaDePagamentoRepository formaRepository;
	
	@Autowired
	private CadastroFormaDePagamentoService formaService;
	
	@Autowired
	private FormaDePagamentoDTOAssembler formaDTOAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public List<FormaDePagamentoDTO> listar(){
		return formaDTOAssembler.toCollectionModel(formaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaDePagamentoDTO> buscarPorId(@PathVariable Long id) {
		
		FormaPagamento forma = formaService.buscarOuFalhar(id);
		
		return ResponseEntity.ok(formaDTOAssembler.toModel(forma));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaDePagamentoDTO adicionar(@RequestBody FormaPagamentoInput formaInput) {
		
		FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaInput);
		
		return formaDTOAssembler.toModel(formaService.salvar(formaPagamento));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<FormaDePagamentoDTO> atualizar(@PathVariable Long id, @RequestBody FormaPagamentoInput formaInput) {
		
		FormaPagamento formaAtual = formaService.buscarOuFalhar(id);
		
		formaPagamentoInputDisassembler.copyToDomainObject(formaInput, formaAtual);
		
		formaAtual = formaService.salvar(formaAtual);
			
		return ResponseEntity.ok(formaDTOAssembler.toModel(formaAtual));

	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		
		formaService.excluir(id);
	}
}
