package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.UsuarisDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Usuaris and its DTO UsuarisDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarisMapper {

    UsuarisDTO usuarisToUsuarisDTO(Usuaris usuaris);

    List<UsuarisDTO> usuarisesToUsuarisDTOs(List<Usuaris> usuarises);

    Usuaris usuarisDTOToUsuaris(UsuarisDTO usuarisDTO);

    List<Usuaris> usuarisDTOsToUsuarises(List<UsuarisDTO> usuarisDTOs);
}
