package com.dualion.controldiners.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Quantitat.
 */
@Entity
@Table(name = "quantitat")
public class Quantitat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "diners", nullable = false)
    private Float diners;

    @Column(name = "actiu", nullable = false)
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

    public Quantitat diners(Float diners) {
        this.diners = diners;
        return this;
    }

    public void setDiners(Float diners) {
        this.diners = diners;
    }

    public Boolean isActiu() {
        return actiu;
    }

    public Quantitat actiu(Boolean actiu) {
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
        Quantitat quantitat = (Quantitat) o;
        if(quantitat.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, quantitat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Quantitat{" +
            "id=" + id +
            ", diners='" + diners + "'" +
            ", actiu='" + actiu + "'" +
            '}';
    }
}
