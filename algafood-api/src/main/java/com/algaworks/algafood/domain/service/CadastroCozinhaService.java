package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha add(Cozinha cozinha) {
        return cozinhaRepository.add(cozinha);
    }

    public void remove(Long id) {
        try {
            cozinhaRepository.remove(id);
        }catch(EmptyResultDataAccessException exception) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código: %d",id));
        } catch (DataIntegrityViolationException exception) {
           throw new EntitadeEmUsoException(String.format("Cozinha %d não pode ser removida," +
                   "pois está em uso.", id));
        }

    }
}
