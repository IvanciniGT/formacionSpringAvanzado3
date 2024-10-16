package com.curso.diccionarios.jpa.dominio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "diccionarios")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DiccionarioEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 10, unique = true)
    String nombre;

    @ManyToOne()
    @JoinColumn(name = "idioma_id")
    IdiomaEntity idioma;

    @OneToMany(mappedBy = "diccionario")
    List<PalabraEntity> palabras;

}
