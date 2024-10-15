package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.model.Diccionario;
import com.curso.diccionarios.dominio.model.Palabra;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface PalabrasRepository {

    void updatePalabra(@NonNull Palabra palabra);
    void newPalabra(@NonNull Palabra palabra);
    Optional<Palabra> deletePalabra(@NonNull Palabra palabra);
    Optional<Palabra> getPalabra(@NonNull Diccionario diccionario, @NonNull String palabra);
    List<Palabra> getPalabras();

}

// KISS: Keep It Simple, Stupid ~ YAGNI: You Aren't Gonna Need It
// Queremos poder añadir un diccionario sin palabras? TO DO EL SENTIDO
// Puedo querer ir registrando Contextos de uso... para luego poder irlos aplicando a palabras? SI
// Puedo querer ir registrando Tipos Morfológicos... para luego poder irlos aplicando a palabras? SI
// Puedo querer cambiar el icono de un idioma? SI
// Voy a asignar significados fuera del contexto de una palabra? NO
