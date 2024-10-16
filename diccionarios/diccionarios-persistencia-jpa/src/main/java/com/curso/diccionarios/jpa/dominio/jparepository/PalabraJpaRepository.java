package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.jpa.dominio.entity.PalabraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalabraJpaRepository extends JpaRepository<PalabraEntity, Integer> {

}
