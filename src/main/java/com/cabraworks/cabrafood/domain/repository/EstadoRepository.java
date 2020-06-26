package com.cabraworks.cabrafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.restaurante.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{

}
