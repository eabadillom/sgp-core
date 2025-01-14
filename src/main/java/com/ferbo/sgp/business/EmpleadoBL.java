
package com.ferbo.sgp.business;

import com.ferbo.sgp.dao.EmpleadoDAO;
import com.ferbo.sgp.model.Empleado;
import com.ferbo.sgp.model.Vacaciones;
import com.ferbo.sgp.util.DateUtil;
import com.ferbo.sgp.util.SGPException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class EmpleadoBL {
    
    public static List<Empleado> obtenerEmpleados() throws SGPException{
        EmpleadoDAO empleadodao = new EmpleadoDAO();
        return empleadodao.obtenerTodos();
    }
    
    public static void generarAnioVacaciones(Empleado empleado) throws SGPException{
    
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

        while (fechaaux.before(DateUtil.now())) {
            Date fechainicio = null;
            Date fechafin = null;
            Date fechatmp = null;

            int dias = 0;

            if (empleado.getVacacion().isEmpty()) {

                fechainicio = fechaaux;
                fechafin = DateUtil.addYear(fechaaux, 1);

                dias = 12;
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
}
