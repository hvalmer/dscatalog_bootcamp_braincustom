package com.baincustom.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.baincustom.dscatalog.services.exceptions.DatabaseException;
import com.baincustom.dscatalog.services.exceptions.ResourceNotFoundException;

/**
 * @ControllerAdvice
 * permite que a classe abaixo intercepte alguma excessão
 * que acontecer na camada de Resource(Recursos) do controlador
 * Rest, ele trata a excessão agora 
 */
@ControllerAdvice
public class ResourceExceptionHandler {

	/**
	 * 	esse método vai ser uma resposta de requisição
		onde o payload da resposta(conteúdo) vai ser
		um objeto do tipo StandardError
	 */
	//configurando a resposta padrão de Not Found
	@ExceptionHandler(ResourceNotFoundException.class) //sabe o tipo de exceção que vai interceptar
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		//declarando um status
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());//hora e data atual
		err.setStatus(status.value());//erro 404
		err.setError("Resource not found!");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	//mostrando o erro do delete, caso o id não exista mais
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		
		//declarando um status
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());//hora e data atual
		err.setStatus(status.value());//erro 400
		err.setError("Database exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
}
