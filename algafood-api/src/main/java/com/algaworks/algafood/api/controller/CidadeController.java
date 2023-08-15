package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listAll() {
        return cidadeRepository.listAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cidade> find(@PathVariable(name = "id") Long id) {
        Cidade cidade = cidadeRepository.find(id);
        if (cidade != null) {
            return ResponseEntity.ok(cidade);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(@RequestBody Cidade cidade) {
        try {
           cidade = cadastroCidadeService.add(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch(EntidadeNaoEncontradaException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> upDate(@PathVariable(name = "id") Long id, @RequestBody Cidade cidade){
        Cidade c = cidadeRepository.find(id);
        BeanUtils.copyProperties(cidade, c,"id");

        if (c != null) {
            BeanUtils.copyProperties(cidade,c,"id");
            c = cadastroCidadeService.add(c);
            return ResponseEntity.ok(c);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(name = "id") Long id){
        try{
            cadastroCidadeService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        } catch(EntitadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }





}
