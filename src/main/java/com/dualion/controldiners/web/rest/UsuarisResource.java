package com.dualion.controldiners.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dualion.controldiners.service.UsuarisService;
import com.dualion.controldiners.web.rest.util.HeaderUtil;
import com.dualion.controldiners.web.rest.util.PaginationUtil;
import com.dualion.controldiners.service.dto.UsuarisDTO;
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
 * REST controller for managing Usuaris.
 */
@RestController
@RequestMapping("/api")
public class UsuarisResource {

    private final Logger log = LoggerFactory.getLogger(UsuarisResource.class);
        
    @Inject
    private UsuarisService usuarisService;

    /**
     * POST  /usuarises : Create a new usuaris.
     *
     * @param usuarisDTO the usuarisDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuarisDTO, or with status 400 (Bad Request) if the usuaris has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuarises")
    @Timed
    public ResponseEntity<UsuarisDTO> createUsuaris(@Valid @RequestBody UsuarisDTO usuarisDTO) throws URISyntaxException {
        log.debug("REST request to save Usuaris : {}", usuarisDTO);
        if (usuarisDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("usuaris", "idexists", "A new usuaris cannot already have an ID")).body(null);
        }
        UsuarisDTO result = usuarisService.save(usuarisDTO);
        return ResponseEntity.created(new URI("/api/usuarises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("usuaris", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuarises : Updates an existing usuaris.
     *
     * @param usuarisDTO the usuarisDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarisDTO,
     * or with status 400 (Bad Request) if the usuarisDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuarisDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuarises")
    @Timed
    public ResponseEntity<UsuarisDTO> updateUsuaris(@Valid @RequestBody UsuarisDTO usuarisDTO) throws URISyntaxException {
        log.debug("REST request to update Usuaris : {}", usuarisDTO);
        if (usuarisDTO.getId() == null) {
            return createUsuaris(usuarisDTO);
        }
        UsuarisDTO result = usuarisService.save(usuarisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("usuaris", usuarisDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuarises : get all the usuarises.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usuarises in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/usuarises")
    @Timed
    public ResponseEntity<List<UsuarisDTO>> getAllUsuarises(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Usuarises");
        Page<UsuarisDTO> page = usuarisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuarises");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /usuarises/:id : get the "id" usuaris.
     *
     * @param id the id of the usuarisDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuarisDTO, or with status 404 (Not Found)
     */
    @GetMapping("/usuarises/{id}")
    @Timed
    public ResponseEntity<UsuarisDTO> getUsuaris(@PathVariable Long id) {
        log.debug("REST request to get Usuaris : {}", id);
        UsuarisDTO usuarisDTO = usuarisService.findOne(id);
        return Optional.ofNullable(usuarisDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /usuarises/:id : delete the "id" usuaris.
     *
     * @param id the id of the usuarisDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usuarises/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsuaris(@PathVariable Long id) {
        log.debug("REST request to delete Usuaris : {}", id);
        usuarisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("usuaris", id.toString())).build();
    }

}
