package com.curso.diccionarios.service;

import com.curso.diccionarios.service.dto.ContextoDTO;
import com.curso.diccionarios.service.exception.InvalidArgumentServiceException;
import com.curso.diccionarios.service.mapper.ContextosServiceMapper;
import com.curso.diccionarios.dominio.repository.ContextosEditableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContextoServicioImpl implements ContextoServicio {

    private final ContextosEditableRepository contextosEditableRepository;
    private final ContextosServiceMapper mapeador;

    @Override
    public List<ContextoDTO> getContextos() {
        return contextosEditableRepository.getContextos().stream().map(mapeador::modelt2DTO).toList();
    }

    @Override
    public ContextoDTO crearContexto(ContextoDTO contexto) throws InvalidArgumentServiceException {
        try {
            return mapeador.modelt2DTO(contextosEditableRepository.newContexto(contexto.getContexto(), contexto.getDescripcion()));
        } catch (Exception e) {
            throw new InvalidArgumentServiceException("Error al crear el contexto");
        }
    }
}
