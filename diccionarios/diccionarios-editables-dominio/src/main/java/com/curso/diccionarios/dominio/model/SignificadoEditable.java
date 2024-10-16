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
/*

    Significados
    id | definicion | contexto | tipo_morfologico | ejemplos | sinonimos
    1  | "def1"     | "cont1"  | "tipo1"          | "ej1"   | "sin1"
    2  | "def2"     | "cont2"  | "tipo2"          | "ej2"   | "sin2"
    3  | "def3"     | "cont3"  | "tipo3"          | "ej3"   | "sin3"

    Sinonimos                   Significados -< Sinonimos
                                    |              v
                                    +--------------+
    1,2
    2,3



*/