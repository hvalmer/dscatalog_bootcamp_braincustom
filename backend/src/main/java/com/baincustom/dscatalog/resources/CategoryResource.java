package com.baincustom.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baincustom.dscatalog.entities.Category;

/**
 * 
 * @author hvgoyana
 * Recursos da aplicação implementados
 * pelos Controllers
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	//criando os endpoints
	@GetMapping
	public ResponseEntity<List <Category>> findAll(){
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		return ResponseEntity.ok().body(list);
	}
}
