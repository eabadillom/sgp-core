package com.ferbo.sgp.business;

import com.ferbo.sgp.dao.DiaNoLaboralDAO;
import com.ferbo.sgp.model.DiaNoLaboral;
import com.ferbo.sgp.util.DateUtil;
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

    public static Boolean esFestivoEsteDia(Date dia, String pais) {

        Boolean respuesta = false;
        
        List<DiaNoLaboral> diasNoLaborables = diasDecansoAnioVigente(pais);
        
         for(DiaNoLaboral diaNoLaboral : diasNoLaborables){
             if(diaNoLaboral.getFecha().compareTo(dia) == 0 && diaNoLaboral.getOficial()){
                 respuesta = true;
                 break;
             }
         }
         return respuesta;
    }

}
