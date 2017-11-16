package com.br.donna.service.impl;

import com.br.donna.service.LawyerService;
import com.br.donna.domain.Lawyer;
import com.br.donna.repository.LawyerRepository;
import com.br.donna.service.dto.LawyerDTO;
import com.br.donna.service.mapper.LawyerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Lawyer.
 */
@Service
@Transactional
public class LawyerServiceImpl implements LawyerService{

    private final Logger log = LoggerFactory.getLogger(LawyerServiceImpl.class);

    private final LawyerRepository lawyerRepository;

    private final LawyerMapper lawyerMapper;

    public LawyerServiceImpl(LawyerRepository lawyerRepository, LawyerMapper lawyerMapper) {
        this.lawyerRepository = lawyerRepository;
        this.lawyerMapper = lawyerMapper;
    }

    /**
     * Save a lawyer.
     *
     * @param lawyerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LawyerDTO save(LawyerDTO lawyerDTO) {
        log.debug("Request to save Lawyer : {}", lawyerDTO);
        Lawyer lawyer = lawyerMapper.toEntity(lawyerDTO);
        lawyer = lawyerRepository.save(lawyer);
        return lawyerMapper.toDto(lawyer);
    }

    /**
     *  Get all the lawyers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LawyerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lawyers");
        return lawyerRepository.findAll(pageable)
            .map(lawyerMapper::toDto);
    }

    /**
     *  Get one lawyer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LawyerDTO findOne(Long id) {
        log.debug("Request to get Lawyer : {}", id);
        Lawyer lawyer = lawyerRepository.findOne(id);
        return lawyerMapper.toDto(lawyer);
    }

    /**
     *  Delete the  lawyer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lawyer : {}", id);
        lawyerRepository.delete(id);
    }
}
