package com.br.donna.service;

import com.br.donna.service.dto.LawyerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Lawyer.
 */
public interface LawyerService {

    /**
     * Save a lawyer.
     *
     * @param lawyerDTO the entity to save
     * @return the persisted entity
     */
    LawyerDTO save(LawyerDTO lawyerDTO);

    /**
     *  Get all the lawyers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LawyerDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" lawyer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LawyerDTO findOne(Long id);

    /**
     *  Delete the "id" lawyer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
