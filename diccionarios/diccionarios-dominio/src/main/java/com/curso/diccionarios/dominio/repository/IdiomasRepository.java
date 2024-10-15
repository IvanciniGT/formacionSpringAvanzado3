package com.curso.diccionarios.dominio.repository;

import com.curso.diccionarios.dominio.exception.AlreadyExistsEntityException;
import com.curso.diccionarios.dominio.exception.InvalidArgumentException;
import com.curso.diccionarios.dominio.exception.NonExistentEntityException;
import com.curso.diccionarios.dominio.model.Idioma;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface IdiomasRepository {

    Optional<Idioma> getIdioma(@NonNull String idioma);
    List<Idioma> getIdiomas();
    void updateIdioma(@NonNull Idioma idioma) throws InvalidArgumentException, NonExistentEntityException;
    void newIdioma(@NonNull Idioma idioma) throws InvalidArgumentException, AlreadyExistsEntityException;
    Optional<Idioma> deleteIdioma(@NonNull Idioma idioma);

}
