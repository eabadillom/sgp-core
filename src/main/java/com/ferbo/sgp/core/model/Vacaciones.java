
package com.ferbo.sgp.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "det_vacaciones")
public class Vacaciones implements Serializable {
    
    private static final long serialVersionUID = -1814121746856810590L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_vacaciones")
    private Integer idVacaciones;
    
    @Column(name = "fh_inicio")
    private Date fechainicio;
    
    @Column(name = "fh_fin")
    private Date fechafin;
    
    @Column(name = "nu_dias_totales")
    private Integer diastotales;
    
    @Column(name = "nu_dias_tomados")
    private Integer diastomados;
    
    @Column(name = "st_prima_pagada")
    private Boolean primapagada;
    
    @Column(name = "st_dias_pend_pagados")
    private Boolean diaspendientespagados;
    
    @Column(name = "nu_dias_pagados")
    private Integer diasPagados;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    private Empleado empleado;

    public Vacaciones() {
    }

    public Vacaciones(Integer idVacaciones) {
        this.idVacaciones = idVacaciones;
    }

    public Vacaciones(Integer idVacaciones, Date fechainicio, Date fechafin, Integer diastotales, Integer diastomados, Boolean primapagada, Boolean diaspendientespagados) {
        this.idVacaciones = idVacaciones;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.diastotales = diastotales;
        this.diastomados = diastomados;
        this.primapagada = primapagada;
        this.diaspendientespagados = diaspendientespagados;
    }

    public Integer getIdVacaciones() {
        return idVacaciones;
    }

    public void setIdVacaciones(Integer idVacaciones) {
        this.idVacaciones = idVacaciones;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Integer getDiastotales() {
        return diastotales;
    }

    public void setDiastotales(Integer diastotales) {
        this.diastotales = diastotales;
    }

    public Integer getDiastomados() {
        return diastomados;
    }

    public void setDiastomados(Integer diastomados) {
        this.diastomados = diastomados;
    }

    public Boolean getPrimapagada() {
        return primapagada;
    }

    public void setPrimapagada(Boolean primapagada) {
        this.primapagada = primapagada;
    }

    public Boolean getDiaspendientespagados() {
        return diaspendientespagados;
    }

    public void setDiaspendientespagados(Boolean diaspendientespagados) {
        this.diaspendientespagados = diaspendientespagados;
    }

    public Integer getDiasPagados() {
        return diasPagados;
    }

    public void setDiasPagados(Integer diasPagados) {
        this.diasPagados = diasPagados;
    } 

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    @Override
    public String toString() {
        return "Vacaciones{" + "idVacaciones=" + idVacaciones + ", fechainicio=" + fechainicio + ", fechafin=" + fechafin + ", diastotales=" + diastotales + ", diastomados=" + diastomados + ", primapagada=" + primapagada + ", diaspendientespagados=" + diaspendientespagados + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.idVacaciones);
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
        final Vacaciones other = (Vacaciones) obj;
        return Objects.equals(this.idVacaciones, other.idVacaciones);
    }
    
    
}
