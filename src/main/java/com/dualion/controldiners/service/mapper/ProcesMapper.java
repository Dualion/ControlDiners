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

    @Mapping(source = "pot.id", target = "potId")
    ProcesDTO procesToProcesDTO(Proces proces);

    List<ProcesDTO> procesToProcesDTOs(List<Proces> proces);

    @Mapping(source = "potId", target = "pot")
    Proces procesDTOToProces(ProcesDTO procesDTO);

    List<Proces> procesDTOsToProces(List<ProcesDTO> procesDTOs);

    default Pot potFromId(Long id) {
        if (id == null) {
            return null;
        }
        Pot pot = new Pot();
        pot.setId(id);
        return pot;
    }
}
