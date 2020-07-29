package com.cabraworks.cabrafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabraworks.cabrafood.domain.exception.NegocioException;
import com.cabraworks.cabrafood.domain.exception.PedidoNaoEncontradoException;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.model.pedido.FormaPagamento;
import com.cabraworks.cabrafood.domain.model.pedido.Pedido;
import com.cabraworks.cabrafood.domain.model.produto.Produto;
import com.cabraworks.cabrafood.domain.model.restaurante.Cidade;
import com.cabraworks.cabrafood.domain.model.usuario.Usuario;
import com.cabraworks.cabrafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroCidadeService cidadeService;
	
	@Autowired
	private CadastroUsuarioService usuarioService;
	
	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private CadastroFormaDePagamentoService formaService;

	@Autowired
	private CadastroProdutoService produtoService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.valorTotalPedido();
		
		return pedidoRepository.save(pedido);
	}
	
	private void validarPedido(Pedido pedido) {
		
		Cidade cidade = cidadeService.buscaOuFalha(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = formaService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", formaPagamento.getDescricao()));
		}
	}
	
	private void validarItens(Pedido pedido) {
		
		pedido.getItens().forEach(item -> {
			
			Produto produto = produtoService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}
	
	public Pedido buscarOuFalhar(Long pedidoId) {
		
		return pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
}
