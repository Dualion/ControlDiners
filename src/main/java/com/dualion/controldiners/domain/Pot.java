package com.dualion.controldiners.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.annotation.CreatedDate;

import com.dualion.controldiners.domain.util.BigDecimalConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Convert(converter=BigDecimalConverter.class)
    @Column(name = "diners_totals", nullable = false)
    private BigDecimal dinersTotals;
    
    @NotNull
    @CreatedDate
    @Column(name = "data", nullable = false)
    private ZonedDateTime data = ZonedDateTime.now();

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "descripcio", length = 100, nullable = false)
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

    public Pot dinersTotals(BigDecimal dinersTotals) {
        this.dinersTotals = dinersTotals.setScale(2, RoundingMode.CEILING);
        return this;
    }

    public void setDinersTotals(BigDecimal dinersTotals) {
        this.dinersTotals = dinersTotals.setScale(2, RoundingMode.CEILING);
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Pot data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public Pot descripcio(String descripcio) {
        this.descripcio = descripcio;
        return this;
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
            ", dinersTotals='" + dinersTotals + "'" +
            ", data='" + data + "'" +
            ", descripcio='" + descripcio + "'" +
            '}';
    }
}
