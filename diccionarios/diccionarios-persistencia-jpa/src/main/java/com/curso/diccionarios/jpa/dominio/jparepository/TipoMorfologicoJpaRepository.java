package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.dominio.model.TipoMorfologico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMorfologicoJpaRepository extends JpaRepository<TipoMorfologico, Integer> {
}