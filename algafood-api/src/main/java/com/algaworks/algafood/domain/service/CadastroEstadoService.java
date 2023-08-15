package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class CadastroEstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado add(Estado estado) {
        return estadoRepository.add(estado);
    }

    public void remove(Long id) {
        try{
            estadoRepository.remove(id);
        } catch(EmptyResultDataAccessException exception) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com código: %d",id));
        } catch(DataIntegrityViolationException exception) {
            throw new EntitadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido, pois está em uso", id));
        }
    }


}
