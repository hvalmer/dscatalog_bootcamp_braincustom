package com.baincustom.dscatalog.services.exceptions;

//criando uma exceção personalizada da camada de serviço
public class DatabaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	//construtor
	public DatabaseException(String msg) {
		super(msg);
	}

}
