package com.cabraworks.cabrafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;

@Repository
public interface FormaDePagamentoRepository extends JpaRepository<FormaPagamento, Long>{

}
