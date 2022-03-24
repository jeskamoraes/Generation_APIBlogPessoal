package com.generation.blogpessoal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
