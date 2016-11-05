package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.UsuarisService;
import com.dualion.controldiners.domain.Usuaris;
import com.dualion.controldiners.repository.UsuarisRepository;
import com.dualion.controldiners.service.dto.UsuarisDTO;
import com.dualion.controldiners.service.mapper.UsuarisMapper;
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
 * Service Implementation for managing Usuaris.
 */
@Service
@Transactional
public class UsuarisServiceImpl implements UsuarisService{

    private final Logger log = LoggerFactory.getLogger(UsuarisServiceImpl.class);
    
    @Inject
    private UsuarisRepository usuarisRepository;

    @Inject
    private UsuarisMapper usuarisMapper;

    /**
     * Save a usuaris.
     *
     * @param usuarisDTO the entity to save
     * @return the persisted entity
     */
    public UsuarisDTO save(UsuarisDTO usuarisDTO) {
        log.debug("Request to save Usuaris : {}", usuarisDTO);
        Usuaris usuaris = usuarisMapper.usuarisDTOToUsuaris(usuarisDTO);
        usuaris = usuarisRepository.save(usuaris);
        UsuarisDTO result = usuarisMapper.usuarisToUsuarisDTO(usuaris);
        return result;
    }

    /**
     *  Get all the usuarises.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<UsuarisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarises");
        Page<Usuaris> result = usuarisRepository.findAll(pageable);
        return result.map(usuaris -> usuarisMapper.usuarisToUsuarisDTO(usuaris));
    }

    /**
     *  Get one usuaris by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public UsuarisDTO findOne(Long id) {
        log.debug("Request to get Usuaris : {}", id);
        Usuaris usuaris = usuarisRepository.findOne(id);
        UsuarisDTO usuarisDTO = usuarisMapper.usuarisToUsuarisDTO(usuaris);
        return usuarisDTO;
    }

    /**
     *  Delete the  usuaris by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuaris : {}", id);
        usuarisRepository.delete(id);
    }
}
