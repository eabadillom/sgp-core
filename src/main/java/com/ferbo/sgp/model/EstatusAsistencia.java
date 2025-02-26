package com.ferbo.sgp.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estatus_registro")
@NamedQueries({
    @NamedQuery(name = "EstatusAsistencia.findAll", query = "SELECT ea FROM EstatusAsistencia ea"),
    @NamedQuery(name = "EstatusAsistencia.findByCodigo", query = "SELECT ea FROM EstatusAsistencia ea WHERE ea.codigo = :codigo")
})
public class EstatusAsistencia implements Serializable {

    public static final String ST_REG_EN_TIEMPO = "T";
    public static final String ST_REG_RETARDO = "R";
    public static final String ST_REG_FALTA = "F";
    public static final String ST_REG_JUSTIFICADO = "J";
    public static final String ST_REG_VACACIONES = "V";
    public static final String ST_REG_ASISTENCIA_DIA_NO_LABORAL = "X";
    public static final String ST_REG_PERMISO = "P";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estatus")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    
    @Basic(optional = false)
    @Column(name = "activo")
    private short activo;

    public EstatusAsistencia() {
    }

    public EstatusAsistencia(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final EstatusAsistencia other = (EstatusAsistencia) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "EstatusAsistencia{" + "id=" + id + ", descripcion=" + descripcion + ", codigo=" + codigo + ", activo=" + activo + '}';
    }
 
}
