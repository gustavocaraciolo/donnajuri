package com.br.donna.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.br.donna.service.LawyerService;
import com.br.donna.web.rest.errors.BadRequestAlertException;
import com.br.donna.web.rest.util.HeaderUtil;
import com.br.donna.web.rest.util.PaginationUtil;
import com.br.donna.service.dto.LawyerDTO;
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
 * REST controller for managing Lawyer.
 */
@RestController
@RequestMapping("/api")
public class LawyerResource {

    private final Logger log = LoggerFactory.getLogger(LawyerResource.class);

    private static final String ENTITY_NAME = "lawyer";

    private final LawyerService lawyerService;

    public LawyerResource(LawyerService lawyerService) {
        this.lawyerService = lawyerService;
    }

    /**
     * POST  /lawyers : Create a new lawyer.
     *
     * @param lawyerDTO the lawyerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lawyerDTO, or with status 400 (Bad Request) if the lawyer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lawyers")
    @Timed
    public ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO) throws URISyntaxException {
        log.debug("REST request to save Lawyer : {}", lawyerDTO);
        if (lawyerDTO.getId() != null) {
            throw new BadRequestAlertException("A new lawyer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LawyerDTO result = lawyerService.save(lawyerDTO);
        return ResponseEntity.created(new URI("/api/lawyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lawyers : Updates an existing lawyer.
     *
     * @param lawyerDTO the lawyerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lawyerDTO,
     * or with status 400 (Bad Request) if the lawyerDTO is not valid,
     * or with status 500 (Internal Server Error) if the lawyerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lawyers")
    @Timed
    public ResponseEntity<LawyerDTO> updateLawyer(@Valid @RequestBody LawyerDTO lawyerDTO) throws URISyntaxException {
        log.debug("REST request to update Lawyer : {}", lawyerDTO);
        if (lawyerDTO.getId() == null) {
            return createLawyer(lawyerDTO);
        }
        LawyerDTO result = lawyerService.save(lawyerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lawyerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lawyers : get all the lawyers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lawyers in body
     */
    @GetMapping("/lawyers")
    @Timed
    public ResponseEntity<List<LawyerDTO>> getAllLawyers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Lawyers");
        Page<LawyerDTO> page = lawyerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lawyers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lawyers/:id : get the "id" lawyer.
     *
     * @param id the id of the lawyerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lawyerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lawyers/{id}")
    @Timed
    public ResponseEntity<LawyerDTO> getLawyer(@PathVariable Long id) {
        log.debug("REST request to get Lawyer : {}", id);
        LawyerDTO lawyerDTO = lawyerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lawyerDTO));
    }

    /**
     * DELETE  /lawyers/:id : delete the "id" lawyer.
     *
     * @param id the id of the lawyerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lawyers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLawyer(@PathVariable Long id) {
        log.debug("REST request to delete Lawyer : {}", id);
        lawyerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
