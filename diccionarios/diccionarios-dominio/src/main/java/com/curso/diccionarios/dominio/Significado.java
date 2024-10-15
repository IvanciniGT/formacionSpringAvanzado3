package com.curso.diccionarios.dominio;

import java.util.List;

public interface Significado {
    Integer getNumero();
    String getDefinicion();
    List<String> getEjemplos();
    Palabra getPalabra();
    List<Significado> getSinonimos();
    List<Contexto> getContextos();
    List<TipoMorfologico> getTiposMorfologicos();
}
