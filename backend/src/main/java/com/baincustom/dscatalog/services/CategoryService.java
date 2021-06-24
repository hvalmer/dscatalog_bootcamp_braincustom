package com.baincustom.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baincustom.dscatalog.dto.CategoryDTO;
import com.baincustom.dscatalog.entities.Category;
import com.baincustom.dscatalog.repositories.CategoryRepository;
import com.baincustom.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired //injeta automaticamente a dependência
	private CategoryRepository repository;
	
	//garante a integridade da transação do BD
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		//convertendo uma lista de Category para CategoryDTO
		/**
		 * fazendo uma implementação lâmbida(x->...)
		 * .stream() - converte uma lista em funções de alta ordem
		 * c/ expressões lâmbida(x->), função map() : transf cada elemento
		 * original em outra coisa.
		 * collect - transf uma stream() em uma lista
 		 */
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		//caso o objeto Category não existir(orElseThrow), instancia(new) uma exceção
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
		return new CategoryDTO(entity);
	}
}
