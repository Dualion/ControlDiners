package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.service.UsuarisService;
import com.dualion.controldiners.domain.Usuaris;
import com.dualion.controldiners.repository.UsuarisRepository;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.UsuarisDTO;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
import com.dualion.controldiners.service.mapper.UsuarisMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.time.ZonedDateTime;
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

    @Inject
    private ProcesService procesService;
    
    @Inject
    private UsuarisProcesService usuarisProcesService; 
    
    /**
     * Save a usuaris.
     *
     * @param usuarisDTO the entity to save
     * @return the persisted entity
     */
    public UsuarisDTO save(UsuarisDTO usuarisDTO) {
        log.debug("Request to save Usuaris : {}", usuarisDTO);
        Usuaris usuaris = usuarisMapper.usuarisDTOToUsuaris(usuarisDTO);
        usuaris.setDataInici(ZonedDateTime.now());
        usuaris = usuarisRepository.save(usuaris);
        if (usuaris != null) {
        	ProcesDTO procesDTO = procesService.findActiva();
        	if (procesDTO != null) {
        		UsuarisProcesDTO usuarisProcesDTO = new UsuarisProcesDTO();
        		usuarisProcesDTO.setProcesId(procesDTO.getId());
        		usuarisProcesDTO.setUsuarisId(usuaris.getId());
        		usuarisProcesDTO.setDiners(0.0F);
        		usuarisProcesService.save(usuarisProcesDTO);
        	}
        }
        UsuarisDTO result = usuarisMapper.usuarisToUsuarisDTO(usuaris);
        return result;
    }

    /**
     *  Get all actives usuarises.
     *  
     *  @return the list of entities
     */
	@Override
	public List<Usuaris> findAllActiveUser() {
		log.debug("Request to get all Usuarises");
        List<Usuaris> result = usuarisRepository.findByActiuTrue();
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
     *  Desactiva the usuaris by id.
     *
     *  @param id the id of the entity
     */
    public void desactiva(Long id) {
        log.debug("Request to desactiva Usuaris : {}", id);
        ProcesDTO procesDTO = procesService.findActiva();
    	if (procesDTO != null) {
    		UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOneByUserIdAndProcesId(id, procesDTO.getId());
    		if (usuarisProcesDTO != null) { 
    			if( usuarisProcesDTO.getDiners() == 0) {
    				usuarisProcesService.delete(usuarisProcesDTO.getId());
    			}
    		}
    	}
    	usuarisRepository.setUsuarisActiuById(false, id);
    }
    
    /**
     *  Activa the usuaris by id.
     *
     *  @param id the id of the entity
     */
    public void activa(Long id) {
        log.debug("Request to Activa Usuaris : {}", id);
        ProcesDTO procesDTO = procesService.findActiva();
    	if (procesDTO != null) {
    		UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOneByUserIdAndProcesId(id, procesDTO.getId());
    		if (usuarisProcesDTO == null) {
    			UsuarisProcesDTO newUsuarisProcesDTO = new UsuarisProcesDTO();
    			newUsuarisProcesDTO.setProcesId(procesDTO.getId());
    			newUsuarisProcesDTO.setUsuarisId(id);
    			newUsuarisProcesDTO.setDiners(0.0F);
        		usuarisProcesService.save(newUsuarisProcesDTO);
    		}
    	}
    	usuarisRepository.setUsuarisActiuById(true, id);
    }
}
