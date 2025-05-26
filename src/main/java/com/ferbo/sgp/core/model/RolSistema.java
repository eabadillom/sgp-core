package com.ferbo.sgp.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "det_rol_sistema")
public class RolSistema  implements Serializable{

	private static final long serialVersionUID = -9128969530916264997L;
	
	@Id
	@Column(name = "nb_rol")
	private String clave;
	
	@Column(name = "nb_descripcion")
	private String descripcion;
	
	@Override
	public int hashCode() {
		return Objects.hash(clave);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolSistema other = (RolSistema) obj;
		return Objects.equals(clave, other.clave);
	}

	@Override
	public String toString() {
		return "RolSistema [clave=" + clave + ", descripcion=" + descripcion + "]";
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
