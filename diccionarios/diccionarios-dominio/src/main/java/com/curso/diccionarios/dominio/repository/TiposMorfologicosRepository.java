package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.exception.AlreadyExistsEntityException;
import com.curso.diccionarios.dominio.exception.InvalidArgumentException;
import com.curso.diccionarios.dominio.exception.NonExistentEntityException;
import com.curso.diccionarios.dominio.model.TipoMorfologico;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface TiposMorfologicosRepository {

    Optional<TipoMorfologico> getTipoMorfologico(@NonNull String tipoMorfologico);
    List<TipoMorfologico> getTiposMorfologicos();
    void updateTipoMorfologico(@NonNull TipoMorfologico tipoMorfologico) throws InvalidArgumentException, NonExistentEntityException;
    void newTipoMorfologico(@NonNull TipoMorfologico tipoMorfologico) throws AlreadyExistsEntityException, InvalidArgumentException;
    Optional<TipoMorfologico> deleteTipoMorfologico(@NonNull TipoMorfologico tipoMorfologico);

}
