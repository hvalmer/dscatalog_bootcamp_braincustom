package com.baincustom.dscatalog.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baincustom.dscatalog.entities.Category;
import com.baincustom.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired //injeta automaticamente a dependência
	private CategoryRepository repository;
	
	//garante a integridade da transação do BD
	@Transactional(readOnly = true)
	public List<Category> findAll(){
		return repository.findAll();
	}
}
