
package com.ferbo.sgp.dao;

import com.ferbo.sgp.model.Empleado;
import com.ferbo.sgp.util.SGPException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmpleadoDAO extends BaseDAO{
    
    private static Logger log = LogManager.getLogger(EmpleadoDAO.class);
    
    public EmpleadoDAO(Class<Empleado> modelClass) {
        super(modelClass);
    }

    public EmpleadoDAO() {
        super(Empleado.class);
    }
    
    public synchronized List<Empleado> obtenerTodos() throws SGPException{
        
        List<Empleado> empleados = null;
        EntityManager em = null;
        
        try{
            log.info("Inicia el proceso de obtener todos los empleados.");
            em = super.getEntityManager();
            TypedQuery<Empleado> resultado = em.createQuery("select e from Empleado e", Empleado.class);
            empleados = resultado.getResultList();
            
            for(Empleado empleadoaux : empleados){
                log.info("Datos empresariales del empleado: " + empleadoaux.getDatoEmpresarial());
                log.info("Vacaciones del empleado: " + empleadoaux.getVacacion());
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
        
        return empleados;
    }
    
}
