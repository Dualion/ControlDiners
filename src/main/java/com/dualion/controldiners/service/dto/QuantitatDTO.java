package com.dualion.controldiners.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Quantitat entity.
 */
public class QuantitatDTO implements Serializable {

    private Long id;

    @NotNull
    private Float diners;

    @NotNull
    private Boolean actiu;


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
    public Boolean getActiu() {
        return actiu;
    }

    public void setActiu(Boolean actiu) {
        this.actiu = actiu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuantitatDTO quantitatDTO = (QuantitatDTO) o;

        if ( ! Objects.equals(id, quantitatDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuantitatDTO{" +
            "id=" + id +
            ", diners='" + diners + "'" +
            ", actiu='" + actiu + "'" +
            '}';
    }
}
