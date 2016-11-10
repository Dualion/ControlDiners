package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.service.UsuarisService;
import com.dualion.controldiners.domain.Proces;
import com.dualion.controldiners.repository.ProcesRepository;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.service.mapper.ProcesMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Proces.
 */
@Service
@Transactional
public class ProcesServiceImpl implements ProcesService{

private final Logger log = LoggerFactory.getLogger(ProcesServiceImpl.class);
    
    @Inject
    private ProcesRepository procesRepository;

    @Inject
    private ProcesMapper procesMapper;

    @Inject
    private UsuarisProcesService usuarisProcesService;
    
    @Inject
    private UsuarisService usuarisService;
   
    @Inject
    private QuantitatService quantitatService;
    
    /**
     * Save a proces.
     *
     * @param procesDTO the entity to save
     * @return the persisted entity
     * @throws ProcesException 
     * @throws QuantitatException 
     */
    public ProcesDTO save() throws ProcesException, QuantitatException {
        log.debug("Request to save Proces ");
        
        if (findActiva() != null) {
        	throw new ProcesException("Hi ha un procés actiu");
        }
        
        QuantitatDTO quantitatDTO = quantitatService.findActiva();
        
        Proces procesBS = new Proces();
        procesBS.setDataInici(ZonedDateTime.now());
        procesBS.setEstat(true);
        final Proces proces = procesRepository.save(procesBS);
        log.debug("Procés {} iniciat amb la quantitat: {}", proces.getId(), quantitatDTO.getDiners());
        if (proces != null){
        	final BigDecimal diners = BigDecimal.ZERO;
        	usuarisService.findAllActiveUser().stream().forEach(usuari -> {
        		UsuarisProcesDTO usuarisProcesDTO = new UsuarisProcesDTO();
        		usuarisProcesDTO.setUsuarisId(usuari.getId());
        		usuarisProcesDTO.setProcesId(proces.getId());
        		usuarisProcesDTO.setDiners(diners);
        		usuarisProcesService.save(usuarisProcesDTO);
        	});
        }
        ProcesDTO result = procesMapper.procesToProcesDTO(proces);
        return result;
    }
    
    /**
     * Acabar proces
     * 
     * @throws UsuarisProcesException 
     * @throws ProcesException 
     */
    public ProcesDTO acabarProcesActiu() throws UsuarisProcesException, ProcesException {
		Optional<Proces> optionalProces = procesRepository.findFirstByEstat(true); 
    	if (optionalProces.isPresent()) {
    		Proces proces = optionalProces.get();
    		List<UsuarisProcesDTO> usuarisProcesDTOs = usuarisProcesService.findAllProcesId(proces.getId());
    		Long noPagatPot = usuarisProcesDTOs.stream().filter(usuarisProces -> usuarisProces.getDiners().compareTo(BigDecimal.ZERO) == 0).count();
    		if (noPagatPot > 0) {
    			throw new UsuarisProcesException("Hi ha usuaris que encara no han pagat");
    		}
    		// Ha pagat tothom i podem finalitzar el procés
    		proces.setEstat(false);
    		procesRepository.save(proces);
    		ProcesDTO result = procesMapper.procesToProcesDTO(proces);
            return result;
    	} else {
    		throw new ProcesException("No hi ha procés actiu");
    	}
	}
    

    /**
     *  Get all the proces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<ProcesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proces");
        Page<Proces> result = procesRepository.findAll(pageable);
        return result.map(proces -> procesMapper.procesToProcesDTO(proces));
    }

    /**
     *  Get one proces by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public ProcesDTO findOne(Long id) {
        log.debug("Request to get Proces : {}", id);
        Proces proces = procesRepository.findOne(id);
        ProcesDTO procesDTO = procesMapper.procesToProcesDTO(proces);
        return procesDTO;
    }

    /**
	 * Get proces actiu.
	 * 
	 * @return the entity 
	 */
	@Override
	public ProcesDTO findActiva() {
		Optional<Proces> optionalProces = procesRepository.findFirstByEstat(true);
		if (optionalProces.isPresent()) {
			ProcesDTO procesDTO = procesMapper.procesToProcesDTO(optionalProces.get());
			return procesDTO;
		}
		return null;
	}
    
    /**
     *  Delete the  proces by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proces : {}", id);
        procesRepository.delete(id);
    }
}
