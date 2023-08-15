package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listAll();
	Cidade find(Long id);
	Cidade add(Cidade cidade);
	void remove(Long id);
	
}
