package com.dualion.controldiners.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dualion.controldiners.domain.Usuaris;
import com.dualion.controldiners.service.dto.UsuarisDTO;

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
     *  Get all active usuarises.
     *  
     *  @return the list of entities
     */
    List<Usuaris> findAllActiveUser();
    
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
     *  Desactiva the usuaris by id.
     *
     *  @param id the id of the entity
     */
    public void desactiva(Long id);
    
    /**
     *  Activa the usuaris by id.
     *
     *  @param id the id of the entity
     */
    public void activa(Long id);
    
}
