package com.br.donna.service;

import com.br.donna.service.dto.LegalProcessDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LegalProcess.
 */
public interface LegalProcessService {

    /**
     * Save a legalProcess.
     *
     * @param legalProcessDTO the entity to save
     * @return the persisted entity
     */
    LegalProcessDTO save(LegalProcessDTO legalProcessDTO);

    /**
     *  Get all the legalProcesses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LegalProcessDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" legalProcess.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LegalProcessDTO findOne(Long id);

    /**
     *  Delete the "id" legalProcess.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
