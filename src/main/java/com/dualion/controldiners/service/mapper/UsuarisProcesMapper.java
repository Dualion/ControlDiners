package com.dualion.controldiners.service.mapper;

import com.dualion.controldiners.domain.*;
import com.dualion.controldiners.service.dto.UsuarisProcesDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity UsuarisProces and its DTO UsuarisProcesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarisProcesMapper {

    @Mapping(source = "proces.id", target = "procesId")
    @Mapping(source = "usuaris.id", target = "usuarisId")
    @Mapping(source = "usuaris.nom", target = "usuarisNom")
    UsuarisProcesDTO usuarisProcesToUsuarisProcesDTO(UsuarisProces usuarisProces);

    List<UsuarisProcesDTO> usuarisProcesToUsuarisProcesDTOs(List<UsuarisProces> usuarisProces);

    @Mapping(source = "procesId", target = "proces")
    @Mapping(source = "usuarisId", target = "usuaris")
    UsuarisProces usuarisProcesDTOToUsuarisProces(UsuarisProcesDTO usuarisProcesDTO);

    List<UsuarisProces> usuarisProcesDTOsToUsuarisProces(List<UsuarisProcesDTO> usuarisProcesDTOs);

    default Proces procesFromId(Long id) {
        if (id == null) {
            return null;
        }
        Proces proces = new Proces();
        proces.setId(id);
        return proces;
    }

    default Usuaris usuarisFromId(Long id) {
        if (id == null) {
            return null;
        }
        Usuaris usuaris = new Usuaris();
        usuaris.setId(id);
        return usuaris;
    }
}
