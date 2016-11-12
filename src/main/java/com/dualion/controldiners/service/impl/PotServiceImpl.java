package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.PotService;
import com.dualion.controldiners.domain.Pot;
import com.dualion.controldiners.repository.PotRepository;
import com.dualion.controldiners.service.dto.PotDTO;
import com.dualion.controldiners.service.mapper.PotMapper;
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

    /**
     * Save a pot.
     *
     * @param potDTO the entity to save
     * @return the persisted entity
     */
    public PotDTO save(PotDTO potDTO) {
        log.debug("Request to save Pot : {}", potDTO);
        Pot pot = potMapper.potDTOToPot(potDTO);
        pot = potRepository.save(pot);
        PotDTO result = potMapper.potToPotDTO(pot);
        return result;
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
     *  Delete the  pot by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pot : {}", id);
        potRepository.delete(id);
    }
}
