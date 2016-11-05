package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.domain.Quantitat;
import com.dualion.controldiners.repository.QuantitatRepository;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.mapper.QuantitatMapper;
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
 * Service Implementation for managing Quantitat.
 */
@Service
@Transactional
public class QuantitatServiceImpl implements QuantitatService{

    private final Logger log = LoggerFactory.getLogger(QuantitatServiceImpl.class);
    
    @Inject
    private QuantitatRepository quantitatRepository;

    @Inject
    private QuantitatMapper quantitatMapper;

    /**
     * Save a quantitat.
     *
     * @param quantitatDTO the entity to save
     * @return the persisted entity
     */
    public QuantitatDTO save(QuantitatDTO quantitatDTO) {
        log.debug("Request to save Quantitat : {}", quantitatDTO);
        Quantitat quantitat = quantitatMapper.quantitatDTOToQuantitat(quantitatDTO);
        quantitat = quantitatRepository.save(quantitat);
        QuantitatDTO result = quantitatMapper.quantitatToQuantitatDTO(quantitat);
        return result;
    }

    /**
     *  Get all the quantitats.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<QuantitatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Quantitats");
        Page<Quantitat> result = quantitatRepository.findAll(pageable);
        return result.map(quantitat -> quantitatMapper.quantitatToQuantitatDTO(quantitat));
    }

    /**
     *  Get one quantitat by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public QuantitatDTO findOne(Long id) {
        log.debug("Request to get Quantitat : {}", id);
        Quantitat quantitat = quantitatRepository.findOne(id);
        QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(quantitat);
        return quantitatDTO;
    }

    /**
     *  Delete the  quantitat by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Quantitat : {}", id);
        quantitatRepository.delete(id);
    }
}
