package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.UsuarisDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Usuaris.
 */
public interface UsuarisService {

    /**
     * Save a usuaris.
     *
     * @param usuarisDTO the entity to save
     * @return the persisted entity
     */
    UsuarisDTO save(UsuarisDTO usuarisDTO);

    /**
     *  Get all the usuarises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UsuarisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" usuaris.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UsuarisDTO findOne(Long id);

    /**
     *  Delete the "id" usuaris.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
