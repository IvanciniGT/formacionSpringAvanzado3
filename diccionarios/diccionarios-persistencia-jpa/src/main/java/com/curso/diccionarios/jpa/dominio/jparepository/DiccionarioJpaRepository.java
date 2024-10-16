package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.jpa.dominio.entity.DiccionarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiccionarioJpaRepository extends JpaRepository<DiccionarioEntity, Integer> {

}