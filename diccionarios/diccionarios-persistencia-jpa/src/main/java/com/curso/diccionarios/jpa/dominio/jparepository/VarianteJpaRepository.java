package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.dominio.model.Variante;
import com.curso.diccionarios.jpa.dominio.entity.VarianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarianteJpaRepository extends JpaRepository<VarianteEntity, Integer> {

}