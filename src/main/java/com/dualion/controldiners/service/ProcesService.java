package com.dualion.controldiners.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;

/**
 * Service Interface for managing Proces.
 */
public interface ProcesService {

    /**
     * Save a proces.
     *
     * @return the persisted entity
     * @throws ProcesException 
     * @throws QuantitatException 
     */
    ProcesDTO save() throws ProcesException, QuantitatException;
    
    /**
     * Acabar proces
     * 
     * @throws UsuarisProcesException 
     * @throws ProcesException 
     */
    ProcesDTO acabarProcesActiu() throws UsuarisProcesException, ProcesException;

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
     *  Get proces actiu.
     *
     *  @return the entity 
     */
	ProcesDTO findActiva();

    /**
     *  Delete the "id" proces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
