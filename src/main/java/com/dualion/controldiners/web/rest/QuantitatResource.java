package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dualion.controldiners.service.QuantitatService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.service.dto.QuantitatDTO;
import com.dualion.controldiners.service.exception.QuantitatException;

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
 * REST controller for managing Quantitat.
 */
@RestController
public class QuantitatResource {

	private final Logger log = LoggerFactory.getLogger(QuantitatResource.class);
    
    @Inject
    private QuantitatService quantitatService;

    /**
     * POST  /quantitats : Create a new quantitat.
     *
     * @param quantitatDTO the quantitatDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quantitatDTO, or with status 400 (Bad Request) if the quantitat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/quantitats")
    @Timed
    public ResponseEntity<QuantitatDTO> createQuantitat(@Valid @RequestBody QuantitatDTO quantitatDTO) throws URISyntaxException {
        log.debug("REST request to save Quantitat : {}", quantitatDTO);
        if (quantitatDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("quantitat", "idexists", "A new quantitat cannot already have an ID")).body(null);
        }
        QuantitatDTO result;
		try {
			result = quantitatService.save(quantitatDTO);
		} catch (QuantitatException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("quantitat", "procesactiu", e.getMessage())).body(null);
		}
        return ResponseEntity.created(new URI("/api/quantitats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quantitat", result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quantitats : get all the quantitats.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of quantitats in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/public/quantitats")
    @Timed
    public ResponseEntity<List<QuantitatDTO>> getAllQuantitats(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Quantitats");
        Page<QuantitatDTO> page = quantitatService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quantitats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quantitats/:id : get the "id" quantitat.
     *
     * @param id the id of the quantitatDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quantitatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/quantitats/{id}")
    @Timed
    public ResponseEntity<QuantitatDTO> getQuantitat(@PathVariable Long id) {
        log.debug("REST request to get Quantitat : {}", id);
        QuantitatDTO quantitatDTO = quantitatService.findOne(id);
        return Optional.ofNullable(quantitatDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /quantitats/activa : get the active quantitat.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the quantitatDTO, or with status 404 (Not Found)
     */
    @GetMapping("/public/quantitats/activa")
    @Timed
    public ResponseEntity<QuantitatDTO> getQuantitatActiva() {
        log.debug("REST request to get Quantitat Activa");
        QuantitatDTO quantitatDTO;
		try {
			quantitatDTO = quantitatService.findActiva();
		} catch (QuantitatException e) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("quantitat", "actiunoexist", e.getMessage())).body(null);
		}
        return Optional.ofNullable(quantitatDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
