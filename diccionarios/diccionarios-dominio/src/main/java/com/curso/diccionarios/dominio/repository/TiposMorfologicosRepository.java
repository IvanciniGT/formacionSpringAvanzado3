package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.model.TipoMorfologico;

import java.util.List;
import java.util.Optional;

public interface TiposMorfologicosRepository {

    Optional<TipoMorfologico> getTipoMorfologico(String tipoMorfologico);
    List<TipoMorfologico> getTiposMorfologicos();
    void updateTipoMorfologico(TipoMorfologico tipoMorfologico);
    void newTipoMorfologico(TipoMorfologico tipoMorfologico);
    void deleteTipoMorfologico(TipoMorfologico tipoMorfologico);

}
