package com.ferbo.sgp.dao;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.model.Parametro;

public class ParametroDAO extends BaseDAO<Parametro, Integer> {
	
	private static Logger log = LogManager.getLogger(ParametroDAO.class);

	public ParametroDAO(Class<Parametro> modelClass) {
		super(modelClass);
	}
	
	public Parametro buscarPorClave(String clave) {
		Parametro model = null;
		EntityManager em = null;
		try {
			em = this.getEntityManager();
			model = em.createNamedQuery("Parametro.buscarPorClave", modelClass)
					.setParameter("clave", clave)
					.getSingleResult()
					;
		} catch(Exception ex) {
			log.warn("Problema para obtener el parametro: {}", clave);
		} finally {
			close(em);
		}
		return model;
	}

}
