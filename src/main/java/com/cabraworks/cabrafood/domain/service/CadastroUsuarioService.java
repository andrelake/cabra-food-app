package com.cabraworks.cabrafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
import com.cabraworks.cabrafood.domain.exception.NegocioException;
import com.cabraworks.cabrafood.domain.exception.UsuarioNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.usuario.Grupo;
import com.cabraworks.cabrafood.domain.model.usuario.Usuario;
import com.cabraworks.cabrafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private CadastroGrupoService grupoService;
	
	@Transactional
	public Usuario salvar(Usuario user) {
		
		repository.detach(user);
		
		Optional<Usuario> usuario = repository.findByEmail(user.getEmail());
		
		if(usuario.isPresent() && !usuario.get().equals(user)) {
			throw new NegocioException(String.format("O email %s já está cadastrado em outro usuário. Insira outro.", user.getEmail()));
		}
		
		return repository.save(user);
	}
	
	@Transactional
	public void excluir(Long id) {
		
		try {
			buscarOuFalhar(id);
			repository.deleteById(id);
			repository.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Usuário de código %d não pode ser removido, pois está em uso", id));
		}
	}
	
	@Transactional
	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
		
		Usuario user = buscarOuFalhar(id);
		
		if(user.senhaUnchecked(senhaAtual)) {
			throw new NegocioException("Erro ao validar a senha");
		}
		
		user.setSenha(novaSenha);
	}
	
	@Transactional
	public void associarGrupo(Long usuarioId, Long grupoId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.adicionarGrupo(grupo);
	}
	
	@Transactional
	public void desassociarGrupo(Long usuarioId, Long grupoId) {
		
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		
		usuario.removerGrupo(grupo);
	}
	
	public Usuario buscarOuFalhar(Long id) {
		
		return repository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}
}
