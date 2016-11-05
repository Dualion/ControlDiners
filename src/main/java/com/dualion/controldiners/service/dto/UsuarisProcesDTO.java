package com.dualion.controldiners.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the UsuarisProces entity.
 */
public class UsuarisProcesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Float diners;


    private Long procesId;
    
    private Long usuarisId;
    

    private String usuarisNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Float getDiners() {
        return diners;
    }

    public void setDiners(Float diners) {
        this.diners = diners;
    }

    public Long getProcesId() {
        return procesId;
    }

    public void setProcesId(Long procesId) {
        this.procesId = procesId;
    }

    public Long getUsuarisId() {
        return usuarisId;
    }

    public void setUsuarisId(Long usuarisId) {
        this.usuarisId = usuarisId;
    }


    public String getUsuarisNom() {
        return usuarisNom;
    }

    public void setUsuarisNom(String usuarisNom) {
        this.usuarisNom = usuarisNom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarisProcesDTO usuarisProcesDTO = (UsuarisProcesDTO) o;

        if ( ! Objects.equals(id, usuarisProcesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UsuarisProcesDTO{" +
            "id=" + id +
            ", diners='" + diners + "'" +
            '}';
    }
}
