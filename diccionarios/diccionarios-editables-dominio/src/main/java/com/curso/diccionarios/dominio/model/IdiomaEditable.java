package com.curso.diccionarios.dominio.model;

import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
public class IdiomaEditable {

    String idioma;
    @Setter
    String icono;
    List<Diccionario> diccionarios;

}
