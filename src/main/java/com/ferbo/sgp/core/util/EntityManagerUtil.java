package com.ferbo.sgp.core.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

public class EntityManagerUtil {
	private static Logger log = LogManager.getLogger(EntityManagerUtil.class);

	protected static String PERSIST_UNIT = "sgpCR";
    protected static EntityManagerFactory emf = null;

    public static EntityManager getEntityManager() {
    	if(emf == null)
    		emf = Persistence.createEntityManagerFactory(PERSIST_UNIT);
        return emf.createEntityManager();
    }
    public static Connection getConnection() {
    	
    	Connection connection;
    	Session session = EntityManagerUtil.getEntityManager().unwrap(Session.class);
    	SessionImpl sessionImpl = (SessionImpl) session;
    	connection = sessionImpl.connection();
    	return connection;
    }
	public static void close(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException ex) {
			log.error("Problema al cerrar el objeto Connection.", ex);
		} catch (Exception ex) {
			log.error("Problema general al cerrar el objeto Connection.", ex);
		} finally {
			connection = null;
		}
	}
	
	public static void close(EntityManager em) {
		if(em == null)
			return;
		
		if(em.isOpen())
			em.close();
		em = null;
		return;
	}
	
	public static void rollback(EntityManager em) {
		if(em == null)
			return;
		
		if(em.isOpen() == false)
			return;
		
		em.getTransaction().rollback();
	}
}
