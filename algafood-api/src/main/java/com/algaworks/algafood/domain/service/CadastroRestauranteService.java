package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class CadastroRestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Restaurante add(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();// restaurante>> cozinha>> id_cozinha
        Cozinha cozinha = cozinhaRepository.find(cozinhaId);
        if (cozinha == null){
            throw new EntidadeNaoEncontradaException(String.format
                    ("Não existe cadastro de cozinha com o código %d: ", cozinhaId));
        }
        return restauranteRepository.add(restaurante);
    }

}
