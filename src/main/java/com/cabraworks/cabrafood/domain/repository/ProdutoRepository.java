package com.cabraworks.cabrafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.produto.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
