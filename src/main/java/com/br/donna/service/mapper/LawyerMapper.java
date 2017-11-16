package com.br.donna.service.mapper;

import com.br.donna.domain.*;
import com.br.donna.service.dto.LawyerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lawyer and its DTO LawyerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LawyerMapper extends EntityMapper<LawyerDTO, Lawyer> {

    

    @Mapping(target = "legalProcesses", ignore = true)
    Lawyer toEntity(LawyerDTO lawyerDTO);

    default Lawyer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lawyer lawyer = new Lawyer();
        lawyer.setId(id);
        return lawyer;
    }
}
