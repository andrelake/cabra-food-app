package com.cabraworks.cabrafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.usuario.Produto;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Produto, Long>{

	Optional<Produto> findByEmail(String email);
}
