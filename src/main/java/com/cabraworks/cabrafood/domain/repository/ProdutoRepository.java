package com.cabraworks.cabrafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.produto.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query("from Produto WHERE restaurante.id = :restaurante and id = :produto")
	Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);
	
	List<Produto> findByRestaurante(Restaurante restaurante);
}
