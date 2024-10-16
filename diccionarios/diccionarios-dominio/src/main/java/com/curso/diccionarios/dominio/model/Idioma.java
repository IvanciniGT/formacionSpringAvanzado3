package com.curso.diccionarios.dominio.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class Idioma {

    String idioma;
    String icono;
    List<Diccionario> diccionarios;

}
