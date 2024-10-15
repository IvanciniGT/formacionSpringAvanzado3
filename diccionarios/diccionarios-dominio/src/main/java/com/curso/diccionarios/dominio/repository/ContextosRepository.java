package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.exception.AlreadyExistsEntityException;
import com.curso.diccionarios.dominio.exception.InvalidArgumentException;
import com.curso.diccionarios.dominio.exception.NonExistentEntityException;
import com.curso.diccionarios.dominio.model.Contexto;
import com.curso.diccionarios.dominio.model.TipoMorfologico;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface ContextosRepository {

    Optional<Contexto> getContexto(@NonNull String contexto);
    List<Contexto> getContextos();
    void updateContexto(@NonNull Contexto contexto) throws InvalidArgumentException, NonExistentEntityException;
    void newContexto(@NonNull Contexto contexto) throws InvalidArgumentException, AlreadyExistsEntityException;
    Optional<Contexto> deleteContexto(@NonNull Contexto contexto);

}
