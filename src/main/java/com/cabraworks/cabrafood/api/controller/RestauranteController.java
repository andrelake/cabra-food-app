package com.cabraworks.cabrafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cabraworks.cabrafood.api.assembler.RestauranteDTOAssembler;
import com.cabraworks.cabrafood.api.assembler.RestauranteInputDisassembler;
import com.cabraworks.cabrafood.api.model.RestauranteDTO;
import com.cabraworks.cabrafood.api.model.input.RestauranteInput;
import com.cabraworks.cabrafood.domain.exception.CidadeNaoEncontradaException;
import com.cabraworks.cabrafood.domain.exception.CozinhaNaoEncontradaException;
import com.cabraworks.cabrafood.domain.exception.NegocioException;
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.repository.RestauranteRepository;
import com.cabraworks.cabrafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadRestauranteService;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteDTODisassembler;
	
	@GetMapping
	public List<RestauranteDTO> listar(){
		
		return restauranteDTOAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		
		Restaurante restaurante = cadRestauranteService.buscarOuFalhar(id);
		
		return restauranteDTOAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			Restaurante restaurante = restauranteDTODisassembler.toDomainObject(restauranteInput);
			
			return restauranteDTOAssembler.toModel(cadRestauranteService.salvar(restaurante));
		}
		catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody  @Valid RestauranteInput restauranteInput){
		
		try {
			Restaurante restauranteAtual = cadRestauranteService.buscarOuFalhar(id);
			
			restauranteDTODisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
		
			return restauranteDTOAssembler.toModel(cadRestauranteService.salvar(restauranteAtual));
			//return toDTO(cadRestauranteService.salvar(restauranteAtual));
		}
		catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		cadRestauranteService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		cadRestauranteService.inativar(id);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		
		cadRestauranteService.ativar(restauranteIds);
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		
		cadRestauranteService.inativar(restauranteIds);
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		cadRestauranteService.abrirRestaurante(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		cadRestauranteService.fecharRestaurante(id);;
	}

}
