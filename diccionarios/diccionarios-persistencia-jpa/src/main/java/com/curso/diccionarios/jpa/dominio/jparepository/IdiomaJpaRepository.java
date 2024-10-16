package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.jpa.dominio.entity.IdiomaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdiomaJpaRepository extends JpaRepository<IdiomaEntity, Integer> {
}