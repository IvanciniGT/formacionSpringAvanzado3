package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.model.Palabra;

import java.util.List;
import java.util.Optional;

public interface PalabrasRepository {

    void updatePalabra(Palabra palabra);
    void newPalabra(Palabra palabra);
    void deletePalabra(Palabra palabra);
    Optional<Palabra> getPalabra(String palabra);
    List<Palabra> getPalabras();
}
