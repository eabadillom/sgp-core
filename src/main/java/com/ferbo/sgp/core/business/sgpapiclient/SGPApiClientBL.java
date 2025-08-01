package com.ferbo.sgp.core.business.sgpapiclient;

import com.ferbo.sgp.core.client.SGPApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ferbo.sgp.core.dto.NotificacionMovilDTO;
import com.ferbo.sgp.tools.exceptions.SGPException;

/**
 *
 * @author alberto
 */
public class SGPApiClientBL extends SGPApiClient
{
    private static Logger log = LogManager.getLogger(SGPApiClientBL.class);
    
    public SGPApiClientBL()
    {
        super();
    }
    
    public void enviarNotificacion(NotificacionMovilDTO notificacion) 
    {
        String sURL = null;
        
        HttpPost request = null;
        StringEntity userEntity = null;
        CloseableHttpResponse response = null;
        
        Gson prettyGson   = null;
        String jsonRequest = null;
        
        int httpStatus = -1;
        
        try {
            sURL = basePath + "/sgp-api/fp-client/enviar/notificacion";
            sURL = String.format(sURL);
            
            prettyGson = new GsonBuilder().setPrettyPrinting().create();
            jsonRequest = prettyGson.toJson(notificacion);
            
            userEntity = new StringEntity(jsonRequest, ContentType.APPLICATION_JSON );
            request = this.createPostRequest(sURL);
            request.setEntity(userEntity);
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new SGPException("Respuesta no satisfactoria de SGP-Api.");

            log.info("Se envio la notificación correctamente.");
        } catch(SGPException | IOException ex) {
            log.error("Se presentó un problema en la comunicación con SGP-Api...", ex);
	}
    }
    
}
