package com.br.donna.service.impl;

import com.br.donna.service.OfficeService;
import com.br.donna.domain.Office;
import com.br.donna.repository.OfficeRepository;
import com.br.donna.service.dto.OfficeDTO;
import com.br.donna.service.mapper.OfficeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Office.
 */
@Service
@Transactional
public class OfficeServiceImpl implements OfficeService{

    private final Logger log = LoggerFactory.getLogger(OfficeServiceImpl.class);

    private final OfficeRepository officeRepository;

    private final OfficeMapper officeMapper;

    public OfficeServiceImpl(OfficeRepository officeRepository, OfficeMapper officeMapper) {
        this.officeRepository = officeRepository;
        this.officeMapper = officeMapper;
    }

    /**
     * Save a office.
     *
     * @param officeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OfficeDTO save(OfficeDTO officeDTO) {
        log.debug("Request to save Office : {}", officeDTO);
        Office office = officeMapper.toEntity(officeDTO);
        office = officeRepository.save(office);
        return officeMapper.toDto(office);
    }

    /**
     *  Get all the offices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OfficeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Offices");
        return officeRepository.findAll(pageable)
            .map(officeMapper::toDto);
    }

    /**
     *  Get one office by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OfficeDTO findOne(Long id) {
        log.debug("Request to get Office : {}", id);
        Office office = officeRepository.findOne(id);
        return officeMapper.toDto(office);
    }

    /**
     *  Delete the  office by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Office : {}", id);
        officeRepository.delete(id);
    }
}
