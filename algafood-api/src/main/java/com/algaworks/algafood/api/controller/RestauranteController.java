package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    public RestauranteRepository restauranteRepository;
    @Autowired
    public CadastroRestauranteService cadastroRestauranteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listAll() {
        return restauranteRepository.listAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurante> find(@PathVariable(name = "id") Long id) {
        Restaurante restaurante = restauranteRepository.find(id);
        if (restaurante != null) {
            return ResponseEntity.status(HttpStatus.OK).body(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody Restaurante restaurante) { // <?> = Wild Card
        try {
            restaurante = cadastroRestauranteService.add(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upDate(@PathVariable(name = "id") Long id, @RequestBody Restaurante restaurante) {
        Restaurante r = restauranteRepository.find(id);
        try {
            if (r != null) {
                BeanUtils.copyProperties(restaurante, r, "id");
                r = cadastroRestauranteService.add(r);
                return ResponseEntity.ok(r);
            }

            return ResponseEntity.notFound().build();

        } catch (EntidadeNaoEncontradaException exception) {
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> upDate(@PathVariable(name = "id") Long id, @RequestBody Map<String,Object> campos){
        Restaurante restaurante = restauranteRepository.find(id);

        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }
        merge(campos, restaurante);
        return upDate(id, restaurante);
    }

    private static void merge(Map<String, Object> dados, Restaurante restaurante) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante r = objectMapper.convertValue(dados, Restaurante.class);


        dados.forEach((nomePropriedade, valorPropriedade) -> {

        Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
        field.setAccessible(true);

        Object novoValor = ReflectionUtils.getField(field,restaurante);

        ReflectionUtils.setField(field,restaurante,novoValor);
      //  System.out.println(nomePropriedade + " = " + valorPropriedade +" = " + novoValor);
        });
    }
}
