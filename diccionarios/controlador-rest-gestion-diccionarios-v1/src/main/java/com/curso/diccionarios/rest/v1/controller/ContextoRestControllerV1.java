package com.curso.diccionarios.rest.v1.controller;

import com.curso.diccionarios.rest.v1.dto.ContextoRestV1DTO;
import com.curso.diccionarios.rest.v1.mapper.ContextoMapper;
import com.curso.diccionarios.service.ContextoServicio;

import com.curso.diccionarios.service.dto.ContextoDTO;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                 // PURO LENGUAJE DECLARATIVO
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContextoRestControllerV1 {

    private final ContextoServicio contextoServicio;
    private final ContextoMapper contextoMapper;

    @GetMapping("/contexto")
    public ResponseEntity<List<ContextoRestV1DTO>> obtenerContextos() {
        List<ContextoDTO> resultadoDelServicio = contextoServicio.getContextos();
        List<ContextoRestV1DTO> resultado = resultadoDelServicio.stream().map( contextoMapper:: serviceDTO2controllerDTO ).toList();
        return ResponseEntity.ok(resultado); // Codigo 200
    }
    // ResponseEntity es una Caja que lleva dentro un Objeto (que será transformado a JSON por Spring)
    // y un código de estado HTTP

    // Implementamos el post para crear un contexto
    @PostMapping("/contexto")
    public ResponseEntity<ContextoRestV1DTO> crearContexto(@RequestBody ContextoRestV1DTO contexto) throws InvalidArgumentServiceException {
                                                            // El JSON que se mande en el cuerpo(body) del requestHTTP se transformará en un objeto de tipo ContextoRestV1DTO
        ContextoDTO contextoDTO = contextoMapper.controllerDTO2serviceDTO(contexto);
        ContextoDTO resultadoDelServicio = contextoServicio.crearContexto(contextoDTO);
        ContextoRestV1DTO resultado = contextoMapper.serviceDTO2controllerDTO(resultadoDelServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado); // Codigo 201
    }
}
