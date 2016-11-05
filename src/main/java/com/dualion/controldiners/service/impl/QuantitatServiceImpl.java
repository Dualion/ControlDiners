package com.dualion.controldiners.service.impl;

import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.domain.Quantitat;
import com.dualion.controldiners.repository.QuantitatRepository;
import com.dualion.controldiners.service.dto.ProcesDTO;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.mapper.QuantitatMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
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
	
	@Inject
	private ProcesService procesService;

	@PersistenceContext
	private EntityManager em;

	/**
	 * Save a quantitat.
	 *
	 * @param quantitatDTO
	 *            the entity to save
	 * @return the persisted entity
	 * @throws QuantitatException 
	 */
	public QuantitatDTO save(QuantitatDTO quantitatDTO) throws QuantitatException {
		log.debug("Request to save Quantitat : {}", quantitatDTO);
		ProcesDTO procesDTO = procesService.findActiva();
		if (procesDTO != null ) {
			throw new QuantitatException("No es pot cambiar la quantitat, hi ha un procés actiu");
		}
		
		Quantitat quantitat = quantitatMapper.quantitatDTOToQuantitat(quantitatDTO);
		quantitat.setActiu(true);
		quantitat = quantitatRepository.save(quantitat);
		if (quantitat != null) {
			desactivaQuantitatAntigues(quantitat.getId());
		}
		QuantitatDTO result = quantitatMapper.quantitatToQuantitatDTO(quantitat);
		return result;
	}

	/**
	 * Get all the quantitats.
	 * 
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Transactional(readOnly = true)
	public Page<QuantitatDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Quantitats");
		Page<Quantitat> result = quantitatRepository.findAll(pageable);
		return result.map(quantitat -> quantitatMapper.quantitatToQuantitatDTO(quantitat));
	}

	/**
	 * Get one quantitat by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Transactional(readOnly = true)
	public QuantitatDTO findOne(Long id) {
		log.debug("Request to get Quantitat : {}", id);
		Quantitat quantitat = quantitatRepository.findOne(id);
		QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(quantitat);
		return quantitatDTO;
	}
	
	/**
	 * Get quantitat activa.
	 * 
	 * @return the entity
	 * @throws QuantitatException 
	 */
	@Override
	public QuantitatDTO findActiva() throws QuantitatException {
		Optional<Quantitat> optionalQuantitat = quantitatRepository.findFirstByActiu(true);
		if (!optionalQuantitat.isPresent()) {
			throw new QuantitatException("No existeix ninguna quantitat activa");
		}
		QuantitatDTO quantitatDTO = quantitatMapper.quantitatToQuantitatDTO(optionalQuantitat.get());
		return quantitatDTO;
	}

	/**
	 * Delete the quantitat by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	public void delete(Long id) {
		log.debug("Request to delete Quantitat : {}", id);
		quantitatRepository.delete(id);
	}

	private void desactivaQuantitatAntigues(Long idQuantitat) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		// create update
		CriteriaUpdate<Quantitat> update = cb.createCriteriaUpdate(Quantitat.class);

		// set the root class
		Root<Quantitat> root = update.from(Quantitat.class);

		// set update and where clause
		update.set("actiu", false);
		update.where(cb.notEqual(root.get("id"), idQuantitat));

		// perform update
		em.createQuery(update).executeUpdate();
	}
}
