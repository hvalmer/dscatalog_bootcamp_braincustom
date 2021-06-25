package com.baincustom.dscatalog.services.exceptions;

//criando uma exceção personalizada da camada de serviço
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//construtor
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
