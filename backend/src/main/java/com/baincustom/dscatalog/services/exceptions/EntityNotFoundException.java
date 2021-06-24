package com.baincustom.dscatalog.services.exceptions;

//criando uma exceção personalizada da camada de serviço
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//construtor
	public EntityNotFoundException(String msg) {
		super(msg);
	}

}
