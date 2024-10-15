package com.curso.diccionarios.dominio;

import java.util.List;

public interface Palabra {
    String getPalabra();
    Diccionario getDiccionario();
    List<Variante> getVariantes();
    List<Significado> getSignificados();
}
