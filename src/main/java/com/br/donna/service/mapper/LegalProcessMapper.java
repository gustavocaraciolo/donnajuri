package com.br.donna.service.mapper;

import com.br.donna.domain.*;
import com.br.donna.service.dto.LegalProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LegalProcess and its DTO LegalProcessDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LegalProcessMapper extends EntityMapper<LegalProcessDTO, LegalProcess> {





    default LegalProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        LegalProcess legalProcess = new LegalProcess();
        legalProcess.setId(id);
        return legalProcess;
    }
}
