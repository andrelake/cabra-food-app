//package com.cabraworks.cabrafood.api.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
//import com.cabraworks.cabrafood.domain.exception.EntidadeNaoEncontradaException;
//import com.cabraworks.cabrafood.domain.model.usuario.Permissao;
//import com.cabraworks.cabrafood.domain.repository.PermissaoRepository;
//import com.cabraworks.cabrafood.domain.service.CadastroPermissaoService;
//
//@RestController
//@RequestMapping("/permissoes")
//public class PermissaoController {
//
//	@Autowired
//	private PermissaoRepository permissaoRepository;
//	
//	@Autowired
//	private CadastroPermissaoService cadPermissaoService;
//	
//	@GetMapping
//	public List<Permissao> listar(){
//		return permissaoRepository.findAll();
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<Permissao> buscarPorId(@PathVariable Long id) {
//		
//		Optional<Permissao> permissao = permissaoRepository.findById(id);
//		
//		if(permissao.isPresent()) {
//			return ResponseEntity.ok(permissao.get());
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public Permissao adicionar(@RequestBody Permissao permissao) {
//		return cadPermissaoService.salvar(permissao);
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<Permissao> atualizar(@PathVariable Long id, @RequestBody Permissao permissao) {
//		Optional<Permissao> permissaoAtual = permissaoRepository.findById(id);
//			
//		if(permissaoAtual.isPresent()) {
//				
//			BeanUtils.copyProperties(permissao, permissaoAtual.get(), "id");
//				
//			Permissao permissaoSalva = cadPermissaoService.salvar(permissaoAtual.get());
//				
//			return ResponseEntity.ok().body(permissaoSalva);
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Permissao> remover(@PathVariable Long id){
//		
//		try {
//			cadPermissaoService.excluir(id);
//			return ResponseEntity.noContent().build();
//		}
//		catch(EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//		catch(EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
//}
