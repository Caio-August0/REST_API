package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository {

	List<Restaurante> listAll();
	Restaurante find(Long id);
	Restaurante add(Restaurante restaurante);
	void remove(Restaurante restaurante);
	
}
