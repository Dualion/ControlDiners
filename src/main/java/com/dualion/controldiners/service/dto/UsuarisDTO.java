package com.dualion.controldiners.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Usuaris entity.
 */
public class UsuarisDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    private String nom;

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    private ZonedDateTime dataInici;

    private Boolean actiu;


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
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public void setDataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
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

        UsuarisDTO usuarisDTO = (UsuarisDTO) o;

        if ( ! Objects.equals(id, usuarisDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UsuarisDTO{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", email='" + email + "'" +
            ", dataInici='" + dataInici + "'" +
            ", actiu='" + actiu + "'" +
            '}';
    }
}
