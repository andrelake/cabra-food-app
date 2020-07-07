package com.cabraworks.cabrafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabraworks.cabrafood.domain.exception.EntidadeEmUsoException;
import com.cabraworks.cabrafood.domain.exception.GrupoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.usuario.Grupo;
import com.cabraworks.cabrafood.domain.model.usuario.Permissao;
import com.cabraworks.cabrafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {

	@Autowired
	private GrupoRepository repository;

	@Autowired
	private CadastroPermissaoService permissaoService;

	@Transactional
	public Grupo salvar(Grupo grupo) {

		return repository.save(grupo);
	}

	@Transactional
	public void excluir(Long id) {

		try {
			buscarOuFalhar(id);
			repository.deleteById(id);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Grupo com o código %d não pode ser excluido, pois está em uso", id));
		}
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {

		Grupo grupo = buscarOuFalhar(grupoId);

		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

		grupo.adicionarPermissao(permissao);
	}

	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {

		Grupo grupo = buscarOuFalhar(grupoId);

		Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

		grupo.removerPermissao(permissao);
	}

	public Grupo buscarOuFalhar(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}
}
