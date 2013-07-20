/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author Josep
 */
public class CachedHttpResponse {
    protected StatusLine statusLine;
    protected String content;
    protected InputStream inputStream;
    
    public CachedHttpResponse(HttpResponse response) throws IOException
    {
        statusLine = response.getStatusLine();
        InputStream stream = response.getEntity().getContent();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        org.apache.commons.io.IOUtils.copy(stream, baos);
        byte[] bytes = baos.toByteArray();    
        
        //Clonar l'stream
        inputStream = new ByteArrayInputStream(bytes); 
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }
        rd.close();
        content = builder.toString();
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public String getContent() {
        return content;
    }

    public InputStream getInputStream() {
       return inputStream;
    }
}
