package com.ferbo.sgp.dao;

import com.ferbo.sgp.util.EntityManagerUtil;
import com.ferbo.sgp.util.SGPException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract class BaseDAO<MODEL, PK> {
	
	private static Logger log = LogManager.getLogger(BaseDAO.class);
	
	protected Class<MODEL> modelClass;
	protected static EntityManagerFactory emf = null;
	
	public BaseDAO(Class<MODEL> modelClass) {
		this.modelClass = modelClass;
	}
	
	public EntityManager getEntityManager() {
		EntityManager em = null;
		try {
			em = EntityManagerUtil.getEntityManager();
		} catch(Exception ex) {
			log.error("Problema para obtener el entity manager...", ex);
		}
		
		return em;
	}
	
	public MODEL buscarPorId(PK id) {
		MODEL model = null;
		
		EntityManager em = null;
		
		try {
			em = EntityManagerUtil.getEntityManager();
			model = em.find(modelClass, id);
		} catch(Exception ex) {
			log.warn("Problema para obtener el elemento por ID: {}", id);
		} finally {
			EntityManagerUtil.close(em);
		}
		
		return model;
	}
	
	
	public synchronized void guardar(MODEL model) throws SGPException {
		EntityManager em = null;
		try {
			log.info("Guardando objeto: {}", model);
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(model);
			em.getTransaction().commit();
			//log.info("Objeto guardado correctamente: {}", model);
		} catch(Exception ex) {
			rollback(em);
			log.error("Problema para guardar el objeto: " + model, ex);
                        throw new SGPException("Error al guardar en la base de datos.");
		} finally {
			close(em);
		}
		
	}
	public synchronized void actualizar(MODEL model)throws SGPException {
		EntityManager em = null;
		try {
			log.info("Actualizando objeto: {}", model);
			em = getEntityManager();
			em.getTransaction().begin();
			model = em.merge(model);
			em.getTransaction().commit();
			log.info("Objeto actualizado correctamente: {}", model);
		} catch(Exception ex) {
			rollback(em);
			log.error("Problema para actualizar el objeto: " + model, ex);
                        throw new SGPException("Error al actualizar en la base de datos.");
		} finally {
			close(em);
		}
	}
	public synchronized void eliminar(MODEL model) throws SGPException {
		EntityManager em = null;
		try {
			log.info("Eliminando objeto: {}", model);
			em = getEntityManager();
			em.getTransaction().begin();
			em.remove(em.contains(model) ? model : em.merge(model));
			em.getTransaction().commit();
			log.info("Objeto eliminado correctamente: {}", model);
		} catch(Exception ex) {
			this.rollback(em);
			log.error("Probleam para eliminar el objeto: " + model, ex);
                        throw new SGPException("Error al eliminar en la base de datos.");
		} finally {
			this.close(em);
		}
	}
	
	public synchronized void close(EntityManager em) {
		if(em == null)
			return;
		
		if(em.isOpen())
			em.close();
		em = null;
		return;
	}
	
	public synchronized void rollback(EntityManager em) {
		if(em == null)
			return;
		
		if(em.isOpen() == false)
			return;
		
		em.getTransaction().rollback();
	}

}
