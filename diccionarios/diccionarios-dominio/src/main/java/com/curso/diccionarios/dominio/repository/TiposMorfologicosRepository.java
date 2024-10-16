package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.model.TipoMorfologico;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface TiposMorfologicosRepository {

    Optional<TipoMorfologico> getTipoMorfologico(@NonNull String tipoMorfologico);
    List<TipoMorfologico> getTiposMorfologicos();

}
