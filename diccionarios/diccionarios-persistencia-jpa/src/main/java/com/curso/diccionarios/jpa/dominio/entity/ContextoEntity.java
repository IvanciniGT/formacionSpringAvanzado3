package com.curso.diccionarios.jpa.dominio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contextos")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ContextoEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 10, unique = true)
    String contexto;
    @Column(nullable = false, length = 100)
    String descripcion;

}