package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dualion.controldiners.service.UsuarisProcesService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;
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
 * REST controller for managing UsuarisProces.
 */
@RestController
@RequestMapping("/api")
public class UsuarisProcesResource {

    private final Logger log = LoggerFactory.getLogger(UsuarisProcesResource.class);
        
    @Inject
    private UsuarisProcesService usuarisProcesService;

    /**
     * POST  /usuaris-proces : Create a new usuarisProces.
     *
     * @param usuarisProcesDTO the usuarisProcesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuarisProcesDTO, or with status 400 (Bad Request) if the usuarisProces has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuaris-proces")
    @Timed
    public ResponseEntity<UsuarisProcesDTO> createUsuarisProces(@Valid @RequestBody UsuarisProcesDTO usuarisProcesDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarisProces : {}", usuarisProcesDTO);
        if (usuarisProcesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usuarisProces", "idexists", "A new usuarisProces cannot already have an ID")).body(null);
        }
        UsuarisProcesDTO result = usuarisProcesService.save(usuarisProcesDTO);
        return ResponseEntity.created(new URI("/api/usuaris-proces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("usuarisProces", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuaris-proces : Updates an existing usuarisProces.
     *
     * @param usuarisProcesDTO the usuarisProcesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarisProcesDTO,
     * or with status 400 (Bad Request) if the usuarisProcesDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuarisProcesDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuaris-proces")
    @Timed
    public ResponseEntity<UsuarisProcesDTO> updateUsuarisProces(@Valid @RequestBody UsuarisProcesDTO usuarisProcesDTO) throws URISyntaxException {
        log.debug("REST request to update UsuarisProces : {}", usuarisProcesDTO);
        if (usuarisProcesDTO.getId() == null) {
            return createUsuarisProces(usuarisProcesDTO);
        }
        UsuarisProcesDTO result = usuarisProcesService.save(usuarisProcesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("usuarisProces", usuarisProcesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuaris-proces : get all the usuarisProces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usuarisProces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/usuaris-proces")
    @Timed
    public ResponseEntity<List<UsuarisProcesDTO>> getAllUsuarisProces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UsuarisProces");
        Page<UsuarisProcesDTO> page = usuarisProcesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuaris-proces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usuaris-proces/:id : get the "id" usuarisProces.
     *
     * @param id the id of the usuarisProcesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuarisProcesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/usuaris-proces/{id}")
    @Timed
    public ResponseEntity<UsuarisProcesDTO> getUsuarisProces(@PathVariable Long id) {
        log.debug("REST request to get UsuarisProces : {}", id);
        UsuarisProcesDTO usuarisProcesDTO = usuarisProcesService.findOne(id);
        return Optional.ofNullable(usuarisProcesDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /usuaris-proces/:id : delete the "id" usuarisProces.
     *
     * @param id the id of the usuarisProcesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usuaris-proces/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsuarisProces(@PathVariable Long id) {
        log.debug("REST request to delete UsuarisProces : {}", id);
        usuarisProcesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usuarisProces", id.toString())).build();
    }

}
