package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.dominio.model.TipoMorfologico;

import com.curso.diccionarios.jpa.dominio.entity.TipoMorfologicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMorfologicoJpaRepository extends JpaRepository<TipoMorfologicoEntity, Integer> {

}