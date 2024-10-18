package com.curso.diccionarios.rest.v1.controller;

import com.curso.diccionarios.rest.v1.dto.ContextoRestV1DTO;
import com.curso.diccionarios.rest.v1.mapper.ContextoTestV1Mapper;
import com.curso.diccionarios.service.ContextoServicio;

import com.curso.diccionarios.service.dto.ContextoDTO;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                 // PURO LENGUAJE DECLARATIVO
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContextoRestControllerV1 {

    private final ContextoServicio contextoServicio;
    private final ContextoTestV1Mapper contextoTestV1Mapper;

    @GetMapping("/contexto")
    public ResponseEntity<List<ContextoRestV1DTO>> obtenerContextos() {
        List<ContextoDTO> resultadoDelServicio = contextoServicio.getContextos();
        List<ContextoRestV1DTO> resultado = resultadoDelServicio.stream().map( contextoTestV1Mapper:: serviceDTO2controllerDTO ).toList();
        return ResponseEntity.ok(resultado); // Codigo 200
    }
    // ResponseEntity es una Caja que lleva dentro un Objeto (que será transformado a JSON por Spring)
    // y un código de estado HTTP

    // Implementamos el post para crear un contexto
    @PostMapping("/contexto")
    // A esta función solo le deben poder llamar EDITORES DE DICCIONARIOS!
    @PreAuthorize("hasRole('EDITOR_DICCIONARIOS')") // LENGUAJE DECLARATIVO
    //@RolesAllowed("EDITOR_DICCIONARIOS") // Listo.. De donde sale esta información ? ME LA PELA !
    // Eso funcionará siempre y cuando me permitan usar esta anotación
    //@Secured("EDITOR_DICCIONARIOS") // Esto es lo mismo que la anotación de arriba
    // Eso funcionará siempre y cuando me permitan usar esta anotación
    //@PreAuthorize("isAuthenticated()")//  Dentro usamos un lenguaje llamado SPEL (Spring Expression Language.. muy potente
    //@PreAuthorize("hasRole('EDITOR_DICCIONARIOS')") // Esto es lo mismo que las anotaciones de arriba
    // Eso funcionará siempre y cuando me permitan usar esta anotación... que va en conjunto con otra:
    //@PostAuthorize() // Que el objeto que se devuelve tiene una atributo propietario igual al id del usuario autenticado
    public ResponseEntity<ContextoRestV1DTO> crearContexto(@RequestBody ContextoRestV1DTO contexto) throws InvalidArgumentServiceException {
                                                            // El JSON que se mande en el cuerpo(body) del requestHTTP se transformará en un objeto de tipo ContextoRestV1DTO
        ContextoDTO contextoDTO = contextoTestV1Mapper.controllerDTO2serviceDTO(contexto);
        ContextoDTO resultadoDelServicio = contextoServicio.crearContexto(contextoDTO);
        ContextoRestV1DTO resultado = contextoTestV1Mapper.serviceDTO2controllerDTO(resultadoDelServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado); // Codigo 201
    }
}
