package com.curso.diccionarios.service.mapper;

import com.curso.diccionarios.dominio.model.ContextoEditable;
import com.curso.diccionarios.service.dto.ContextoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContextosServiceMapper {
    ContextoDTO modelt2DTO(ContextoEditable contextoEditable);
}
