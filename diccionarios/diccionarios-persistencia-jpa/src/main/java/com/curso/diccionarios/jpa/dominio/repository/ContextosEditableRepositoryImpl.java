package com.curso.diccionarios.jpa.dominio.repository;

import com.curso.diccionarios.dominio.exception.AlreadyExistsEntityException;
import com.curso.diccionarios.dominio.exception.InvalidArgumentException;
import com.curso.diccionarios.dominio.exception.NonExistentEntityException;
import com.curso.diccionarios.dominio.model.ContextoEditable;
import com.curso.diccionarios.dominio.repository.ContextosEditableRepository;
import com.curso.diccionarios.jpa.dominio.entity.ContextoEntity;
import com.curso.diccionarios.jpa.dominio.jparepository.ContextoJpaRepository;
import com.curso.diccionarios.jpa.dominio.mappers.ContextoMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContextosEditableRepositoryImpl implements ContextosEditableRepository {

    private final ContextoJpaRepository contextoJpaRepository;
    private final ContextoMapper mapeador;

    public Optional<ContextoEditable> getContexto(@NonNull String contexto){
        return contextoJpaRepository.findByContexto(contexto).map(mapeador::entityToModel);
    }
    public List<ContextoEditable> getContextos(){
        return contextoJpaRepository.findAll().stream().map(mapeador::entityToModel).toList();

    }
    public void updateContexto(@NonNull ContextoEditable contexto) throws InvalidArgumentException, NonExistentEntityException{
        Optional<ContextoEntity> existintingContexto = contextoJpaRepository.findByContexto(contexto.getContexto());
        if(existintingContexto.isEmpty()){
            throw new NonExistentEntityException("Contexto no existente");
        }
        existintingContexto.ifPresent(c -> {
            c.setDescripcion(contexto.getDescripcion());
            contextoJpaRepository.save(c);
        });
    }
    public ContextoEditable newContexto(@NonNull String contexto, @NonNull String descripcion) throws InvalidArgumentException, AlreadyExistsEntityException{
        if(contextoJpaRepository.findByContexto(contexto).isPresent()){
            throw new AlreadyExistsEntityException("Contexto ya existente");
        }
        ContextoEntity contextoEntity = new ContextoEntity();
        contextoEntity.setContexto(contexto);
        contextoEntity.setDescripcion(descripcion);
        return mapeador.entityToModel(contextoJpaRepository.save(contextoEntity));

    }
    public Optional<ContextoEditable> deleteContexto(@NonNull String contexto){
        Optional<ContextoEntity> existintingContexto = contextoJpaRepository.findByContexto(contexto);
        existintingContexto.ifPresent(contextoJpaRepository::delete);
        return existintingContexto.map(mapeador::entityToModel);
    }

}
