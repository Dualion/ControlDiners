package com.dualion.controldiners.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Proces entity.
 */
public class ProcesDTO implements Serializable {

    private Long id;

    private ZonedDateTime dataInici;

    private Boolean estat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public void setDataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
    }
    public Boolean getEstat() {
        return estat;
    }

    public void setEstat(Boolean estat) {
        this.estat = estat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcesDTO procesDTO = (ProcesDTO) o;

        if ( ! Objects.equals(id, procesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProcesDTO{" +
            "id=" + id +
            ", dataInici='" + dataInici + "'" +
            ", estat='" + estat + "'" +
            '}';
    }
}
