package com.baincustom.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.baincustom.dscatalog.services.exceptions.EntityNotFoundException;

/**
 * @ControllerAdvice
 * permite que a classe abaixo intercepte alguma excessão
 * que acontecer na camada de Resource(Recursos) do controlador
 * Rest, ele trata a excessão agora 
 */
@ControllerAdvice
public class ResourceExceptionHandler {

	/**
	 * 
	 * @param e
	 * @param request
	 * @return
	 * 
	 * 	esse método vai ser uma resposta de requisição
		onde o payload da resposta(conteúdo) vai ser
		um objeto do tipo StandardError
	 */
	//configurando a resposta padrão de NOt Found
	@ExceptionHandler(EntityNotFoundException.class) //sabe o tipo de excessão que vai interceptar
	public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());//hora e data atual
		err.setStatus(HttpStatus.NOT_FOUND.value());//erro 404
		err.setError("Resource not found!");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
