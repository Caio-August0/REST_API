package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listAll();
	Cozinha find(Long id);
	Cozinha add(Cozinha cozinha);
	void remove(Long id);
	
}
