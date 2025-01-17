package com.ferbo.sgp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.model.Pais;
import com.ferbo.sgp.util.SGPException;

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

    public synchronized Pais obtenrPorId(Integer id) throws SGPException{
        Pais resultado = null;
        EntityManager em = null;
        
        try{ 
            log.info("Comienza el proceso de obtener el registro por id");
            em = super.getEntityManager();
            em.getTransaction().begin();
            resultado = em.find(Pais.class, id);
            em.getTransaction().commit();
            log.info("Finaliza el proceso de obtener el registro por id");
        }
        catch (Exception ex){
            log.error("Hubo algun problema al obtener el registro por id. " + ex.getMessage());
            super.rollback(em);
            throw new SGPException("Hubo algun problema al obtener el registro");
        }
        finally{
            super.close(em);
        }
        
        return resultado;
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
