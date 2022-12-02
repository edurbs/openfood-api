package com.edurbs.openfood;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import jakarta.validation.ConstraintViolationException;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.edurbs.openfood.domain.exception.CozinhaNaoEncontradaException;
import com.edurbs.openfood.domain.exception.EntidadeEmUsoException;
import com.edurbs.openfood.domain.model.Cozinha;
import com.edurbs.openfood.domain.service.CadastroCozinhaService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class OpenfoodApiApplicationTestsIT {

	@Autowired
	CadastroCozinhaService cadastroCozinhaService;

	
	@Test
	public void shouldSave_WhenCozinhaOK() {
		// cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Americada");

		// ação		
		Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinha);

		//validação
		assertNotNull(cozinhaSalva);
		assertNotNull(cozinha.getId());
	}

	@Test
	public void shouldNotSave_WhenNomeNull(){
		Cozinha cozinha = new Cozinha();
		cozinha.setNome(null);

		assertThrowsExactly(
				ConstraintViolationException.class, 
				() -> cadastroCozinhaService.salvar(cozinha)
		);
		
	}

	@Test
	public void shouldFail_WhenDeleteCozinhaInUse(){
		
		assertThrows(EntidadeEmUsoException.class, () ->cadastroCozinhaService.remover(1L));

	}

	@Test
	public void shouldFail_WhenDeleteCozinhaNotExists(){
		assertThrows(CozinhaNaoEncontradaException.class, () -> cadastroCozinhaService.remover(100L));
	}


}
