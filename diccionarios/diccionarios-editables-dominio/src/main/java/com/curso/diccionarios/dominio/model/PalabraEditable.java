package com.curso.diccionarios.dominio.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@ToString
@Builder

public class PalabraEditable  {
    String palabra;
    Diccionario getDiccionario;

    List<Variante> variantes;

    List<SignificadoEditable> significados;
}
