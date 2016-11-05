package com.dualion.controldiners.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dualion.controldiners.service.dto.PotDTO;
import com.dualion.controldiners.service.exception.PotException;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.web.rest.vm.ExtreureVM;
import com.dualion.controldiners.web.rest.vm.PagamentVM;

/**
 * Service Interface for managing Pot.
 */
public interface PotService {

    /**
     * Save pagament.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws QuantitatException 
     * @throws ProcesException 
     * @throws UsuarisProcesException 
     */
    PotDTO savePagament(PagamentVM pagamentVM) throws QuantitatException, ProcesException, UsuarisProcesException;

    /**
     * Cancelar pagament.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws ProcesException 
     * @throws UsuarisProcesException 
     * @throws PotException 
     */
    PotDTO cancelarPagament(PagamentVM pagamentVM) throws ProcesException, PotException, UsuarisProcesException;
    
    /**
     * Save extreure diners.
     *
     * @param diners
     * @return the persisted entity 
     * @throws PotException 
     */
	PotDTO saveExtreure(ExtreureVM extreureVM) throws PotException;
    
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
     *  Get last pot.
     *
     *  @return the entity
     */ 
    PotDTO findLast();
}
