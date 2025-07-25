package com.ferbo.sgp.core.business.notifmovil;

import com.ferbo.sgp.core.dto.NotificacionMovilDTO;
import com.ferbo.sgp.core.model.Empleado;

/**
 *
 * @author alberto
 */
public class NotifMovilBL 
{
    
    public static NotificacionMovilDTO obtenerMensaje(String operacion, Empleado empleado) 
    {
        String incidencia;
        String descripcion;

        switch (operacion) {

            case "retardo":
            case "ausencia":
                incidencia = "Notificiacion";
                descripcion = " tiene ";
                break;

            default:
                incidencia = "Solicitud";
                descripcion = " ha solicitado ";
                break;
        }

        String titulo = String.format("%s de %s pendiente", incidencia, operacion);
        String contenido = String.format(
                "El empleado %s %s %s%s",
                empleado.getNombre(),
                empleado.getPrimerApellido(),
                descripcion,
                operacion
        );

        NotificacionMovilDTO notificacionMovilDTO = new NotificacionMovilDTO(titulo, contenido);

        return notificacionMovilDTO;
    }
    
}
