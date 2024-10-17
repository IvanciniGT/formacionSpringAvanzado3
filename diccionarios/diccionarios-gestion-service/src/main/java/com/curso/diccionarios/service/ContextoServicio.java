package com.curso.diccionarios.service;

import com.curso.diccionarios.service.dto.ContextoDTO;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;

import java.util.List;

public interface ContextoServicio {

    List<ContextoDTO> getContextos();
    ContextoDTO crearContexto(ContextoDTO contexto) throws InvalidArgumentServiceException;

}
