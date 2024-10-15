package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.model.Contexto;
import com.curso.diccionarios.dominio.model.TipoMorfologico;

import java.util.List;
import java.util.Optional;

public interface ContextosRepository {

    Optional<Contexto> getContexto(String contexto);
    List<Contexto> getContextos();
    void updateContexto(Contexto contexto);
    void newContexto(Contexto contexto);
    void deleteContexto(Contexto contexto);

}
