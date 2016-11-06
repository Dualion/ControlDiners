package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;

import com.dualion.controldiners.service.DinersPotService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.web.rest.vm.ExtreureVM;
import com.dualion.controldiners.web.rest.vm.PagamentVM;
import com.dualion.controldiners.service.dto.DinersPotDTO;
import com.dualion.controldiners.service.exception.DinersPotException;
import com.dualion.controldiners.service.exception.ProcesException;
import com.dualion.controldiners.service.exception.QuantitatException;
import com.dualion.controldiners.service.exception.UsuarisProcesException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Pot.
 */
@RestController
public class DinersPotResource {

	private final Logger log = LoggerFactory.getLogger(DinersPotResource.class);
    
    @Inject
    private DinersPotService dinersPotService;

    /**
     * POST  /api/diners-pots/pagament : Create a new pagament.
     *
     * @param PagamentVM 
     * @return the ResponseEntity with status 201 (Created) and with body the new potDTO, or with status 400 (Bad Request) if the pot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/diners-pots/pagament")
    @Timed
    public ResponseEntity<?> createPagamentAlPot(@Valid @RequestBody PagamentVM pagamentVM) throws URISyntaxException {
    	log.debug("REST request to Pagament pot : {}", pagamentVM);
    	DinersPotDTO dinersPotDTO;
		try {
			dinersPotDTO = dinersPotService.savePagament(pagamentVM);
		} catch (QuantitatException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "quantitatnotexist", e.getMessage())).body(null);
		} catch (ProcesException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "procesnoactive", e.getMessage())).body(null);
		} catch (UsuarisProcesException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "usuarisproces", e.getMessage())).body(null);
		}

		return ResponseEntity.created(new URI("/api/diners-pots/" + dinersPotDTO.getId()))
		        .headers(HeaderUtil.createEntityCreationAlert("dinersPot", dinersPotDTO.getId().toString()))
		        .body(dinersPotDTO);
    }
    
    /**
     * POST  /api/diners-pots/cancelarpagament : Cancel·lar pagament.
     *
     * @param PagamentVM 
     * @return the ResponseEntity with status 201 (Created) and with body the new potDTO, or with status 400 (Bad Request) if the pot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/diners-pots/cancelarpagament")
    @Timed
    public ResponseEntity<?> createCancelarPagament(@Valid @RequestBody PagamentVM pagamentVM) throws URISyntaxException {
    	log.debug("REST request to Cancelar Pagament pot : {}", pagamentVM);
    	DinersPotDTO dinersPotDTO;
    	try {
    		dinersPotDTO = dinersPotService.cancelarPagament(pagamentVM);
    	} catch (DinersPotException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "potnegatiu", e.getMessage())).body(null);
		} catch (ProcesException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "procesnoactive", e.getMessage())).body(null);
		} catch (UsuarisProcesException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "usuarisproces", e.getMessage())).body(null);
		}
		
		return ResponseEntity.created(new URI("/api/diners-pots/" + dinersPotDTO.getId()))
		        .headers(HeaderUtil.createEntityCreationAlert("dinersPot", dinersPotDTO.getId().toString()))
		        .body(dinersPotDTO);
    }

    /**
     * POST  /api/diners-pots/extreure : Create a new Extraccio.
     *
     * @param PagamentVM 
     * @return the ResponseEntity with status 201 (Created) and with body the new potDTO, or with status 400 (Bad Request) if the pot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/diners-pots/extreure")
    @Timed
    public ResponseEntity<?> createExtreure(@Valid @RequestBody ExtreureVM extreureVM) throws URISyntaxException {
    	log.debug("REST request to extreure pot : {}", extreureVM);
    	DinersPotDTO dinersPotDTO;
		try {
			dinersPotDTO = dinersPotService.saveExtreure(extreureVM);
		} catch (DinersPotException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dinersPot", "potnegatiu", e.getMessage())).body(null);
		}
		
		return ResponseEntity.created(new URI("/api/diners-pots/" + dinersPotDTO.getId()))
		        .headers(HeaderUtil.createEntityCreationAlert("dinersPot", dinersPotDTO.getId().toString()))
		        .body(dinersPotDTO);
    }
    
    /**
     * GET  /public/diners-pots : get all the pots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/public/diners-pots")
    @Timed
    public ResponseEntity<List<DinersPotDTO>> getAllPots(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pots");
        Page<DinersPotDTO> page = dinersPotService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diners-pots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /public/diners-pots/:id : get the "id" pot.
     *
     * @param id the id of the potDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the potDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/diners-pots/{id}")
    @Timed
    public ResponseEntity<DinersPotDTO> getPot(@PathVariable Long id) {
        log.debug("REST request to get Pot : {}", id);
        DinersPotDTO potDTO = dinersPotService.findOne(id);
        return Optional.ofNullable(potDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /public/diners-pots/last : get last pot.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the potDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/diners-pots/last")
    @Timed
    public ResponseEntity<DinersPotDTO> getLastPot() {
        log.debug("REST request to get last Diners del Pot");
        DinersPotDTO potDTO = dinersPotService.findLast();
        return Optional.ofNullable(potDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
