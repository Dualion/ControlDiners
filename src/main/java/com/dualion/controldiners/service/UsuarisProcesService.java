package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing UsuarisProces.
 */
public interface UsuarisProcesService {

    /**
     * Save a usuarisProces.
     *
     * @param usuarisProcesDTO the entity to save
     * @return the persisted entity
     */
    UsuarisProcesDTO save(UsuarisProcesDTO usuarisProcesDTO);

    /**
     *  Get all the usuarisProces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UsuarisProcesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" usuarisProces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UsuarisProcesDTO findOne(Long id);

    /**
     *  Delete the "id" usuarisProces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
