
package com.ferbo.sgp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inf_empleado_empresa")
public class DatoEmpresarial implements Serializable{
    
    private static final long serialVersionUID = -7428030225407170556L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_empresa")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "fh_ingreso")
    private Date fechaIngreso;

    @Basic(optional = true)
    @Column(name = "fh_baja")
    private Date fechaBaja;

    @Basic(optional = true)
    @Column(name = "nu_nss")
    private String nss;

    @Basic(optional = true)
    @Column(name = "nb_rfc")
    private String rfc;

    @Basic(optional = true)
    @Column(name = "nu_salario_diario")
    private BigDecimal salarioDiario;

    @Basic(optional = true)
    @Column(name = "tm_entrada")
    private Date horaEntrada;

    @Basic(optional = true)
    @Column(name = "tm_salida")
    private Date horaSalida;

    @Basic(optional = true)
    @Column(name = "nu_tolerancia")
    private Integer minutosTolerancia;
    
    @Basic(optional = true)
    @Column(name = "st_sindicalizado")
    private Boolean sindicalizado;

    @Basic(optional = true)
    @Column(name = "st_confianza")
    private Boolean confianza;

    @Basic(optional = true)
    @Column(name = "st_diat_lunes")
    private Boolean diaLunes;

    @Basic(optional = true)
    @Column(name = "st_diat_martes")
    private Boolean diaMartes;

    @Basic(optional = true)
    @Column(name = "st_diat_miercoles")
    private Boolean diaMiercoles;

    @Basic(optional = true)
    @Column(name = "st_diat_jueves")
    private Boolean diaJueves;

    @Basic(optional = true)
    @Column(name = "st_diat_viernes")
    private Boolean diaViernes;

    @Basic(optional = true)
    @Column(name = "st_diat_sabado")
    private Boolean diaSabado;

    @Basic(optional = true)
    @Column(name = "st_diat_domingo")
    private Boolean diaDomingo;

    @Basic(optional = true)
    @Column(name = "nb_baja")
    private String motivobaja;
    
    @ManyToOne
    @JoinColumn(name = "id_planta", referencedColumnName = "id_planta")
    private Planta planta;

    public DatoEmpresarial() {
    }

    public DatoEmpresarial(Integer id) {
        this.id = id;
    }

    public DatoEmpresarial(Date fechaIngreso, Date fechaBaja, String nss, String rfc, BigDecimal salarioDiario, Date horaEntrada, Date horaSalida, Integer minutosTolerancia, Boolean sindicalizado, Boolean confianza, Boolean diaLunes, Boolean diaMartes, Boolean diaMiercoles, Boolean diaJueves, Boolean diaViernes, Boolean diaSabado, Boolean diaDomingo, String motivobaja) {
        this.fechaIngreso = fechaIngreso;
        this.fechaBaja = fechaBaja;
        this.nss = nss;
        this.rfc = rfc;
        this.salarioDiario = salarioDiario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.minutosTolerancia = minutosTolerancia;
        this.sindicalizado = sindicalizado;
        this.confianza = confianza;
        this.diaLunes = diaLunes;
        this.diaMartes = diaMartes;
        this.diaMiercoles = diaMiercoles;
        this.diaJueves = diaJueves;
        this.diaViernes = diaViernes;
        this.diaSabado = diaSabado;
        this.diaDomingo = diaDomingo;
        this.motivobaja = motivobaja;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public BigDecimal getSalarioDiario() {
        return salarioDiario;
    }

    public void setSalarioDiario(BigDecimal salarioDiario) {
        this.salarioDiario = salarioDiario;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Integer getMinutosTolerancia() {
        return minutosTolerancia;
    }

    public void setMinutosTolerancia(Integer minutosTolerancia) {
        this.minutosTolerancia = minutosTolerancia;
    }

    public Boolean getSindicalizado() {
        return sindicalizado;
    }

    public void setSindicalizado(Boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public Boolean getConfianza() {
        return confianza;
    }

    public void setConfianza(Boolean confianza) {
        this.confianza = confianza;
    }

    public Boolean getDiaLunes() {
        return diaLunes;
    }

    public void setDiaLunes(Boolean diaLunes) {
        this.diaLunes = diaLunes;
    }

    public Boolean getDiaMartes() {
        return diaMartes;
    }

    public void setDiaMartes(Boolean diaMartes) {
        this.diaMartes = diaMartes;
    }

    public Boolean getDiaMiercoles() {
        return diaMiercoles;
    }

    public void setDiaMiercoles(Boolean diaMiercoles) {
        this.diaMiercoles = diaMiercoles;
    }

    public Boolean getDiaJueves() {
        return diaJueves;
    }

    public void setDiaJueves(Boolean diaJueves) {
        this.diaJueves = diaJueves;
    }

    public Boolean getDiaViernes() {
        return diaViernes;
    }

    public void setDiaViernes(Boolean diaViernes) {
        this.diaViernes = diaViernes;
    }

    public Boolean getDiaSabado() {
        return diaSabado;
    }

    public void setDiaSabado(Boolean diaSabado) {
        this.diaSabado = diaSabado;
    }

    public Boolean getDiaDomingo() {
        return diaDomingo;
    }

    public void setDiaDomingo(Boolean diaDomingo) {
        this.diaDomingo = diaDomingo;
    }

    public String getMotivobaja() {
        return motivobaja;
    }

    public void setMotivobaja(String motivobaja) {
        this.motivobaja = motivobaja;
    }

    public Planta getPlanta() {
        return planta;
    }

    public void setPlanta(Planta planta) {
        this.planta = planta;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final DatoEmpresarial other = (DatoEmpresarial) obj;
        return Objects.equals(this.id, other.id);
    }
    
    
}
