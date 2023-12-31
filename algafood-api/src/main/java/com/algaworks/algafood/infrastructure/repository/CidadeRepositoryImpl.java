package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listAll() {
		return manager.createQuery("from Cidade", Cidade.class)
				.getResultList();
	}
	
	@Override
	public Cidade find(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@Transactional
	@Override
	public Cidade add(Cidade cidade) {
		return manager.merge(cidade);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		Cidade cidade = find(id);
		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cidade);
	}

}
