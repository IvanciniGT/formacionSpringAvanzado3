package com.curso.diccionarios.dominio.model;

import java.util.List;

public interface Diccionario {
    String getNombre();
    Idioma getIdioma();
    List<Palabra> getPalabras();
}
