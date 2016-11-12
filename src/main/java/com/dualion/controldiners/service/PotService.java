package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.PotDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Pot.
 */
public interface PotService {

    /**
     * Save a pot.
     *
     * @param potDTO the entity to save
     * @return the persisted entity
     */
    PotDTO save(PotDTO potDTO);

    /**
     *  Get all the pots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PotDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pot.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PotDTO findOne(Long id);

    /**
     *  Delete the "id" pot.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
