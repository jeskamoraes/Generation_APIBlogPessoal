package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/postagens")
//LIBERAR ORIGENS (SERVIDORES DO BACK E FRONT DIFERENTES)
//NO LUGAR DO * COLOCA - SE O LINK
//ALLOWEDHEADRES REFERE-SE AO TOKEN DE ACESSO RELACIONADO AO SECURITY
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class PostagemController {
	
	@Autowired //TRANSFERE A RESPONSABILIDADE DE CRIAR E INSTANCIAR O OBJETO PARA O SPRING
	private PostagemRepository postagemRepository; //INSTANCIANDO OBJETO DA INTERFACE
	
	@GetMapping // ResponseEntity, PORQUE DEVOLVE UM STATUS HTTP
	public ResponseEntity<List<Postagem>> getAll() { //getAll É NOME QUE DEMOS PARA O MÉTODO
		return ResponseEntity.ok(postagemRepository.findAll());
		// ESSE MÉTODO EQUIVALE AO 'SELECT * FROM tb_postagens'
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Postagem> getById(@PathVariable Long id) { // PEGA O VALOR DIGITADO NA URL E ARMANEZA NA VARIÁVEL 'LONG ID'
	//LAMBDA
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) //MAP É UM OBJETO DO TIPO OPTIONAL
				.orElse(ResponseEntity.notFound().build());
				
		//EQUIVALE AO SELECT * FROM tb_postagens WHERE id = 1
		//lambda = verifica se o id digitado existe ou se é nulo
		//se existe traz o conteúdo, senão retorna uma mensagem
	}
	
	@GetMapping("/titulo/{titulo}") 
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@Valid @RequestBody Postagem postagem) { 
		//CRIANDO UM NOVO DADO
		//EQUIVALE AO INSERT TO NO MYSQL
		//RECEBE JSON E TRANSFORMA EM OBJETO, DEPOIS PERSISTE NA TABELA NO MYSQL
		//STATUS 201 - CRIADO
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping("/{id}")	
	public ResponseEntity<Postagem> putPostagem(@PathVariable("id") Long id, @Valid @RequestBody Postagem postagem) { 
		//ATUALIZANDO UM DADO
		//EQUIVALE AO ALTER NO MYSQL
		//RECEBE JSON E TRANSFORMA EM OBJETO, DEPOIS PERSISTE NA TABELA NO MYSQL
		//STATUS 200 - OK
		Optional<Postagem> resposta = postagemRepository.findById(id);
		if(resposta.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}
		else 
			return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}") //MÉTODO QUE NÃO RETORNA NADA
	public ResponseEntity<Postagem> deletePostagem(@PathVariable Long id) {
		
		Optional<Postagem> resposta = postagemRepository.findById(id);
		if(resposta.isPresent()) {
			postagemRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
			else
				return ResponseEntity.notFound().build();
	}
}
