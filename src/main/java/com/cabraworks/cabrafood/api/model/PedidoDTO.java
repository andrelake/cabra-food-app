package com.cabraworks.cabrafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String status;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	private RestauranteResumoDTO restaurante;
	private FormaDePagamentoDTO formaPagamento;
	private UsuarioDTO cliente;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;
}
