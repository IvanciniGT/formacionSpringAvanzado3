package com.curso.diccionarios.jpa.dominio.entity;

import com.curso.diccionarios.dominio.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Optional;


@Entity
@Table(name = "idiomas")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PalabraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 10, unique = true)
    String palabra;

    @ManyToOne()
    @JoinColumn(name = "diccionario_id")
    DiccionarioEntity diccionario;

    @OneToMany(mappedBy = "palabra")
    List<VarianteEntity> variantes;

    @OneToMany(mappedBy = "palabra")
    List<SignificadoEntity> significados;

}
