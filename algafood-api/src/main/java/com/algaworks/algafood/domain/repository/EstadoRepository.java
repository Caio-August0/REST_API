package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listAll();
	Estado find(Long id);
	Estado add(Estado estado);
	void remove(Long id);
	
}
