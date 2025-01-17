package com.ferbo.sgp.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_pais")
@NamedQueries({
        @NamedQuery(name = "Pais.findById", query = "SELECT d FROM Pais d WHERE d.id = :cdPais"),
        @NamedQuery(name = "Pais.findByClave", query = "SELECT d FROM Pais d WHERE d.clave = :clavePais"),
        @NamedQuery(name = "Pais.findAll", query = "SELECT d FROM Pais d ORDER BY d.nombrePais")
})
public class Pais implements Serializable {

    private static final long serialVersionUID = 869199289584873346L;

    @Id
    @Basic(optional = false)
    @Column(name = "cd_pais")
    private Integer id;

    @Column(name = "nb_clave")
    private String clave;

    @Column(name = "nb_pais")
    private String nombrePais;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pais other = (Pais) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Pais [clavePais=" + clave + ", nombrePais=" + nombrePais + "]";
    }

    public Pais() {
    }

    public Pais(Integer id, String clave, String nombrePais) {
        super();
        this.id = id;
        this.clave = clave;
        this.nombrePais = nombrePais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

}
