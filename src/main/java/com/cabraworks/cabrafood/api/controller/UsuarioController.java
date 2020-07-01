package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.UsuarioDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.UsuarioInputDisassembler;
import com.cabraworks.cabrafood.api.model.UsuarioDTO;
import com.cabraworks.cabrafood.api.model.input.UsuarioInput;
import com.cabraworks.cabrafood.api.model.input.UsuarioSemSenhaInput;
import com.cabraworks.cabrafood.api.model.input.UsuarioSenhaInput;
import com.cabraworks.cabrafood.domain.model.usuario.Produto;
import com.cabraworks.cabrafood.domain.repository.UsuarioRepository;
import com.cabraworks.cabrafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private CadastroUsuarioService service;
	
	@Autowired
	private UsuarioDTOAssembler assembler;
	
	@Autowired
	private UsuarioInputDisassembler disassembler;
	
	@GetMapping
	public List<UsuarioDTO> findAll() {
		
		return assembler.toCollectionModel(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public UsuarioDTO findById(@PathVariable Long id) {
		
		return assembler.toModel(service.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO insert(@RequestBody @Valid UsuarioInput userInput) {
		
		Produto user = disassembler.toDomainObject(userInput);
		
		return assembler.toModel(service.salvar(user));
	}
	
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioSemSenhaInput userSemSenhaInput) {
		
		Produto user = service.buscarOuFalhar(id);
		
		disassembler.copyToDomainObject(userSemSenhaInput, user);
		
		return assembler.toModel(service.salvar(user));
	}
	
	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable Long id, @RequestBody @Valid UsuarioSenhaInput userSenhaInput) {
	
		service.alterarSenha(id, userSenhaInput.getSenhaAtual(), userSenhaInput.getNovaSenha());
	}
	
}
