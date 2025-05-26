package com.ferbo.sgp.core.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.core.model.DiaNoLaboral;

public class DiaNoLaboralDAO extends BaseDAO<DiaNoLaboral, Integer> {
	
	private static Logger log = LogManager.getLogger(DiaNoLaboralDAO.class);

	public DiaNoLaboralDAO(Class<DiaNoLaboral> modelClass) {
		super(modelClass);
	}
	
	public DiaNoLaboralDAO() {
		super(DiaNoLaboral.class);
	}
	
	public List<DiaNoLaboral> buscarPorPeriodo(String clavePais, Date fechaInicio, Date fechaFin) {
		List<DiaNoLaboral> list = null;
		EntityManager em = null;
		
		try {
			em = this.getEntityManager();
			list = em.createNamedQuery("DiaNoLaboral.buscaPorPeriodo", DiaNoLaboral.class)
					.setParameter("clavePais", clavePais)
					.setParameter("fechaInicio", fechaInicio)
					.setParameter("fechaFin", fechaFin)
					.getResultList()
					;
			
			for(DiaNoLaboral d : list) {
				log.trace("Pais: {}, Dia No Laboral: {}", d.getPais().getId(), d.getDescripcion());
			}
			
		} catch(Exception ex) {
			log.error("Problema para obtener la lista de dias no laborales del periodo " + fechaInicio +" al " + fechaFin, ex);
		} finally {
			this.close(em);
		}
		
		return list;
	}
    
}
