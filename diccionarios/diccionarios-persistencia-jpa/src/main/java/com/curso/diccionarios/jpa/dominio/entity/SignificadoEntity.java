package com.curso.diccionarios.jpa.dominio.entity;

import com.curso.diccionarios.dominio.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;


@Entity
@Table(name = "significados")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SignificadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer internalId;

    @Column(nullable = false, length = 50, unique = true)
    private String id; // AUTOGENERADO UUID

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 500, unique = true)
    private String definicion;

    @ManyToOne
    @JoinColumn(name = "palabra_id", nullable = false)
    private PalabraEntity palabra;
/*
    @ManyToMany(mappedBy = "significado")
    @JoinTable(
            name = "significados_sinonimos",
            joinColumns = @JoinColumn(name = "significado_id"),
            inverseJoinColumns = @JoinColumn(name = "sinonimo_id")
    )
    private List<SignificadoEntity> sinonimos;
*/
    @ManyToMany
    @JoinTable(
            name = "significados_contextos",
            joinColumns = @JoinColumn(name = "significado_id"),
            inverseJoinColumns = @JoinColumn(name = "contexto_id")
    )
    private List<ContextoEntity> contextos;

    @ManyToMany
    @JoinTable(
            name = "significados_tipos_morfologicos",
            joinColumns = @JoinColumn(name = "significado_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_morfologico_id")
    )
    private List<TipoMorfologicoEntity> tiposMorfologicos;

    @ElementCollection // (Crear una tabla 1-n) de forma muy cómoda
    @CollectionTable(
            name = "significados_ejemplos",
            joinColumns = @JoinColumn(name = "significado_id")
    )
    private List<String> ejemplos;

}
