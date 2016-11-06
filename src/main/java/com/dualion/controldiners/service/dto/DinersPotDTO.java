package com.dualion.controldiners.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the DinersPot entity.
 */
public class DinersPotDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private Long id;

    @NotNull
    private Float dinersTotals;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    @Size(min = 3, max = 100)
    private String descripcio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Float getDinersTotals() {
        return dinersTotals;
    }

    public void setDinersTotals(Float dinersTotals) {
        this.dinersTotals = dinersTotals;
    }
    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }
    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DinersPotDTO dinersPotDTO = (DinersPotDTO) o;

        if ( ! Objects.equals(id, dinersPotDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DinersPotDTO{" +
            "id=" + id +
            ", dinersTotals='" + dinersTotals + "'" +
            ", data='" + data + "'" +
            ", descripcio='" + descripcio + "'" +
            '}';
    }
}
