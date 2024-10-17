package com.curso.diccionarios.rest.v1.mapper;

import com.curso.diccionarios.rest.v1.dto.ContextoRestV1DTO;
import com.curso.diccionarios.service.dto.ContextoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContextoMapper {
    ContextoRestV1DTO serviceDTO2controllerDTO(ContextoDTO contextoDTO);

    ContextoDTO controllerDTO2serviceDTO(ContextoRestV1DTO contexto);
}
