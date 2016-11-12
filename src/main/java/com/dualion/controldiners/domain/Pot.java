package com.dualion.controldiners.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Pot.
 */
@Entity
@Table(name = "pot")
public class Pot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "nom", length = 20, nullable = false)
    private String nom;

    @Column(name = "data_inici")
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

    public Pot nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public Pot dataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
        return this;
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
        Pot pot = (Pot) o;
        if(pot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pot{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", dataInici='" + dataInici + "'" +
            '}';
    }
}
