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
@EqualsAndHashCode
public class SignificadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer internalId;

    @Column(nullable = false, length = 50, unique = true)
    String id;

    @Column(nullable = false)
    Integer numero;

    @Column(nullable = false, length = 500, unique = true)
    String definicion;

    @ManyToOne
    @JoinColumn(name = "palabra_id", nullable = false)
    PalabraEntity palabra;

    @OneToMany(mappedBy = "significado")
    List<SignificadoEntity> sinonimos;

    @ManyToMany
    @JoinTable(
            name = "significados_contextos",
            joinColumns = @JoinColumn(name = "significado_id"),
            inverseJoinColumns = @JoinColumn(name = "contexto_id")
    )
    List<ContextoEntity> contextos;

    @ManyToMany
    @JoinTable(
            name = "significados_tipos_morfologicos",
            joinColumns = @JoinColumn(name = "significado_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_morfologico_id")
    )
    List<TipoMorfologicoEntity> tiposMorfologicos;

    @ElementCollection
    @CollectionTable(
            name = "significados_ejemplos",
            joinColumns = @JoinColumn(name = "significado_id")
    )
    List<String> ejemplos;

}
