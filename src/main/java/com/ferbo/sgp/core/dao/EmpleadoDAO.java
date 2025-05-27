
package com.ferbo.sgp.core.dao;

import com.ferbo.sgp.core.model.Empleado;
import com.ferbo.sgp.tools.exceptions.SGPException;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmpleadoDAO extends BaseDAO<Empleado, Integer>{
    
    private static Logger log = LogManager.getLogger(EmpleadoDAO.class);
    
    public EmpleadoDAO(Class<Empleado> modelClass) {
        super(modelClass);
    }

    public EmpleadoDAO() {
        super(Empleado.class);
    }
    
    public synchronized List<Empleado> obtenerTodos() throws SGPException{
        List<Empleado> modelList = null;
        EntityManager em = null;
        
        try{
            log.info("Inicia el proceso de obtener todos los empleados.");
            em = super.getEntityManager();
            TypedQuery<Empleado> resultado = em.createQuery("select e from Empleado e", Empleado.class);
            modelList = resultado.getResultList();
            
            for(Empleado empleadoaux : modelList){
                log.debug("Datos empresariales del empleado: " + empleadoaux.getDatoEmpresarial());
                log.debug("Vacaciones del empleado: " + empleadoaux.getVacacion());
            }
            
            log.info("Finaliza el proceso de obntener todos los empleados");
        }
        catch(Exception ex){
            log.error("Sucedio un error al momento de obtemer todos los empleados: " + ex.getMessage());
            super.rollback(em);
            throw new SGPException("Hubo algun problema al obtener todos los empleados");  
        }
        finally{
            super.close(em);
        }
        
        return modelList;
    }

	public List<Empleado> buscarActivos(Date fecha) {
		List<Empleado> modelList = null;
		EntityManager em = null;
		
		try {
			em = this.getEntityManager();
			modelList = em.createNamedQuery("Empleado.buscarActivos", this.modelClass)
					.setParameter("fecha", fecha)
					.getResultList()
					;
		} catch(Exception ex) {
			log.error("Problema para obtener la lista de empleados activos...", ex);
		} finally {
			this.close(em);
		}
		
		return modelList;
	}
    
}
