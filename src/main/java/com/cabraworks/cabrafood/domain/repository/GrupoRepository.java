package com.cabraworks.cabrafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.usuario.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long>{

}
