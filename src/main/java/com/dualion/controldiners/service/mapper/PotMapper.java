package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.PotDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Pot and its DTO PotDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PotMapper {

    PotDTO potToPotDTO(Pot pot);

    List<PotDTO> potsToPotDTOs(List<Pot> pots);

    Pot potDTOToPot(PotDTO potDTO);

    List<Pot> potDTOsToPots(List<PotDTO> potDTOs);
}
