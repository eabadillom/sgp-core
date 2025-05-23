package com.ferbo.sgp.core.dao;

import com.ferbo.sgp.core.model.EstatusAsistencia;
import com.ferbo.sgp.core.util.SGPException;

import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EstatusAsistenciaDAO extends BaseDAO<EstatusAsistencia, Integer> {

    private static Logger log = LogManager.getLogger(EstatusAsistenciaDAO.class);

    public EstatusAsistenciaDAO(Class<EstatusAsistencia> modelClass) {
        super(modelClass);
    }

    public EstatusAsistenciaDAO() {
        super(EstatusAsistencia.class);
    }

    public EstatusAsistencia buscarPorCodigo(String codigo) throws SGPException {
        EstatusAsistencia model = null;
        EntityManager em = null;

        try {
            em = this.getEntityManager();
            model = em.createNamedQuery("EstatusAsistencia.findByCodigo", EstatusAsistencia.class)
                    .setParameter("codigo", codigo)
                    .getSingleResult();
        } catch (Exception ex) {
            log.error("Problema para obtener el estatus de registro", ex);
            throw new SGPException("No se encontro el codigo de estatus introducido");
        } finally {
            this.close(em);
        }

        return model;
    }
}
