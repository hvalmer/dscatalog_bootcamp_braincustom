package com.baincustom.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.baincustom.dscatalog.entities.Category;
import com.baincustom.dscatalog.entities.Product;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	private Instant date;
	
	//lista com mais de uma categoria
	private List<CategoryDTO> categories = new ArrayList<>();
	
	
	//cosntrutores
	public ProductDTO() {
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}
	
	//construtor que recebe a entidade para ter mais opção de instanciar ProductDTO
	public ProductDTO(Product entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	
	//construtor recebendo categorias com sobrecargas de dois argumentos diferentes(...)
	public ProductDTO(Product entity, Set<Category> categories) {
		//quando chamar o construtor, instanciar o DTO e colocando os elementos na lista List<Categories>
		this(entity); //chama o construtor que recebe só entidade
		//percorrendo cj. de categorias e inserindo cada categoria nele como um novo CategoryDTO na lista de categories
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat))); //.forEach() é uma função de alta ordem
		//categories.[argumento] //this.categories [lista de categorias da classe] 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
}
