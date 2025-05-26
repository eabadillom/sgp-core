package com.ferbo.sgp.core.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.sgp.core.dao.AsistenciaDAO;
import com.ferbo.sgp.core.dao.EmpleadoDAO;
import com.ferbo.sgp.core.dao.EstatusAsistenciaDAO;
import com.ferbo.sgp.core.dao.ParametroDAO;
import com.ferbo.sgp.core.model.Asistencia;
import com.ferbo.sgp.core.model.DiaNoLaboral;
import com.ferbo.sgp.core.model.Empleado;
import com.ferbo.sgp.core.model.EstatusAsistencia;
import com.ferbo.sgp.core.model.Parametro;
import com.ferbo.sgp.core.model.Vacaciones;
import com.ferbo.sgp.core.util.SGPException;
import com.ferbo.sgp.tools.time.DateTool;


public class EmpleadoBL {

    private static final Logger log = LogManager.getLogger(EmpleadoBL.class);

    public static List<Empleado> obtenerEmpleados() throws SGPException {
        EmpleadoDAO empleadodao = new EmpleadoDAO();
        return empleadodao.obtenerTodos();
    }

    public static void generarAnioVacaciones(Empleado empleado) throws SGPException {

        if (empleado.getDatoEmpresarial().getFechaIngreso() == null) {
            throw new SGPException("La fecha de ingreso del empleado está vacía.");
        }

        if (empleado.getDatoEmpresarial().getFechaBaja() != null) {
            throw new SGPException("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + " ya no trabaja en la empresa");
        }

        int aniotmp = DateTool.getAnio(empleado.getDatoEmpresarial().getFechaIngreso());
        int mestmp = DateTool.getMes(empleado.getDatoEmpresarial().getFechaIngreso());
        int diatmp = DateTool.getDia(empleado.getDatoEmpresarial().getFechaIngreso());

        Date fechaaux = DateTool.getDate(aniotmp, mestmp, diatmp);

        if (!empleado.getVacacion().isEmpty()) {
            fechaaux = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getFechafin();
        }

        while (fechaaux.before(DateTool.now())) {
            Date fechainicio = null;
            Date fechafin = null;
            Date fechatmp = null;

            int dias = 0;

            if (empleado.getVacacion().isEmpty()) {
                fechainicio = fechaaux;
                fechafin = DateTool.addYear(fechaaux, 1);
                ParametroDAO parametroDAO = new ParametroDAO(Parametro.class);
                Parametro parametro = parametroDAO.buscarPorClave("DIVAC");
                String sDias = parametro.getValor();
                dias = Integer.parseInt(sDias);
            } else {
                dias = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getDiastotales();
                fechatmp = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getFechafin();
                fechainicio = DateTool.addDay(fechatmp, 1);
                fechafin = DateTool.addYear(fechainicio, 1);

                int tamanio = empleado.getVacacion().size() + 1;

                if (tamanio >= 2 && tamanio <= 5) {
                    dias = dias + 2;
                }

                if (tamanio >= 10 && tamanio % 5 == 0) {
                    dias = dias + 2;
                }
            }

            fechafin = DateTool.addDay(fechafin, -1);

            Vacaciones ultimasvacaciones = new Vacaciones();

            ultimasvacaciones.setFechainicio(fechainicio);
            ultimasvacaciones.setFechafin(fechafin);
            ultimasvacaciones.setDiastotales(dias);
            ultimasvacaciones.setEmpleado(empleado);
            ultimasvacaciones.setDiastomados(0);
            ultimasvacaciones.setDiasPagados(0);
            ultimasvacaciones.setPrimapagada(Boolean.FALSE);
            ultimasvacaciones.setDiaspendientespagados(Boolean.FALSE);

            if (empleado.getVacacion().isEmpty()) {
                List<Vacaciones> vacaciones = new ArrayList<Vacaciones>();
                vacaciones.add(ultimasvacaciones);
                empleado.setVacacion(vacaciones);
            } else {
                empleado.getVacacion().add(ultimasvacaciones);
            }
            fechaaux = fechafin;
        }
    }

    /**Devuelve los días de la semana que un empleado puede laborar.<br>
     * Por ejemplo, si un empleado trabaja de lunes a sábado, se devuelve
     * una lista con L, M. X, J, V, S, siendo los días Lunes, Martes,
     * Miercoles, Jueves, Viernes y Sábado respectivamente.
     * @param empleado
     * @return
     */
    public static List<String> diasLaboralesPorSemana(Empleado empleado) {
        List<String> diasLaboralesEmpleado = new ArrayList<String>();

        if (empleado.getDatoEmpresarial().getDiaLunes()) {
            diasLaboralesEmpleado.add("L");
        }

        if (empleado.getDatoEmpresarial().getDiaMartes()) {
            diasLaboralesEmpleado.add("M");
        }

        if (empleado.getDatoEmpresarial().getDiaMiercoles()) {
            diasLaboralesEmpleado.add("X");
        }

        if (empleado.getDatoEmpresarial().getDiaJueves()) {
            diasLaboralesEmpleado.add("J");
        }

        if (empleado.getDatoEmpresarial().getDiaViernes()) {
            diasLaboralesEmpleado.add("V");
        }

        if (empleado.getDatoEmpresarial().getDiaSabado()) {
            diasLaboralesEmpleado.add("S");
        }

        if (empleado.getDatoEmpresarial().getDiaDomingo()) {
            diasLaboralesEmpleado.add("D");
        }

        return diasLaboralesEmpleado;
    }

    public static void empleadoTieneDiasLaborales(Empleado empleado) throws SGPException {

        if (empleado == null) {
            throw new SGPException("Error: El empleado no tiene informacion");
        }

        if (empleado.getDatoEmpresarial() == null) {
            throw new SGPException("Error: El empleado no tiene informacion empresarial");
        }

        if (diasLaboralesPorSemana(empleado).isEmpty()) {
            System.out.println("Error: No tiene dias laborales asignados. Por favor contactar a RH");
        }

    }

    public static boolean empleadoAsisteEnDiaDescanso(Empleado empleado) {

        List<DiaNoLaboral> diasDescanso = DiaNoLaboralBL.diasDecansoAnioVigente("MX");
        List<String> diasLaboralesEmpleado = new ArrayList<String>();
        String diaLaborando = DateTool.getDiaSemana(DateTool.now());
        Date hoy = DateTool.now();
        DateTool.resetTime(hoy);

        diasLaboralesEmpleado = diasLaboralesPorSemana(empleado);

        for (DiaNoLaboral diaDescanso : diasDescanso) {
            if (diaDescanso.getFecha().compareTo(hoy) == 0 && diaDescanso.getOficial()) {
                return true;
            }
        }

        if (!diasLaboralesEmpleado.contains(diaLaborando)) {
            return true;
        }

        return false;
    }

    public static void generarAusencia(Empleado empleado) throws SGPException {
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        EstatusAsistenciaDAO estatusAsistenciaDAO = new EstatusAsistenciaDAO();

        Date hoy = DateTool.now();
        Date ayerInicio = DateTool.addDay(hoy, -1);
        DateTool.setTime(ayerInicio, 0, 0, 0, 0);

        Date ayerFin = DateTool.addDay(hoy, -1);
        DateTool.setTime(ayerFin, 23, 59, 59, 999);

        Optional<Asistencia> oAsistencia = null;
        Asistencia asistenciaAyer = null;
        Date diaAsistencia = null;
        EstatusAsistencia statusAusencia = null;
        String diaSemana = null;
        
        try {
        	if (empleado.getDatoEmpresarial() == null) {
				return;
			}

			
        	
        	if (  (empleado.getDatoEmpresarial().getFechaBaja() != null)
        		&& (empleado.getDatoEmpresarial().getFechaBaja().compareTo(new Date()) > 0) ) {
				return;
			}
        	
        	diaSemana = DateTool.getDiaSemana(ayerInicio);
        	
        	List<String> diasTrabajo = diasLaboralesPorSemana(empleado);
        	
        	if (diasTrabajo.isEmpty()) {
        		log.info("El empleado {} {} {} no tiene dias laborales asignados. Por favor contactar a RRHH.",
        				empleado.getNombre(), empleado.getPrimerApellido(), empleado.getSegundoApellido());
        		return;
        	}
        	
        	if ( diasTrabajo.contains(diaSemana) == false) {
        		log.info("El empleado {} {} {} tiene asignado su día de descanso: {}.",
        				empleado.getNombre(), empleado.getPrimerApellido(), empleado.getSegundoApellido(),
        				diaSemana);
        		return;
        	}
        	
        	oAsistencia = asistenciaDAO.buscarPorEmpleadoFechaEntrada(empleado.getId(), ayerInicio, ayerFin);
    		
    		if (oAsistencia.isPresent()) {
    			log.info("El empleado {} {} {} asistio el dia {} ",
    					empleado.getNombre(), empleado.getPrimerApellido(), empleado.getSegundoApellido(), ayerInicio);
    			return;
    		}
    		
    		log.info("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + " no tiene asistencia del dia " + ayerInicio);
			asistenciaAyer = new Asistencia();
			
			diaAsistencia = DateTool.now();
			diaAsistencia = DateTool.addDay(diaAsistencia, -1);
			
			DateTool.setTime(diaAsistencia, 0, 0, 0, 0);
			
			statusAusencia = estatusAsistenciaDAO.buscarPorCodigo("F");
			asistenciaAyer.setEmpleado(empleado);
			asistenciaAyer.setFechaEntrada(diaAsistencia);
			asistenciaAyer.setFechaSalida(diaAsistencia);
			asistenciaAyer.setEstatus(statusAusencia);
			
			asistenciaDAO.guardar(asistenciaAyer);
        	
        } catch(SGPException ex) {
        	log.error("Error al momento de guardar la asistencia..." + ex);
            throw new SGPException("Hubo algun problema al momento de guardar la inasistencia");
        }
    }
}
