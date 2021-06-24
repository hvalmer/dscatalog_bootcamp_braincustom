package com.baincustom.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.baincustom.dscatalog.dto.CategoryDTO;
import com.baincustom.dscatalog.services.CategoryService;

/**
 * 
 * @author hvgoyana
 * Recursos da aplicação implementados
 * pelos Controllers
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired //injeta automaticamente a dependência
	private CategoryService service;
	
	//criando os endpoints
	@GetMapping
	public ResponseEntity<List <CategoryDTO>> findAll(){
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	//buscando por id
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	//inserindo novo objeto
	@PostMapping //no Rest, para inserir um novo recurso usa-se o Post
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
		dto = service.insert(dto);
		//inserindo no cabecalho da responsta no Postman o location do {id}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();	
		return ResponseEntity.created(uri).body(dto);
	}
}
