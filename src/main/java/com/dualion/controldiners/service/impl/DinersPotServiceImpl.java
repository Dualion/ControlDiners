package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.DinersPotService;
import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.domain.DinersPot;
import com.dualion.controldiners.repository.DinersPotRepository;
import com.dualion.controldiners.service.dto.DinersPotDTO;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.exception.DinersPotException;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.service.mapper.DinersPotMapper;
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
 * Service Implementation for managing DinersPot.
 */
@Service
@Transactional
public class DinersPotServiceImpl implements DinersPotService{

    private final Logger log = LoggerFactory.getLogger(DinersPotServiceImpl.class);
    
    @Inject
    private DinersPotRepository dinersPotRepository;

    @Inject
    private DinersPotMapper dinersPotMapper;

    @Inject
    private UsuarisProcesService usuarisProcesService;
    
    @Inject
    private ProcesService procesService;
    
    @Inject
    private QuantitatService quantitatService;

    /**
     * Save pagament al pot.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws QuantitatException 
     * @throws ProcesException  
     * @throws UsuarisProcesException 
     */
    public DinersPotDTO savePagament(PagamentVM pagamentVM) throws QuantitatException, ProcesException, UsuarisProcesException {
        log.debug("Request to afegir Pagament al pot: {}", pagamentVM);
        ProcesDTO procesDTO = procesService.findActiva();
        DinersPotDTO result = null;
        if (procesDTO != null) {
        	UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOneByUserIdAndProcesId(pagamentVM.getUserId(), procesDTO.getId());
        	QuantitatDTO pagament = quantitatService.findActiva();
        	if (usuarisProcesDTO != null) {
        		if (usuarisProcesDTO.getDiners() > 0) {
        			//Usuari ja ha pagat en aquest procés
        			throw new UsuarisProcesException("L'usuari ja ha pagat!");
        		}
        		
        		// Afegeix el pagament al total del usuari
        		Float diners = usuarisProcesDTO.getDiners() + pagament.getDiners();
        		usuarisProcesDTO.setDiners(diners);
        		usuarisProcesService.save(usuarisProcesDTO);
        		
        		//Afegim el pagament al total del pot.
        		diners = 0.0F;
        		Optional<DinersPot> lastDinersPot = dinersPotRepository.findFirstByOrderByDataDesc();
        		if (lastDinersPot.isPresent()) {
        			diners = lastDinersPot.get().getDinersTotals();
        		} 
        		diners = diners + pagament.getDiners(); 
        		DinersPot newPot = new DinersPot();
    			newPot.dinersTotals(diners)
    				.setDescripcio(new StringBuilder("Pagament ")
    						.append(pagament.getDiners())
    						.append("/")
    						.append(diners.toString())
    						.append(" -> ").append(usuarisProcesDTO.getUsuarisNom())
    						.toString());
				newPot = dinersPotRepository.save(newPot);
    			result = dinersPotMapper.dinersPotToDinersPotDTO(newPot);
        	} else {
        		throw new UsuarisProcesException("No existeix aquest usuari en el procés actiu");
        	}
        } else {
        	throw new ProcesException("No existeix ningun procés actiu");
        }
        return result;
    }
    
    /**
     * Cancelar pagament del pot.
     *
     * @param usuariId
     * @return the persisted entity
     * @throws ProcesException 
     * @throws DinersPotException, UsuarisProcesException 
     */
    public DinersPotDTO cancelarPagament(PagamentVM pagamentVM) throws ProcesException, DinersPotException, UsuarisProcesException {
    	
    	log.debug("Request to Cancel·lar pagament : {}", pagamentVM);
        ProcesDTO procesDTO = procesService.findActiva();
        DinersPotDTO result = null;
        if (procesDTO != null) {
        	UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOneByUserIdAndProcesId(pagamentVM.getUserId(), procesDTO.getId());
        	if (usuarisProcesDTO != null && usuarisProcesDTO.getDiners() > 0.0) {
        		Float dinersATreure = usuarisProcesDTO.getDiners();
        		Optional<DinersPot> lastPot = dinersPotRepository.findFirstByOrderByDataDesc();
        		
        		//Afegim el pagament al total del pot si tenim suficients diners al pot.
        		if (lastPot.isPresent() && (lastPot.get().getDinersTotals() - dinersATreure) >= 0 ) {
        			usuarisProcesDTO.setDiners(0.0F);
            		usuarisProcesService.save(usuarisProcesDTO);
        			
            		DinersPot newDinersPot = new DinersPot();
        			newDinersPot.dinersTotals(lastPot.get().getDinersTotals() - dinersATreure)
        				.setDescripcio(new StringBuilder("Cancel·lar pagament ")
        						.append(dinersATreure.toString())
        						.append("/")
        						.append(lastPot.get().getDinersTotals() - dinersATreure)
        						.append(" -> ").append(usuarisProcesDTO.getUsuarisNom())
        						.toString());
    				newDinersPot = dinersPotRepository.save(newDinersPot);
        			result = dinersPotMapper.dinersPotToDinersPotDTO(newDinersPot);
        		} else {
        			throw new DinersPotException("No es pot extreure més diners dels que hi han al pot");
        		}
        	} else {
        		throw new UsuarisProcesException("No existeix aquest usuari en el procés actiu");
        	}
        } else {
        	throw new ProcesException("No existeix ningun procés actiu");
        }
        return result;	
    }
    
    /**
     * Save extreure diners del pot.
     *
     * @param diners
     * @return the persisted entity 
     * @throws DinersPotException 
     */
	public DinersPotDTO saveExtreure(ExtreureVM extreureVM) throws DinersPotException {
		log.debug("Request to Extreure diners del pot: {}", extreureVM);
		Float diners = 0.0F;
		Optional<DinersPot> lastDinersPot = dinersPotRepository.findFirstByOrderByDataDesc();
		if (lastDinersPot.isPresent()) {
			diners = lastDinersPot.get().getDinersTotals();
		} 
		diners = diners - extreureVM.getDiners();
		if (diners < 0.0) {
			throw new DinersPotException("No es pot extreure més diners dels que hi han al pot");
		}
		DinersPot newPot = new DinersPot();
		newPot.dinersTotals(diners)
			.setDescripcio(new StringBuilder("Extreure ")
					.append(extreureVM.getDiners())
					.append("/")
					.append(diners.toString())
					.toString());
		newPot = dinersPotRepository.save(newPot);
		return dinersPotMapper.dinersPotToDinersPotDTO(newPot);
	}
    
    /**
     *  Get all the pots.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<DinersPotDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DinersPot");
        Page<DinersPot> result = dinersPotRepository.findAll(pageable);
        return result.map(dinersPot -> dinersPotMapper.dinersPotToDinersPotDTO(dinersPot));
    }

    /**
     *  Get one pot by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DinersPotDTO findOne(Long id) {
        log.debug("Request to get DinersPot : {}", id);
        DinersPot pot = dinersPotRepository.findOne(id);
        DinersPotDTO dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(pot);
        return dinersPotDTO;
    }

    /**
     *  Get last pot.
     *
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public DinersPotDTO findLast() {
        log.debug("Request to get last diners Pot");
        Optional<DinersPot> oPot = dinersPotRepository.findFirstByOrderByDataDesc();
        DinersPotDTO dinersPotDTO = null;
        if (oPot.isPresent()) {
        	dinersPotDTO = dinersPotMapper.dinersPotToDinersPotDTO(oPot.get());
        }
        return dinersPotDTO;
    }
}
