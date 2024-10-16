package com.curso.diccionarios.jpa.dominio.jparepository;


import com.curso.diccionarios.jpa.dominio.entity.SignificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignificadoJpaRepository extends JpaRepository<SignificadoEntity, Integer> {

}
