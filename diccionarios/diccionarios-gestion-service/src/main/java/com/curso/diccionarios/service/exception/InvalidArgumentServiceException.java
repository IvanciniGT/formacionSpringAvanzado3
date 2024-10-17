package com.curso.diccionarios.service.exception;

public class InvalidArgumentServiceException extends Throwable {
    public InvalidArgumentServiceException(String mensaje) {
        super("Error en la capa de servicio: " + mensaje);
    }
}
