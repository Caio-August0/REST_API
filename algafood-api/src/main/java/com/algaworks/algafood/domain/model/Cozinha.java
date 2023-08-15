package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
//@JsonRootName("cozinha do Caio") // JSON NOME DA RAIZ podemos customizar o nome da raiz(classe)
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	//@JsonProperty(value= "Identificador") // Usamos nas propriedades da Classe
	// O atributo "id" será representado como "Identificador"
	private Long id;

	//@JsonIgnore // Customiza a Representação do Recurso que está em formato JSON
	// O atributo "nome" não será representado ( nãoimpresso na tela)
	//@JsonProperty(value= "Título") // o @JsonProperty tem preferência sobre o @JsonIgnore
	@Column(nullable = false)
	private String nome;

}
