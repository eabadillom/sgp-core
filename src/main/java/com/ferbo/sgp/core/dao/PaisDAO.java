package com.ferbo.sgp.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.core.model.Pais;

public class PaisDAO extends BaseDAO<Pais, Integer> {

    private static Logger log = LogManager.getLogger(PaisDAO.class);

    public PaisDAO(Class<Pais> modelClass) {
        super(modelClass);
    }
    
    public PaisDAO() {
        super(Pais.class);
    }
    
    public List<Pais> buscarTodos() {
        List<Pais> modelList = null;
        EntityManager em = null;
        
        try {
            em = this.getEntityManager();
            modelList = em.createNamedQuery("Pais.findAll", modelClass)
                .getResultList();
        } catch (Exception ex) {
            log.error("Problema para obtener el pais: {}", ex.getMessage());
        } finally {
            this.close(em);
        }
        
        return modelList;
    }

    public Pais buscarPorClave(String clavePais) {
        Pais model = null;
        EntityManager em = null;
        
        try {
            em = this.getEntityManager();
            model = em.createNamedQuery("Pais.findByClave", Pais.class)
                .setParameter("clavePais", clavePais)
                .getSingleResult();
        } catch (Exception ex) {
            log.error("Problema para obtener el pais: {}", ex.getMessage());
        } finally {
            this.close(em);
        }
        
        return model;
    }

}
