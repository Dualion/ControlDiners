package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.ProcesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Proces.
 */
public interface ProcesService {

    /**
     * Save a proces.
     *
     * @param procesDTO the entity to save
     * @return the persisted entity
     */
    ProcesDTO save(ProcesDTO procesDTO);

    /**
     *  Get all the proces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProcesDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" proces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProcesDTO findOne(Long id);

    /**
     *  Delete the "id" proces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
