package com.br.donna.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.donna.service.LegalProcessService;
import com.br.donna.web.rest.errors.BadRequestAlertException;
import com.br.donna.web.rest.util.HeaderUtil;
import com.br.donna.web.rest.util.PaginationUtil;
import com.br.donna.service.dto.LegalProcessDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LegalProcess.
 */
@RestController
@RequestMapping("/api")
public class LegalProcessResource {

    private final Logger log = LoggerFactory.getLogger(LegalProcessResource.class);

    private static final String ENTITY_NAME = "legalProcess";

    private final LegalProcessService legalProcessService;

    public LegalProcessResource(LegalProcessService legalProcessService) {
        this.legalProcessService = legalProcessService;
    }

    /**
     * POST  /legal-processes : Create a new legalProcess.
     *
     * @param legalProcessDTO the legalProcessDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new legalProcessDTO, or with status 400 (Bad Request) if the legalProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/legal-processes")
    @Timed
    public ResponseEntity<LegalProcessDTO> createLegalProcess(@Valid @RequestBody LegalProcessDTO legalProcessDTO) throws URISyntaxException {
        log.debug("REST request to save LegalProcess : {}", legalProcessDTO);
        if (legalProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new legalProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegalProcessDTO result = legalProcessService.save(legalProcessDTO);
        return ResponseEntity.created(new URI("/api/legal-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /legal-processes : Updates an existing legalProcess.
     *
     * @param legalProcessDTO the legalProcessDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated legalProcessDTO,
     * or with status 400 (Bad Request) if the legalProcessDTO is not valid,
     * or with status 500 (Internal Server Error) if the legalProcessDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/legal-processes")
    @Timed
    public ResponseEntity<LegalProcessDTO> updateLegalProcess(@Valid @RequestBody LegalProcessDTO legalProcessDTO) throws URISyntaxException {
        log.debug("REST request to update LegalProcess : {}", legalProcessDTO);
        if (legalProcessDTO.getId() == null) {
            return createLegalProcess(legalProcessDTO);
        }
        LegalProcessDTO result = legalProcessService.save(legalProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, legalProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /legal-processes : get all the legalProcesses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of legalProcesses in body
     */
    @GetMapping("/legal-processes")
    @Timed
    public ResponseEntity<List<LegalProcessDTO>> getAllLegalProcesses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LegalProcesses");
        Page<LegalProcessDTO> page = legalProcessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/legal-processes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /legal-processes/:id : get the "id" legalProcess.
     *
     * @param id the id of the legalProcessDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the legalProcessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/legal-processes/{id}")
    @Timed
    public ResponseEntity<LegalProcessDTO> getLegalProcess(@PathVariable Long id) {
        log.debug("REST request to get LegalProcess : {}", id);
        LegalProcessDTO legalProcessDTO = legalProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(legalProcessDTO));
    }

    /**
     * DELETE  /legal-processes/:id : delete the "id" legalProcess.
     *
     * @param id the id of the legalProcessDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/legal-processes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLegalProcess(@PathVariable Long id) {
        log.debug("REST request to delete LegalProcess : {}", id);
        legalProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
