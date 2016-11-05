package com.dualion.controldiners.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.exception.QuantitatException;

/**
 * Service Interface for managing Quantitat.
 */
public interface QuantitatService {

    /**
     * Save a quantitat.
     *
     * @param quantitatDTO the entity to save
     * @return the persisted entity
     * @throws QuantitatException 
     */
    QuantitatDTO save(QuantitatDTO quantitatDTO) throws QuantitatException;

    /**
     *  Get all the quantitats.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuantitatDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" quantitat.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuantitatDTO findOne(Long id);

    /**
     *  Get the quantitat activa.
     *
     *  @return the entity
     * @throws QuantitatException 
     */
    QuantitatDTO findActiva() throws QuantitatException;
    
    /**
     *  Delete the "id" quantitat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
