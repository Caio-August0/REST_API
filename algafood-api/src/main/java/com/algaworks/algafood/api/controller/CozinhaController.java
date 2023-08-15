package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntitadeEmUsoException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas") //, produces = MediaType.APPLICATION_JSON_VALUE)
public class  CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping(value = "wrapperClassXml", produces = MediaType.APPLICATION_XML_VALUE) // Utiliza a Classe para customizar a list
	public CozinhasXmlWrapper listXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.listAll());
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)  //produz um tipo de arquivo XML
	public List<Cozinha> list2() {
		System.out.println("LISTAR 2");
		return cozinhaRepository.listAll();
	}
	
	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE) //produz um tipo de arquivo Json
	public List<Cozinha> list1() {
		System.out.println("LISTAR 1");
		return cozinhaRepository.listAll();
	}

	@GetMapping("/{id}") // Colocamos entre chaves pq é path varable
	public ResponseEntity <Cozinha> find(@PathVariable(name = "id") Long id) {// Teremos um BIND, ou seja, a nossa PATH VARIABLE será vinculado ao Id
		Cozinha cozinha =  cozinhaRepository.find(id);
		if (cozinha != null) {
			return ResponseEntity.status(HttpStatus.OK ).body(cozinha);
		}
		return ResponseEntity.notFound().build();
	}
	/* @PathVariable vincula a variável PATH com o ID
	quando recebermos uma consulta Singleton Resource com GET,
	o valor passado na URI, será vinculado ao parâmetro ID.*/

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //O método POST irá criar um recurso dentro da Coleção Cozinha
	@ResponseStatus(HttpStatus.CREATED) // defini o status de resposta HTTP
	public Cozinha add(@RequestBody Cozinha cozinha){
	// @RequestBody vai receber o corpo da requisição e fazer um BIND automaticamente com a instância gerada pelo Framework,
	// ou seja, ela vai vincular as propriedades do corpo da requisição nas propriedades do objeto Cozinha.
		 return cadastroCozinhaService.add(cozinha);
	}
	@PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity upDate(@PathVariable(name = "id") Long id, @RequestBody Cozinha cozinha) {
		Cozinha c = cozinhaRepository.find(id);
		if(c != null) {// caso passa um ID que não existe no Banco de Dados
			BeanUtils.copyProperties(cozinha,c,"id"); // Ignora a copia do parametro ID
			// Equivale: cozinhaAtual.setNome(cozinha.getNome()); ou cozinhaAtual.setId(cozinha.getID())
			cadastroCozinhaService.add(c);
			return ResponseEntity.ok(c);// ok(c) = status(HttpStatus.OK).body(c);
		}
		return ResponseEntity.notFound().build();
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remove(@PathVariable(name = "id") Long id) {
		try {
			cadastroCozinhaService.remove(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeNaoEncontradaException e){
			return ResponseEntity.notFound().build();
		} catch(EntitadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
