package com.curso.diccionarios.jpa.dominio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "idiomas")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class IdiomaEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 10, unique = true)
    String idioma;

    @Column(nullable = false, length = 100)
    String icono;

    @OneToMany(mappedBy = "idioma")
    List<DiccionarioEntity> diccionarios;

}
