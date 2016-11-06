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

    DinersPotDTO dinersPotToDinersPotDTO(DinersPot dinersPot);

    List<DinersPotDTO> dinersPotsToDinersPotDTOs(List<DinersPot> dinersPots);

    DinersPot dinersPotDTOToDinersPot(DinersPotDTO dinersPotDTO);

    List<DinersPot> dinersPotDTOsToDinersPots(List<DinersPotDTO> dinersPotDTOs);
}
