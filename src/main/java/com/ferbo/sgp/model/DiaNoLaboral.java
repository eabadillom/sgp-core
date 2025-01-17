package com.ferbo.sgp.model;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_dia_no_laboral")
@NamedQueries({
	@NamedQuery(name = "DiaNoLaboral.buscaPorPeriodo", query = "SELECT d FROM DiaNoLaboral d WHERE d.pais.clave = :clavePais AND (d.fecha >= :fechaInicio AND d.fecha <= :fechaFin) ORDER BY d.fecha ASC")
})
public class DiaNoLaboral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional =  false)
	@Column(name = "id_fecha")
	private Integer id;
	
	@Column(name = "fh_fecha")
	private Date fecha;
	
	@Column(name = "nb_descripcion")
	private String descripcion;
	
	@JoinColumn(name = "cd_pais", referencedColumnName = "cd_pais")
	@ManyToOne
	private Pais pais;
	
	@Column(name = "st_oficial")
	@Basic(optional = true)
	private Boolean oficial;

    @Override
	public int hashCode() {
		return Objects.hash(descripcion, fecha, id, pais);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiaNoLaboral other = (DiaNoLaboral) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(id, other.id) && Objects.equals(pais, other.pais);
	}

	@Override
	public String toString() {
		return "DetDiaNoLaboral [id=" + id + ", fecha=" + fecha + ", descripcion=" + descripcion + "]";
	}

    public DiaNoLaboral() {
	}
	
	public DiaNoLaboral(Integer id, Date fecha, String descripcion, Pais pais, Boolean oficial) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.pais = pais;
        this.oficial = oficial;
	}

    
	
	public DiaNoLaboral(Integer id, Date fecha, String descripcion, Integer idPais, String clavePais, String nombrePais, Boolean oficial) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.pais = new Pais(idPais, clavePais, nombrePais);
        this.oficial = oficial;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Boolean getOficial() {
        return oficial;
    }

    public void setOficial(Boolean oficial) {
        this.oficial = oficial;
    }

    
    
}
