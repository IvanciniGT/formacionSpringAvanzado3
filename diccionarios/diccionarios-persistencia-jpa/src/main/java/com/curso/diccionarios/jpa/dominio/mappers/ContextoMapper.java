package com.curso.diccionarios.jpa.dominio.mappers;

import com.curso.diccionarios.dominio.model.ContextoEditable;
import com.curso.diccionarios.jpa.dominio.entity.ContextoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContextoMapper {
    ContextoEditable entityToModel(ContextoEntity contextoEntity);

    ContextoEntity modelToEntity(ContextoEditable contextoEntity);
}
