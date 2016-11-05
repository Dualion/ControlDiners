package com.dualion.controldiners.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UsuarisProces.
 */
@Entity
@Table(name = "usuaris_proces")
public class UsuarisProces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "diners", nullable = false)
    private Float diners;

    @ManyToOne
    private Proces proces;

    @ManyToOne
    private Usuaris usuaris;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getDiners() {
        return diners;
    }

    public UsuarisProces diners(Float diners) {
        this.diners = diners;
        return this;
    }

    public void setDiners(Float diners) {
        this.diners = diners;
    }

    public Proces getProces() {
        return proces;
    }

    public UsuarisProces proces(Proces proces) {
        this.proces = proces;
        return this;
    }

    public void setProces(Proces proces) {
        this.proces = proces;
    }

    public Usuaris getUsuaris() {
        return usuaris;
    }

    public UsuarisProces usuaris(Usuaris usuaris) {
        this.usuaris = usuaris;
        return this;
    }

    public void setUsuaris(Usuaris usuaris) {
        this.usuaris = usuaris;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsuarisProces usuarisProces = (UsuarisProces) o;
        if(usuarisProces.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, usuarisProces.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UsuarisProces{" +
            "id=" + id +
            ", diners='" + diners + "'" +
            '}';
    }
}
