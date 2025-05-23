package com.ferbo.sgp.core.dao;

import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.core.model.Asistencia;

public class AsistenciaDAO extends BaseDAO<Asistencia, Integer> {

	private static Logger log = LogManager.getLogger(AsistenciaDAO.class);

	public AsistenciaDAO(Class<Asistencia> modelClass) {
		super(modelClass);
	}

	public AsistenciaDAO() {
		super(Asistencia.class);
	}

	public Optional<Asistencia> buscarPorEmpleadoFechaEntrada(Integer idEmpleado, Date fechaEntradaInicio,
			Date fechaEntradaFin) {
		Asistencia model = null;
		EntityManager em = null;
		
		try {
			em = this.getEntityManager();
			model = em.createNamedQuery("Asistencia.findByIdEmpleadoAndFecha", Asistencia.class)
					.setParameter("idEmpleado", idEmpleado)
					.setParameter("fechaEntradaInicio", fechaEntradaInicio)
					.setParameter("fechaEntradaFin", fechaEntradaFin)
					.getSingleResult();
			log.debug("IdEmpleado: {}", model.getEmpleado().getId());
			log.debug("Estatus: {}", model.getEstatus().getId());
		} catch (NoResultException ex) {
			log.warn("No se encontró registro de asistencia para idEmpleado {}", idEmpleado);
		} catch (Exception ex) {
			model = null;
			log.error("Problema para obtener el listado de registros de asistencia del empleado por fecha de entrada...",ex);
		} finally {
			close(em);
		}

		return Optional.ofNullable(model);
	}

}
