package com.dualion.controldiners.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Pot entity.
 */
public class PotDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String nom;

    private ZonedDateTime dataInici;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public void setDataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
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
            ", nom='" + nom + "'" +
            ", dataInici='" + dataInici + "'" +
            '}';
    }
}
