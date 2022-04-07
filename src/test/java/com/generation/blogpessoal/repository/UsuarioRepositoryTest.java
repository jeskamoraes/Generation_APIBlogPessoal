package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

/*
 * @SpringBootTest INFORMA QUE É UMA CLASSE DE TESTE
 * WebEnvironment.RANDOM_PORT É RESPONSÁVEL POR PROCURAR UMA PORTA LIVRE
 * CASO A PORTA PADRÃO ESTEJA OCUPADA
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*
	 * RESPONSÁVEL POR INSERIR OS DADOS NO BANCO ANTES DE RODAR OS TESTES
	 */
	@BeforeAll
	void start() {

		/*
		 * LIMPANDO O BANCO
		 */
		usuarioRepository.deleteAll();

		/*
		 * INSERINDO DADOS NO BANCO 0L -> EQUIVALE AO ID QUE SERÁ AUTOINCREMENTADO PELO
		 * BANCO DE DADOS (L: CORREPONDE AO TIPO LONG)
		 */
		usuarioRepository.save(
				new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));

		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278",
				"https://i.imgur.com/NtyGneo.jpg"));

		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278",
				"https://i.imgur.com/mB3VM2N.jpg"));

		usuarioRepository.save(
				new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
	}

	@Test
	@DisplayName("Retorna 1 usuário 👀")
	public void deveRetornarUmUsuario() {
		/*
		 * TESTANDO MÉTODO findByUsuario, QUE DEVE RETORNAR APENAS (1) O USUÁRIO SOLICITADO
		 */
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	@Test
	@DisplayName("Retorna 3 usuários 👀")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
	}

}
