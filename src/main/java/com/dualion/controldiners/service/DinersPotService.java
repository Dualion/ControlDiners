package com.dualion.controldiners.service;

import com.dualion.controldiners.service.dto.DinersPotDTO;
import com.dualion.controldiners.service.exception.DinersPotException;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.web.rest.vm.ExtreureVM;
import com.dualion.controldiners.web.rest.vm.PagamentVM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing DinersPot.
 */
public interface DinersPotService {

	/**
     * Save pagament.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws QuantitatException 
     * @throws ProcesException 
     * @throws UsuarisProcesException 
     */
    DinersPotDTO savePagament(PagamentVM pagamentVM) throws QuantitatException, ProcesException, UsuarisProcesException;

    /**
     * Cancelar pagament.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws ProcesException 
     * @throws UsuarisProcesException 
     * @throws DinersPotException 
     */
    DinersPotDTO cancelarPagament(PagamentVM pagamentVM) throws ProcesException, DinersPotException, UsuarisProcesException;
    
    /**
     * Save extreure diners.
     *
     * @param diners
     * @return the persisted entity 
     * @throws DinersPotException 
     */
    DinersPotDTO saveExtreure(ExtreureVM extreureVM) throws DinersPotException;
    
    /**
     *  Get all the diners pots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DinersPotDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" dinersPot.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DinersPotDTO findOne(Long id);

    /**
     *  Get last dinersPot.
     *
     *  @return the entity
     */ 
    DinersPotDTO findLast();
}
