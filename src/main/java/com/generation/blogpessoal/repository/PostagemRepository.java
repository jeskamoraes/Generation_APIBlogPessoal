package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; // POSSUI TODOS OS MÉTODOS PARA GERENCIAR O BANCO DE DADOS
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository <Postagem, Long> { // LONG IDENTIFICA A CHAVE PRIMÁRIA DA TABELA
	//EQUIVALE AO LIKE NO MySQL --> SELECT * FROM tb_postagem WHERE titulo LIKE "%texto%";
	//MÉTODO RESPONSÁVEL PELA BUSCA DE TÍTULOS QUE CONTENHAM A PALAVRA DIGITADA
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo); 
}
