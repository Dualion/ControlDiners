package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dualion.controldiners.service.PotService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.service.dto.PotDTO;
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
@RequestMapping("/api")
public class PotResource {

    private final Logger log = LoggerFactory.getLogger(PotResource.class);
        
    @Inject
    private PotService potService;

    /**
     * POST  /pots : Create a new pot.
     *
     * @param potDTO the potDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new potDTO, or with status 400 (Bad Request) if the pot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pots")
    @Timed
    public ResponseEntity<PotDTO> createPot(@Valid @RequestBody PotDTO potDTO) throws URISyntaxException {
        log.debug("REST request to save Pot : {}", potDTO);
        if (potDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pot", "idexists", "A new pot cannot already have an ID")).body(null);
        }
        PotDTO result = potService.save(potDTO);
        return ResponseEntity.created(new URI("/api/pots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pots : Updates an existing pot.
     *
     * @param potDTO the potDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated potDTO,
     * or with status 400 (Bad Request) if the potDTO is not valid,
     * or with status 500 (Internal Server Error) if the potDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pots")
    @Timed
    public ResponseEntity<PotDTO> updatePot(@Valid @RequestBody PotDTO potDTO) throws URISyntaxException {
        log.debug("REST request to update Pot : {}", potDTO);
        if (potDTO.getId() == null) {
            return createPot(potDTO);
        }
        PotDTO result = potService.save(potDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pot", potDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pots : get all the pots.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pots in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pots")
    @Timed
    public ResponseEntity<List<PotDTO>> getAllPots(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pots");
        Page<PotDTO> page = potService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pots/:id : get the "id" pot.
     *
     * @param id the id of the potDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the potDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pots/{id}")
    @Timed
    public ResponseEntity<PotDTO> getPot(@PathVariable Long id) {
        log.debug("REST request to get Pot : {}", id);
        PotDTO potDTO = potService.findOne(id);
        return Optional.ofNullable(potDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pots/:id : delete the "id" pot.
     *
     * @param id the id of the potDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pots/{id}")
    @Timed
    public ResponseEntity<Void> deletePot(@PathVariable Long id) {
        log.debug("REST request to delete Pot : {}", id);
        potService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pot", id.toString())).build();
    }

}
