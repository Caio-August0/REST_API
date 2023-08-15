package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	List<FormaPagamento> listAll();
	FormaPagamento find(Long id);
	FormaPagamento add(FormaPagamento formaPagamento);
	void remove(FormaPagamento formaPagamento);
	
}
