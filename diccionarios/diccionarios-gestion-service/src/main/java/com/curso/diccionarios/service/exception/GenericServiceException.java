package com.curso.diccionarios.service.exception;

public class GenericServiceException extends RuntimeException {
    public GenericServiceException(String bbddNoDisponible) {
        super("Error en la capa de servicio: " + bbddNoDisponible);
    }
}
