package com.ferbo.sgp.core.business;

import java.util.Date;
import java.util.List;

import com.ferbo.sgp.core.dao.DiaNoLaboralDAO;
import com.ferbo.sgp.core.model.DiaNoLaboral;
import com.ferbo.sgp.tools.time.DateTool;

public class DiaNoLaboralBL {

    public static List<DiaNoLaboral> diasDecansoAnioVigente(String pais) {

        DiaNoLaboralDAO diaNoLaboralDAO = new DiaNoLaboralDAO();

        Date fechaInicio = DateTool.now();
        DateTool.setDia(fechaInicio, 1);
        DateTool.setMes(fechaInicio, 1);
        Date fechaFin = DateTool.addYear(fechaInicio, 1);

        List<DiaNoLaboral> diasNoLaborables = diaNoLaboralDAO.buscarPorPeriodo(pais, fechaInicio, fechaFin);

        return diasNoLaborables;

    }

    public static Boolean esDiaFestivo(Date fecha, String pais) {

        Boolean respuesta = false;
        
        List<DiaNoLaboral> diasNoLaborables = diasDecansoAnioVigente(pais);
        
         for(DiaNoLaboral diaNoLaboral : diasNoLaborables){
             if(diaNoLaboral.getFecha().compareTo(fecha) == 0 && diaNoLaboral.getOficial()){
                 respuesta = true;
                 break;
             }
         }
         return respuesta;
    }

}
