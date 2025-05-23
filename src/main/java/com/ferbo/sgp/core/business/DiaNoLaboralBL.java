package com.ferbo.sgp.core.business;

import com.ferbo.sgp.core.dao.DiaNoLaboralDAO;
import com.ferbo.sgp.core.model.DiaNoLaboral;
import com.ferbo.sgp.core.util.DateUtil;

import java.util.Date;
import java.util.List;

public class DiaNoLaboralBL {

    public static List<DiaNoLaboral> diasDecansoAnioVigente(String pais) {

        DiaNoLaboralDAO diaNoLaboralDAO = new DiaNoLaboralDAO();

        Date fechaInicio = DateUtil.now();
        DateUtil.setDia(fechaInicio, 1);
        DateUtil.setMes(fechaInicio, 1);
        Date fechaFin = DateUtil.addYear(fechaInicio, 1);

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
