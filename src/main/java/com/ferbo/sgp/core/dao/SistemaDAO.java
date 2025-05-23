package com.ferbo.sgp.core.dao;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.core.model.Sistema;

public class SistemaDAO extends BaseDAO<Sistema, Integer> {
	
	private static Logger log = LogManager.getLogger(SistemaDAO.class);

	public SistemaDAO(Class<Sistema> modelClass) {
		super(modelClass);
	}
	
	public SistemaDAO() {
		super(Sistema.class);
	}
	
	public Optional<Sistema> buscarPorNombre(String nombreSistema) {
		Sistema model = null;
		EntityManager em = null;
		
		try {
			em = this.getEntityManager();
			model = em.createNamedQuery("Sistema.findByNombre", this.modelClass)
					.setParameter("nombreSistema", nombreSistema)
					.getSingleResult()
					;
			
		} catch(Exception ex) {
			log.error("Problema para obtener la información de {}", nombreSistema, ex);
		} finally {
			this.close(em);
		}
		
		return Optional.ofNullable(model);
	}
}
