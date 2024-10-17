package com.curso.diccionarios.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextoRestV1DTO {
    String contexto;
    String descripcion;
}
