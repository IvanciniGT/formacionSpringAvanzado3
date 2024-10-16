package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.exception.AlreadyExistsEntityException;
import com.curso.diccionarios.dominio.exception.InvalidArgumentException;
import com.curso.diccionarios.dominio.exception.NonExistentEntityException;
import com.curso.diccionarios.dominio.model.*;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface PalabrasEditableRepository {

    Optional<PalabraEditable> getPalabra(@NonNull Diccionario diccionario, @NonNull String palabra);
    List<PalabraEditable> getPalabras();
    void updatePalabra(@NonNull PalabraEditable palabra) throws InvalidArgumentException, NonExistentEntityException;
    PalabraEditable newPalabra(@NonNull Diccionario diccionario, @NonNull String palabra) throws InvalidArgumentException, AlreadyExistsEntityException;
    Optional<PalabraEditable> deletePalabra(@NonNull String palabra);

    void addVariante(@NonNull PalabraEditable palabra, @NonNull String variante, @NonNull TipoMorfologico contexto) throws InvalidArgumentException;
    Optional<Variante> removeVariante(@NonNull PalabraEditable palabra, @NonNull String variante);

    SignificadoEditable addSignificado(@NonNull PalabraEditable palabra, @NonNull String definicion) throws InvalidArgumentException;
    void updateSignificado(@NonNull SignificadoEditable significado) throws InvalidArgumentException, NonExistentEntityException;
    Optional<SignificadoEditable> removeSignificado(@NonNull PalabraEditable palabra, @NonNull SignificadoEditable significado);
    List<SignificadoEditable> rearrangeSignificados(@NonNull PalabraEditable palabra, @NonNull List<SignificadoEditable> significados);

    void addEjemplo(@NonNull String ejemplo);
    Optional<String> removeEjemplo(@NonNull String ejemplo);

    void addSinonimo(@NonNull Significado sinonimo);
    Optional<Significado> removeSinonimo(@NonNull Significado sinonimo);

    void addContexto(@NonNull String contexto);
    Optional<Contexto> removeContexto(@NonNull String contexto);

    void addTipoMorfologico(@NonNull String tipoMorfologico);
    void removeTipoMorfologico(@NonNull String tipoMorfologico);

}

// KISS: Keep It Simple, Stupid ~ YAGNI: You Aren't Gonna Need It
// Queremos poder añadir un diccionario sin palabras? TO DO EL SENTIDO
// Puedo querer ir registrando Contextos de uso... para luego poder irlos aplicando a palabras? SI
// Puedo querer ir registrando Tipos Morfológicos... para luego poder irlos aplicando a palabras? SI
// Puedo querer cambiar el icono de un idioma? SI
// Voy a asignar significados fuera del contexto de una palabra? NO
