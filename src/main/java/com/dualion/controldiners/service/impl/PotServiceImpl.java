package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.PotService;
import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.domain.Pot;
import com.dualion.controldiners.repository.PotRepository;
import com.dualion.controldiners.service.dto.PotDTO;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.exception.PotException;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.service.mapper.PotMapper;
import com.dualion.controldiners.web.rest.vm.ExtreureVM;
import com.dualion.controldiners.web.rest.vm.PagamentVM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pot.
 */
@Service
@Transactional
public class PotServiceImpl implements PotService{

private final Logger log = LoggerFactory.getLogger(PotServiceImpl.class);
    
    @Inject
    private PotRepository potRepository;

    @Inject
    private PotMapper potMapper;

    @Inject
    private UsuarisProcesService usuarisProcesService;
    
    @Inject
    private ProcesService procesService;
    
    @Inject
    private QuantitatService quantitatService;

    /**
     * Save pagament.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws QuantitatException 
     * @throws ProcesException  
     * @throws UsuarisProcesException 
     */
    public PotDTO savePagament(PagamentVM pagamentVM) throws QuantitatException, ProcesException, UsuarisProcesException {
        log.debug("Request to save Pagament : {}", pagamentVM);
        ProcesDTO procesDTO = procesService.findActiva();
        PotDTO result = null;
        if (procesDTO != null) {
        	UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOneByUserIdAndProcesId(pagamentVM.getUserId(), procesDTO.getId());
        	QuantitatDTO pagament = quantitatService.findActiva();
        	if (usuarisProcesDTO != null) {
        		if (usuarisProcesDTO.getDiners() > 0) {
        			//Usuari ja ha pagat
        			throw new UsuarisProcesException("L'usuari ja ha pagat!");
        		}
        		
        		// Afegeix el pagament al total del usuari
        		Float diners = usuarisProcesDTO.getDiners() + pagament.getDiners();
        		usuarisProcesDTO.setDiners(diners);
        		usuarisProcesService.save(usuarisProcesDTO);
        		
        		//Afegim el pagament al total del pot.
        		diners = 0.0F;
        		Optional<Pot> lastPot = potRepository.findFirstByOrderByDataDesc();
        		if (lastPot.isPresent()) {
        			diners = lastPot.get().getDinersTotals();
        		} 
        		diners = diners + pagament.getDiners(); 
        		Pot newPot = new Pot();
    			newPot.dinersTotals(diners)
    				.setDescripcio(new StringBuilder("Pagament ")
    						.append(pagament.getDiners())
    						.append("/")
    						.append(diners.toString())
    						.append(" -> ").append(usuarisProcesDTO.getUsuarisNom())
    						.toString());
				newPot = potRepository.save(newPot);
    			result = potMapper.potToPotDTO(newPot);
        	} else {
        		throw new UsuarisProcesException("No existeix aquest usuari en el procés actiu");
        	}
        } else {
        	throw new ProcesException("No existeix ningun procés actiu");
        }
        return result;
    }
    
    /**
     * Save extreure diners.
     *
     * @param diners
     * @return the persisted entity 
     * @throws PotException 
     */
	public PotDTO saveExtreure(ExtreureVM extreureVM) throws PotException {
		log.debug("Request to save Extreure : {}", extreureVM);
		Float diners = 0.0F;
		Optional<Pot> lastPot = potRepository.findFirstByOrderByDataDesc();
		if (lastPot.isPresent()) {
			diners = lastPot.get().getDinersTotals();
		} 
		diners = diners - extreureVM.getDiners();
		if (diners < 0.0) {
			throw new PotException("No es pot extreure més diners dels que hi han al pot");
		}
		Pot newPot = new Pot();
		newPot.dinersTotals(diners)
			.setDescripcio(new StringBuilder("Extreure ")
					.append(extreureVM.getDiners())
					.append("/")
					.append(diners.toString())
					.toString());
		newPot = potRepository.save(newPot);
		return potMapper.potToPotDTO(newPot);
	}
    
    /**
     *  Get all the pots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<PotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pots");
        Page<Pot> result = potRepository.findAll(pageable);
        return result.map(pot -> potMapper.potToPotDTO(pot));
    }

    /**
     *  Get one pot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PotDTO findOne(Long id) {
        log.debug("Request to get Pot : {}", id);
        Pot pot = potRepository.findOne(id);
        PotDTO potDTO = potMapper.potToPotDTO(pot);
        return potDTO;
    }

    /**
     *  Get last pot.
     *
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PotDTO findLast() {
        log.debug("Request to get last Pot ");
        Optional<Pot> oPot = potRepository.findFirstByOrderByDataDesc();
        PotDTO potDTO = null;
        if (oPot.isPresent()) {
        	potDTO = potMapper.potToPotDTO(oPot.get());
        }
        return potDTO;
    }
}
