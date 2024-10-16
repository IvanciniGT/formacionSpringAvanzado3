package com.curso.diccionarios.jpa.dominio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipos_morfologicos")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TipoMorfologicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 10, unique = true)
    String tipo; // sm

    @Column(nullable = false, length = 100)
    String descripcion;  // Sustantivo Masculino
}
