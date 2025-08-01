package com.ferbo.sgp.core.business.notifmovil;

import com.ferbo.sgp.core.dto.NotificacionMovilDTO;
import com.ferbo.sgp.core.model.Empleado;

/**
 *
 * @author alberto
 */
public class NotifMovilBL 
{
    
    public static NotificacionMovilDTO obtenerMensaje(int ausencias) 
    {
        String titulo ="Notificación de ausencias pendientes";
        String contenido = "Se presentaron " + ausencias + " ausencias el día de ayer";

        NotificacionMovilDTO notificacionMovilDTO = new NotificacionMovilDTO(titulo, contenido);

        return notificacionMovilDTO;
    }
    
}
