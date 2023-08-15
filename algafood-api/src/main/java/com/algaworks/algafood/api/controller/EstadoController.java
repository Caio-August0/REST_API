package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping("/{id}")
    public ResponseEntity<Estado> find(@PathVariable(name = "id")Long id) {
         Estado e = estadoRepository.find(id);
        if (e != null) {
            return ResponseEntity.ok(e);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Estado> add(@RequestBody Estado estado){
            if (!estado.getNome().equals(" "))  {
                return ResponseEntity.status(HttpStatus.CREATED).body(cadastroEstadoService.add(estado));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Estado> upDate(@PathVariable(name = "id") Long id, @RequestBody Estado estado){
         Estado e = estadoRepository.find(id);
         if (e != null) {
             BeanUtils.copyProperties(estado,e,"id");
             cadastroEstadoService.add(e);
             return ResponseEntity.ok(e);
         }
         return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable(name="id") Long id){
        try {
            cadastroEstadoService.remove(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch(EntidadeNaoEncontradaException exception) {
            return ResponseEntity.notFound().build();
        } catch(EntitadeEmUsoException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
    }

}
