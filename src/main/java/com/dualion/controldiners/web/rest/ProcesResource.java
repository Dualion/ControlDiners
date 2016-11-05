package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dualion.controldiners.service.ProcesService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.service.dto.ProcesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Proces.
 */
@RestController
@RequestMapping("/api")
public class ProcesResource {

    private final Logger log = LoggerFactory.getLogger(ProcesResource.class);
        
    @Inject
    private ProcesService procesService;

    /**
     * POST  /proces : Create a new proces.
     *
     * @param procesDTO the procesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procesDTO, or with status 400 (Bad Request) if the proces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proces")
    @Timed
    public ResponseEntity<ProcesDTO> createProces(@RequestBody ProcesDTO procesDTO) throws URISyntaxException {
        log.debug("REST request to save Proces : {}", procesDTO);
        if (procesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proces", "idexists", "A new proces cannot already have an ID")).body(null);
        }
        ProcesDTO result = procesService.save(procesDTO);
        return ResponseEntity.created(new URI("/api/proces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proces", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proces : Updates an existing proces.
     *
     * @param procesDTO the procesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procesDTO,
     * or with status 400 (Bad Request) if the procesDTO is not valid,
     * or with status 500 (Internal Server Error) if the procesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proces")
    @Timed
    public ResponseEntity<ProcesDTO> updateProces(@RequestBody ProcesDTO procesDTO) throws URISyntaxException {
        log.debug("REST request to update Proces : {}", procesDTO);
        if (procesDTO.getId() == null) {
            return createProces(procesDTO);
        }
        ProcesDTO result = procesService.save(procesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proces", procesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proces : get all the proces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of proces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/proces")
    @Timed
    public ResponseEntity<List<ProcesDTO>> getAllProces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Proces");
        Page<ProcesDTO> page = procesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proces/:id : get the "id" proces.
     *
     * @param id the id of the procesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/proces/{id}")
    @Timed
    public ResponseEntity<ProcesDTO> getProces(@PathVariable Long id) {
        log.debug("REST request to get Proces : {}", id);
        ProcesDTO procesDTO = procesService.findOne(id);
        return Optional.ofNullable(procesDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proces/:id : delete the "id" proces.
     *
     * @param id the id of the procesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proces/{id}")
    @Timed
    public ResponseEntity<Void> deleteProces(@PathVariable Long id) {
        log.debug("REST request to delete Proces : {}", id);
        procesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proces", id.toString())).build();
    }

}
