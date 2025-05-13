package com.ferbo.sgp.dao;

import com.ferbo.sgp.model.Asistencia;
import com.ferbo.sgp.util.SGPException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AsistenciaDAO extends BaseDAO<Asistencia, Integer> {

    private static Logger log = LogManager.getLogger(AsistenciaDAO.class);

    public AsistenciaDAO(Class<Asistencia> modelClass) {
        super(modelClass);
    }

    public AsistenciaDAO() {
        super(Asistencia.class);
    }

    public Asistencia buscarPorEmpleadoFechaEntrada(Integer idEmpleado, Date fechaEntradaInicio, Date fechaEntradaFin) {
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
        } catch (Exception ex) {
            model = null;
            log.debug("Problema para obtener el listado de registros de asistencia del empleado por fecha de entrada...", ex);
        } finally {
            close(em);
        }

        return model;
    }

}
