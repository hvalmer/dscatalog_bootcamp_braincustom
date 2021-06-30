package com.baincustom.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baincustom.dscatalog.dto.ProductDTO;
import com.baincustom.dscatalog.entities.Product;
import com.baincustom.dscatalog.repositories.ProductRepository;
import com.baincustom.dscatalog.services.exceptions.DatabaseException;
import com.baincustom.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired //injeta automaticamente a dependência
	private ProductRepository repository;
	
	//garante a integridade da transação do BD
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		//chamando a busca paginada do repository
		Page<Product> list = repository.findAll(pageRequest); 
		return list.map(x -> new ProductDTO(x));
		//convertendo uma lista de Product para ProductDTO
		/**
		 * fazendo uma implementação lâmbida(x->...)
		 * .stream() - converte uma lista em funções de alta ordem
		 * c/ expressões lâmbida(x->), função map() : transf cada elemento
		 * original em outra coisa.
		 * collect - transf uma stream() em uma lista
 		 */
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);//findById vai ao BD e tras os dados do objeto procurado
		//caso o objeto Product não existir(orElseThrow), instancia(new) uma exceção
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	/**
	 * Caso o id não exista quando for salvar, vai retornar uma
	 * exceção, tendo que adicionar um try(tentar rodar)/
	 * catch(capturar) a exceção [EntityNot...]  
	 */
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);//getOne não toca no BD, instancia um objeto provisório
			//entity.setName(dto.getName());//atualizando os dados na memória
			entity = repository.save(entity);//salvando no BD
			return new ProductDTO(entity);
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
