package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/postagens")
//LIBERAR ORIGENS (SERVIDORES DO BACK E FRONT DIFERENTES)
//NO LUGAR DO * COLOCA - SE O LINK
//ALLOWEDHEADRES REFERE-SE AO TOKEN DE ACESSO RELACIONADO AO SECURITY
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired // TRANSFERE A RESPONSABILIDADE DE CRIAR E INSTANCIAR O OBJETO PARA O SPRING
	private PostagemRepository postagemRepository; // INSTANCIANDO OBJETO DA INTERFACE
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping // ResponseEntity, PORQUE DEVOLVE UM STATUS HTTP
	public ResponseEntity<List<Postagem>> getAll() { // getAll É NOME QUE DEMOS PARA O MÉTODO
		return ResponseEntity.ok(postagemRepository.findAll());
		// ESSE MÉTODO EQUIVALE AO 'SELECT * FROM tb_postagens'
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) { // PEGA O VALOR DIGITADO NA URL E ARMANEZA NA
																		// VARIÁVEL 'LONG ID'
		// FUNÇÃO LAMBDA
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) // MAP É UM OBJETO DO TIPO																	// OPTIONAL
				.orElse(ResponseEntity.notFound().build());

		// EQUIVALE AO SELECT * FROM tb_postagens WHERE id = 1
		// lambda = VERIFICA SE O ID DIGITADO EXISTE NA BASE DE DADOS
		// CASO EXISTA RETORNA O CONTEÚDO(STATUS 200), SENÃO RETORNA UMA MENSAGEM
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) {
		/* - CRIANDO UM NOVO DADO
		   - EQUIVALE AO INSERT TO NO MYSQL
		   - RECEBE JSON E TRANSFORMA EM OBJETO, DEPOIS PERSISTE NA TABELA NO MYSQL
		   - STATUS 201 - CRIADO */
		
		/* - ANTES DE CRIAR UMA NOVA POSTAGEM, VERIFICA SE O TEMA EXISTE
		   - CASO EXISTA, A POSTAGEM É CRIADA COM SUCESSO RETORNANDO STATUS 200 - OK
		   - CASO NÃO EXISTA, RETORNA 400 - BAD REQUEST */
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@Valid @RequestBody Postagem postagem) {
		/* - ATUALIZANDO UM DADO
		   - EQUIVALE AO ALTER NO MYSQL
		   - RECEBE JSON E TRANSFORMA EM OBJETO, DEPOIS PERSISTE NA TABELA NO MYSQL
		   - STATUS 200 - OK */
		
		/* - VERIFICA SE O TEMA E O POST EXISTEM, ATRAVÉS DO ID
		 * - CASO EXISTAM, O POST É ATUALIZADO
		 * - SENÃO, RETORNA STATUS 404 - NOT FOUND */
		if (postagemRepository.existsById(postagem.getId())) {
			if (temaRepository.existsById(postagem.getTema().getId())) 
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
			return ResponseEntity.notFound().build();	
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePostagem(@PathVariable Long id) {
		return postagemRepository.findById(id)
				.map(resposta -> {
					postagemRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
