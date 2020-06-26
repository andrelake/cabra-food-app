package com.cabraworks.cabrafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cabraworks.cabrafood.domain.model.Cozinha;
import com.cabraworks.cabrafood.domain.repository.CozinhaRepository;
import com.cabraworks.cabrafood.util.DatabaseCleaner;
import com.cabraworks.cabrafood.util.ResourcesUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaTests {

	private static final int COZINHA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Integer numCozinhas;
	
	private Cozinha cozinhaTailandesa;
	
	@Before
	public void setUp() {
		
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		dataBaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveConterNumeroCertoDeCozinhas_QuandoConsultarCozinhas() {
		
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numCozinhas));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		
		RestAssured
		.given()
			.body(ResourcesUtils.getContentFromResource("/json/cozinha-test.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		RestAssured
		.given()
			.pathParam("id", cozinhaTailandesa.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(200)
			.body("nome", equalTo(cozinhaTailandesa.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		
		RestAssured
		.given()
			.pathParam("id", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(404);
	}
	
	
	private void prepararDados() {
		
		cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Chinesa");
		cozinhaRepository.save(cozinha2);

		List<Cozinha> list = cozinhaRepository.findAll();
		numCozinhas = list.size();
	}
	
	
	//testes integração
//	@Autowired
//	private CadastroCozinhaService cozinhaService;
//	
//	@Test(expected = EntidadeEmUsoException.class)
//	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
//		
//		//cenário
//		Cozinha cozinha = new Cozinha();
//		cozinha = cozinhaService.buscarOuFalhar(1L);
//
//		//ação
//		cozinhaService.excluir(cozinha.getId());
//		//validação
//	}
//	
//	@Test(expected = EntidadeNaoEncontradaException.class)
//	public void deveFalhar_QuandoExcluirCozinhaNaoEncontrada() {
//		
//		//cenário
//		
//		//ação
//		cozinhaService.excluir(99L);
//	}
}
