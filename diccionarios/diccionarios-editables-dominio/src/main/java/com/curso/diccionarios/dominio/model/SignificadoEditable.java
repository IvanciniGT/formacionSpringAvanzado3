package com.curso.diccionarios.dominio.model;

import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@ToString
@Builder

public class SignificadoEditable {
    String id;
    Integer numero;

    @Setter
    String definicion;

    List<String> ejemplos;

    PalabraEditable palabra;

    List<Significado> sinonimos;

    List<Contexto> contextos;

    List<TipoMorfologico> tiposMorfologicos;
}
