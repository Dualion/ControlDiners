package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.ProcesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Proces and its DTO ProcesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcesMapper {

    ProcesDTO procesToProcesDTO(Proces proces);

    List<ProcesDTO> procesToProcesDTOs(List<Proces> proces);

    Proces procesDTOToProces(ProcesDTO procesDTO);

    List<Proces> procesDTOsToProces(List<ProcesDTO> procesDTOs);
}
