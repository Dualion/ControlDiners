package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.domain.UsuarisProces;
import com.dualion.controldiners.repository.UsuarisProcesRepository;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.exception.UsuarisProcesException;
import com.dualion.controldiners.service.mapper.UsuarisProcesMapper;

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
 * Service Implementation for managing UsuarisProces.
 */
@Service
@Transactional
public class UsuarisProcesServiceImpl implements UsuarisProcesService{

	private final Logger log = LoggerFactory.getLogger(UsuarisProcesServiceImpl.class);
    
    @Inject
    private UsuarisProcesRepository usuarisProcesRepository;

    @Inject
    private UsuarisProcesMapper usuarisProcesMapper;

    @Inject
    private ProcesService procesService;
    
    /**
     * Save a usuarisProces.
     *
     * @param usuarisProcesDTO the entity to save
     * @return the persisted entity
     */
    public UsuarisProcesDTO save(UsuarisProcesDTO usuarisProcesDTO) {
        log.debug("Request to save UsuarisProces : {}", usuarisProcesDTO);
        UsuarisProces usuarisProces = usuarisProcesMapper.usuarisProcesDTOToUsuarisProces(usuarisProcesDTO);
        usuarisProces = usuarisProcesRepository.save(usuarisProces);
        UsuarisProcesDTO result = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces);
        return result;
    }
    
    /**
     *  Get all the usuarisProces del proces actiu.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     *  @throws UsuarisProcesException 
     */
    @Transactional(readOnly = true)
	public List<UsuarisProcesDTO> findAllProcesActiu() throws UsuarisProcesException {
    	log.debug("Request to get all UsuarisProces");
    	ProcesDTO procesDTO = procesService.findActiva();
    	if (procesDTO == null) {
    		throw new UsuarisProcesException("No hi ha un procés actiu");
    	}
    	return findAllProcesId(procesDTO.getId());
	}

    /**
     *  Get all the usuarisProces del proces actiu.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     *  @throws UsuarisProcesException 
     */
    @Transactional(readOnly = true)
	public List<UsuarisProcesDTO> findAllProcesId(Long procesId) {
    	log.debug("Request to get all UsuarisProces from procesId : {}", procesId);
    	List<UsuarisProces> result = usuarisProcesRepository.findAllByProcesId(procesId);
    	return result.stream().map(usuarisProces -> usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces)).collect(Collectors.toList());
	}
    
    /**
     *  Get all the usuarisProces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UsuarisProcesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsuarisProces");
        Page<UsuarisProces> result = usuarisProcesRepository.findAll(pageable);
        return result.map(usuarisProces -> usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces));
    }

    /**
     *  Get one usuarisProces by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UsuarisProcesDTO findOne(Long id) {
        log.debug("Request to get UsuarisProces : {}", id);
        UsuarisProces usuarisProces = usuarisProcesRepository.findOne(id);
        UsuarisProcesDTO usuarisProcesDTO = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces);
        return usuarisProcesDTO;
    }

    /**
     *  Get the usuarisProces by user Id and proces Id.
     *
     *  @param id of usuari and proces.
     *  @return the entity
     */
    @Transactional(readOnly = true)
	public UsuarisProcesDTO findOneByUserIdAndProcesId(Long usuariId, Long procesId) {
    	log.debug("Request to get UsuarisProces : {}, {}", usuariId, procesId);
        Optional<UsuarisProces> usuarisProces = usuarisProcesRepository.findOneByUsuarisIdAndProcesId(usuariId, procesId);
        if (usuarisProces.isPresent()) {
        	UsuarisProcesDTO usuarisProcesDTO = usuarisProcesMapper.usuarisProcesToUsuarisProcesDTO(usuarisProces.get());
        	return usuarisProcesDTO;
        }
        return null;
    }
    
    /**
     *  Delete the  usuarisProces by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarisProces : {}", id);
        usuarisProcesRepository.delete(id);
    }
}
