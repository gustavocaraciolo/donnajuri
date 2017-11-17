package com.br.donna.service.impl;

import com.br.donna.service.LegalProcessService;
import com.br.donna.domain.LegalProcess;
import com.br.donna.repository.LegalProcessRepository;
import com.br.donna.service.dto.LegalProcessDTO;
import com.br.donna.service.mapper.LegalProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LegalProcess.
 */
@Service
@Transactional
public class LegalProcessServiceImpl implements LegalProcessService{

    private final Logger log = LoggerFactory.getLogger(LegalProcessServiceImpl.class);

    private final LegalProcessRepository legalProcessRepository;

    private final LegalProcessMapper legalProcessMapper;

    public LegalProcessServiceImpl(LegalProcessRepository legalProcessRepository, LegalProcessMapper legalProcessMapper) {
        this.legalProcessRepository = legalProcessRepository;
        this.legalProcessMapper = legalProcessMapper;
    }

    /**
     * Save a legalProcess.
     *
     * @param legalProcessDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LegalProcessDTO save(LegalProcessDTO legalProcessDTO) {
        log.debug("Request to save LegalProcess : {}", legalProcessDTO);
        LegalProcess legalProcess = legalProcessMapper.toEntity(legalProcessDTO);
        legalProcess = legalProcessRepository.save(legalProcess);
        return legalProcessMapper.toDto(legalProcess);
    }

    /**
     *  Get all the legalProcesses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LegalProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LegalProcesses");
        return legalProcessRepository.findAll(pageable)
            .map(legalProcessMapper::toDto);
    }

    /**
     *  Get one legalProcess by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LegalProcessDTO findOne(Long id) {
        log.debug("Request to get LegalProcess : {}", id);
        LegalProcess legalProcess = legalProcessRepository.findOne(id);
        return legalProcessMapper.toDto(legalProcess);
    }

    /**
     *  Delete the  legalProcess by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LegalProcess : {}", id);
        legalProcessRepository.delete(id);
    }
}
