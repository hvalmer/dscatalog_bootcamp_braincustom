package com.baincustom.dscatalog.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baincustom.dscatalog.entities.Category;
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
	public ResponseEntity<List <Category>> findAll(){
		List<Category> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
}
