package com.cabraworks.cabrafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;
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
import com.cabraworks.cabrafood.domain.model.Restaurante;
import com.cabraworks.cabrafood.domain.repository.CozinhaRepository;
import com.cabraworks.cabrafood.domain.repository.RestauranteRepository;
import com.cabraworks.cabrafood.util.DatabaseCleaner;
import com.cabraworks.cabrafood.util.ResourcesUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteTests {

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	private Integer numRestaurantes;
	
	private Restaurante joeRestaurante;
	
	@Before
	public void setUp() {
		
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveConterNumeroCertoDeRestaurantes_QuandoConsultarRestaurantes() {
		
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(numRestaurantes));
	}
	
	@Test
	public void deveRestornarStatus201_QuandoCadastrarRestaurante() {
		
		RestAssured
		.given()
			.body(ResourcesUtils.getContentFromResource("/json/restaurante-test.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveRestornarStatus400_QuandoCadastrarRestauranteComCozinhaNull() {
		
		RestAssured
		.given()
			.body(ResourcesUtils.getContentFromResource("/json/restaurante-test.json"))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(400);
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		RestAssured
		.given()
			.pathParam("id", joeRestaurante.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(200)
			.body("nome", equalTo(joeRestaurante.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		
		RestAssured
		.given()
			.pathParam("id", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(404);
	}

	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		cozinhaRepository.save(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		cozinhaRepository.save(cozinha2);
		
		joeRestaurante = new Restaurante();
		joeRestaurante.setNome("Joe's Restaurant");
		joeRestaurante.setTaxaFrete(new BigDecimal(5.00));
		joeRestaurante.setCozinha(cozinha1);
		restauranteRepository.save(joeRestaurante);
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Hiroku Food");
		restaurante2.setTaxaFrete(new BigDecimal(5.00));
		restaurante2.setCozinha(cozinha2);
		restauranteRepository.save(restaurante2);
		
		List<Restaurante> list = restauranteRepository.findAll();
		numRestaurantes = list.size();
	}
}
