package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.QuantitatDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Quantitat and its DTO QuantitatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuantitatMapper {

    QuantitatDTO quantitatToQuantitatDTO(Quantitat quantitat);

    List<QuantitatDTO> quantitatsToQuantitatDTOs(List<Quantitat> quantitats);

    Quantitat quantitatDTOToQuantitat(QuantitatDTO quantitatDTO);

    List<Quantitat> quantitatDTOsToQuantitats(List<QuantitatDTO> quantitatDTOs);
}
