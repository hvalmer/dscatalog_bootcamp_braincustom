package com.baincustom.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baincustom.dscatalog.dto.CategoryDTO;
import com.baincustom.dscatalog.entities.Category;
import com.baincustom.dscatalog.repositories.CategoryRepository;
import com.baincustom.dscatalog.services.exceptions.DatabaseException;
import com.baincustom.dscatalog.services.exceptions.ResourceNotFoundException;

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
		Optional<Category> obj = repository.findById(id);//findById vai ao BD e tras os dados do objeto procurado
		//caso o objeto Category não existir(orElseThrow), instancia(new) uma exceção
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	/**
	 * Caso o id não exista quando for salvar, vai retornar uma
	 * exceção, tendo que adicionar um try(tentar rodar)/
	 * catch(capturar) a exceção [EntityNot...]  
	 */
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);//getOne não toca no BD, instancia um objeto provisório
			entity.setName(dto.getName());//atualizando os dados na memória
			entity = repository.save(entity);//salvando no BD
			return new CategoryDTO(entity);
		}
		catch(EntityNotFoundException e) {
			//lançando a minha exceção personalizada
			throw new ResourceNotFoundException("Id not found!" + id);
		}
	}

	public void delete(Long id) {
		//tratando uma exceção, caso um id que não exista
		try { 
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found!" + id);
		}
		/**
		 * Integridade referencial: 
		 * uma execeção do tipo catch, nesse caso de deletar a tabela do banco,
		 * dando o erro específico 
		 */
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
