package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.DinersPotDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DinersPot and its DTO DinersPotDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DinersPotMapper {

    @Mapping(source = "pot.id", target = "potId")
    DinersPotDTO dinersPotToDinersPotDTO(DinersPot dinersPot);

    List<DinersPotDTO> dinersPotsToDinersPotDTOs(List<DinersPot> dinersPots);

    @Mapping(source = "potId", target = "pot")
    DinersPot dinersPotDTOToDinersPot(DinersPotDTO dinersPotDTO);

    List<DinersPot> dinersPotDTOsToDinersPots(List<DinersPotDTO> dinersPotDTOs);

    default Pot potFromId(Long id) {
        if (id == null) {
            return null;
        }
        Pot pot = new Pot();
        pot.setId(id);
        return pot;
    }
}
