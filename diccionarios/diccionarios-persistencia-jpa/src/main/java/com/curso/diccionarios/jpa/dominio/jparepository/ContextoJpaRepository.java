package com.curso.diccionarios.jpa.dominio.jparepository;

import com.curso.diccionarios.jpa.dominio.entity.ContextoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContextoJpaRepository extends JpaRepository<ContextoEntity, Integer> {

    Optional<ContextoEntity> findByContexto(String contexto);
}