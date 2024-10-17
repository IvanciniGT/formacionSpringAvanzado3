package com.curso.diccionarios.rest.v1.controller.aspects;

import com.curso.diccionarios.rest.v1.dto.MensajeError;
import com.curso.diccionarios.service.exception.GenericServiceException;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Spring mete un proxy en TODOS los controladores
@ControllerAdvice(basePackages = "com.curso.diccionarios.rest.v1.controller")
public class ExceptionesDeLosControladores {
    // Pero si en alguna función de los controladores (lo que hay en com.curso.diccionario.rest.v1.controller)
    // Da igual la función, en CUALQUIERA
    // se lanza un error del tipo GenericServiceException,
    // Quiero que se entregue como respuesta un ResponseEntity<MensajeError> con el código 500

    @ExceptionHandler(GenericServiceException.class)
    public ResponseEntity<MensajeError> handleGenericServiceException(GenericServiceException e) {
        return ResponseEntity.status(500).body(new MensajeError(e.getMessage()));
    }

    // Si se lanza un error de tipo InvalidArgumentServiceException quiero que se entregue como respuesta un ResponseEntity<MensajeError> con el código 400
    @ExceptionHandler(InvalidArgumentServiceException.class)
    public ResponseEntity<MensajeError> handleInvalidArgumentServiceException(InvalidArgumentServiceException e) {
        return ResponseEntity.status(400).body(new MensajeError(e.getMessage()));
    }
}
