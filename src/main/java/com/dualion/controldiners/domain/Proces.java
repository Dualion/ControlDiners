package com.dualion.controldiners.domain;


import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Proces.
 */
@Entity
@Table(name = "proces")
public class Proces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreatedDate
    @Column(name = "data_inici")
    private ZonedDateTime dataInici = ZonedDateTime.now();

    @Column(name = "estat")
    private Boolean estat;

    @ManyToOne
    private Pot pot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataInici() {
        return dataInici;
    }

    public Proces dataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
        return this;
    }

    public void setDataInici(ZonedDateTime dataInici) {
        this.dataInici = dataInici;
    }

    public Boolean isEstat() {
        return estat;
    }

    public Proces estat(Boolean estat) {
        this.estat = estat;
        return this;
    }

    public void setEstat(Boolean estat) {
        this.estat = estat;
    }

    public Pot getPot() {
        return pot;
    }

    public Proces pot(Pot pot) {
        this.pot = pot;
        return this;
    }

    public void setPot(Pot pot) {
        this.pot = pot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proces proces = (Proces) o;
        if(proces.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, proces.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Proces{" +
            "id=" + id +
            ", dataInici='" + dataInici + "'" +
            ", estat='" + estat + "'" +
            '}';
    }
}
