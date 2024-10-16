package com.curso.diccionarios.dominio.model;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class TipoMorfologico {

    String tipo; // sm
    String descripcion;  // Sustantivo Masculino
}
