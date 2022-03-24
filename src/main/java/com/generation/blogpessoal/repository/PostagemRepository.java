package com.generation.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository; // POSSUI TODOS OS MÉTODOS PARA GERENCIAR O BANCO DE DADOS
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long> { // LONG IDENTIFICA A CHAVE PRIMÁRIA DA TABELA
	
}
