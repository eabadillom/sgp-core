package com.ferbo.sgp.business;

import com.ferbo.sgp.dao.AsistenciaDAO;
import com.ferbo.sgp.dao.EmpleadoDAO;
import com.ferbo.sgp.dao.EstatusAsistenciaDAO;
import com.ferbo.sgp.dao.ParametroDAO;
import com.ferbo.sgp.model.Asistencia;
import com.ferbo.sgp.model.DiaNoLaboral;
import com.ferbo.sgp.model.Empleado;
import com.ferbo.sgp.model.Parametro;
import com.ferbo.sgp.model.Vacaciones;
import com.ferbo.sgp.util.DateUtil;
import com.ferbo.sgp.util.SGPException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        int aniotmp = DateUtil.getAnio(empleado.getDatoEmpresarial().getFechaIngreso());
        int mestmp = DateUtil.getMes(empleado.getDatoEmpresarial().getFechaIngreso());
        int diatmp = DateUtil.getDia(empleado.getDatoEmpresarial().getFechaIngreso());

        Date fechaaux = DateUtil.getDate(aniotmp, mestmp, diatmp);

        if (!empleado.getVacacion().isEmpty()) {
            fechaaux = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getFechafin();
        }

        while (fechaaux.before(DateUtil.now())) {
            Date fechainicio = null;
            Date fechafin = null;
            Date fechatmp = null;

            int dias = 0;

            if (empleado.getVacacion().isEmpty()) {
                fechainicio = fechaaux;
                fechafin = DateUtil.addYear(fechaaux, 1);
                ParametroDAO parametroDAO = new ParametroDAO(Parametro.class);
                Parametro parametro = parametroDAO.buscarPorClave("DIVAC");
                String sDias = parametro.getValor();
                dias = Integer.parseInt(sDias);
            } else {
                dias = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getDiastotales();
                fechatmp = empleado.getVacacion().get(empleado.getVacacion().size() - 1).getFechafin();
                fechainicio = DateUtil.addDay(fechatmp, 1);
                fechafin = DateUtil.addYear(fechainicio, 1);

                int tamanio = empleado.getVacacion().size() + 1;

                if (tamanio >= 2 && tamanio <= 5) {
                    dias = dias + 2;
                }

                if (tamanio >= 10 && tamanio % 5 == 0) {
                    dias = dias + 2;
                }
            }

            fechafin = DateUtil.addDay(fechafin, -1);

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

    public static List<String> diasEmpleadoTrabaja(Empleado empleado) {
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

        if (diasEmpleadoTrabaja(empleado).isEmpty()) {
            System.out.println("Error: No tiene dias laborales asignados. Por favor contactar a RH");
        }

    }

    public static boolean empleadoAsisteEnDiaDescanso(Empleado empleado) {

        List<DiaNoLaboral> diasDescanso = DiaNoLaboralBL.diasDecansoAnioVigente("MX");
        List<String> diasLaboralesEmpleado = new ArrayList<String>();
        String diaLaborando = DateUtil.getDiaSemana(DateUtil.now());
        Date hoy = DateUtil.now();
        DateUtil.resetTime(hoy);

        diasLaboralesEmpleado = diasEmpleadoTrabaja(empleado);

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

    public static void generarInasistencia(Empleado empleado) throws SGPException {
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        EstatusAsistenciaDAO estatusAsistenciaDAO = new EstatusAsistenciaDAO();

        Date hoy = DateUtil.now();
        Date ayerInicio = DateUtil.addDay(hoy, -1);
        DateUtil.setTime(ayerInicio, 0, 0, 0, 0);

        Date ayerFin = DateUtil.addDay(hoy, -1);
        DateUtil.setTime(ayerFin, 23, 59, 59, 999);

        Asistencia asistenciaAyer = null;

        String nombreDia = DateUtil.getDiaSemana(ayerInicio);

        List<String> diasTrabajo = diasEmpleadoTrabaja(empleado);

        if (diasTrabajo.isEmpty()) {
            log.info("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + ", no tiene dias laborales asignados. Por favor contactar a RH");
        } else {
            if (!diasTrabajo.contains(nombreDia)) {
                log.info("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + " por contrato no trabajo ayer.");
            } else {

                asistenciaAyer = asistenciaDAO.buscarPorEmpleadoFechaEntrada(empleado.getId(), ayerInicio, ayerFin);

                if (asistenciaAyer == null) {
                    log.info("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + " no tiene asistencia del dia " + ayerInicio);
                    asistenciaAyer = new Asistencia();
                    Date diaAsistencia = DateUtil.now();
                    diaAsistencia = DateUtil.addDay(diaAsistencia, -1);
                    DateUtil.setTime(diaAsistencia, 0, 0, 0, 0);
                    asistenciaAyer.setEmpleado(empleado);
                    asistenciaAyer.setFechaEntrada(diaAsistencia);
                    asistenciaAyer.setFechaSalida(diaAsistencia);
                    try {
                        asistenciaAyer.setEstatus(estatusAsistenciaDAO.buscarPorCodigo("F"));
                        asistenciaDAO.guardar(asistenciaAyer);
                    } catch (Exception ex) {
                        log.error("Error al momento de guardar la asistencia..." + ex);
                        throw new SGPException("Hubo algun problema al momento de guardar la inasistencia");
                    }
                    asistenciaAyer = null;
                } else {
                    log.info("El empleado " + empleado.getNombre() + " " + empleado.getPrimerApellido() + " " + empleado.getSegundoApellido() + " asistio el dia " + ayerInicio);
                }
            }
        }
    }
}
