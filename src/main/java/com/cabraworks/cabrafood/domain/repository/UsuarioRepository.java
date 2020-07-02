package com.cabraworks.cabrafood.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.usuario.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
}
