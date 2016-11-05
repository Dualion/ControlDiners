package com.dualion.controldiners.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Usuaris.
 */
@Entity
@Table(name = "usuaris")
public class Usuaris implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "nom", length = 20, nullable = false)
    private String nom;

    @NotNull
    @Email
    @Size(min = 5, max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @NotNull
    @CreatedDate
    @Column(name = "data_inici", nullable = false)
    private ZonedDateTime dataInici = ZonedDateTime.now();

    @Column(name = "actiu")
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

    public Usuaris nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public Usuaris email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public Usuaris dataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
        return this;
    }

    public void setDataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
    }

    public Boolean isActiu() {
        return actiu;
    }

    public Usuaris actiu(Boolean actiu) {
        this.actiu = actiu;
        return this;
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
        Usuaris usuaris = (Usuaris) o;
        if(usuaris.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usuaris.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuaris{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", email='" + email + "'" +
            ", dataInici='" + dataInici + "'" +
            ", actiu='" + actiu + "'" +
            '}';
    }
}
