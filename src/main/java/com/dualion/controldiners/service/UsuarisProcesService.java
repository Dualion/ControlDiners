package com.dualion.controldiners.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.exception.UsuarisProcesException;

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
     *  Get all the usuarisProces del proces actiu.
     *  
     *  @return the list of entities
     *  @throws UsuarisProcesException 
     */
    List<UsuarisProcesDTO> findAllProcesActiu() throws UsuarisProcesException;
    
    /**
     *  Get all the usuarisProces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UsuarisProcesDTO> findAll(Pageable pageable);

    /**
     *  Get all the usuarisProces del proces actiu.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
	public List<UsuarisProcesDTO> findAllProcesId(Long procesId);
    
    /**
     *  Get the "id" usuarisProces.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UsuarisProcesDTO findOne(Long id);

    /**
     *  Get the usuarisProces by user Id and proces Id.
     *
     *  @param id of usuari and proces.
     *  @return the entity
     */
    UsuarisProcesDTO findOneByUserIdAndProcesId(Long usuariId, Long procesId);
    
    /**
     *  Delete the "id" usuarisProces.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
