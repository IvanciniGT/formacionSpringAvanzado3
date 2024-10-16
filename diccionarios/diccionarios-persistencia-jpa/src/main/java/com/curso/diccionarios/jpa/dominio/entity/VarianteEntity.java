package com.curso.diccionarios.jpa.dominio.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variantes")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VarianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 25, unique = true)
    private String variante;

    @ManyToOne()
    @JoinColumn(name = "tipo_morfologico_id")
    private TipoMorfologicoEntity tipoMorfologico;

}
