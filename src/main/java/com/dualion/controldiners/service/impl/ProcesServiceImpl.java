package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.domain.Proces;
import com.dualion.controldiners.repository.ProcesRepository;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.mapper.ProcesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
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

    /**
     * Save a proces.
     *
     * @param procesDTO the entity to save
     * @return the persisted entity
     */
    public ProcesDTO save(ProcesDTO procesDTO) {
        log.debug("Request to save Proces : {}", procesDTO);
        Proces proces = procesMapper.procesDTOToProces(procesDTO);
        proces = procesRepository.save(proces);
        ProcesDTO result = procesMapper.procesToProcesDTO(proces);
        return result;
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
     *  Delete the  proces by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Proces : {}", id);
        procesRepository.delete(id);
    }
}
