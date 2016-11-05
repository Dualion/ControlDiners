package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.QuantitatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Quantitat.
 */
public interface QuantitatService {

    /**
     * Save a quantitat.
     *
     * @param quantitatDTO the entity to save
     * @return the persisted entity
     */
    QuantitatDTO save(QuantitatDTO quantitatDTO);

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
     *  Delete the "id" quantitat.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
