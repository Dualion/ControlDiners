package com.dualion.controldiners.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


/**
 * A DTO for the Pot entity.
 */
public class PotDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private BigDecimal dinersTotals;

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
    public BigDecimal getDinersTotals() {
        return dinersTotals;
    }

    public void setDinersTotals(BigDecimal dinersTotals) {
        this.dinersTotals = dinersTotals.setScale(2, RoundingMode.CEILING);
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

        PotDTO potDTO = (PotDTO) o;

        if ( ! Objects.equals(id, potDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PotDTO{" +
            "id=" + id +
            ", dinersTotals='" + dinersTotals + "'" +
            ", data='" + data + "'" +
            ", descripcio='" + descripcio + "'" +
            '}';
    }
}
