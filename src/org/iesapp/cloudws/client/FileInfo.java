/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import com.google.gson.annotations.SerializedName;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Josep
 */
public class FileInfo  {

    public static final long KB = 1024;
    public static final long MB = 1024*KB;
    public static final long GB = 1024*MB;
    
    @SerializedName("name")
    protected String name;
    
    @SerializedName("bytes")
    protected long bytes;
 
    protected String size;
    
    @SerializedName("dir")
    protected boolean dir;
    
    protected String type="";
    
    protected String lastModified;
    
    protected String url;
    
    protected String icon64;
    
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDir() {
        return dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

   
    
    @Override
    public String toString()
    {
        String onlyname = name;
        if(name.contains(File.separator))
        {
           int idx= name.lastIndexOf(File.separator);
           onlyname = name.substring(idx+1);
        }
        return onlyname;
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon64() {
        return icon64;
    }

    public void setIcon64(String icon64) {
        this.icon64 = icon64;
    }
    
    public ImageIcon getImageIcon() throws IOException
    {
        byte[] decodeBase64 = org.apache.commons.codec.binary.Base64.decodeBase64(this.icon64);
        ByteArrayInputStream bais = new ByteArrayInputStream(decodeBase64);
        BufferedImage buffimg = ImageIO.read(bais);
        bais.close();
        return new ImageIcon(buffimg);
    }
    
    @Override
    public FileInfo clone()
    {
        FileInfo fi = new FileInfo();
        fi.bytes = this.bytes;
        fi.dir = this.dir;
        fi.icon64 = this.icon64;
        fi.lastModified = this.lastModified;
        fi.name = this.name;
        fi.size = this.size;
        fi.type = this.type;
        fi.url =this.url;
        return fi;
    }
    
    public static String convertFileSize(long bytes)
    {
        String txt = bytes + " B";
        
        if(bytes>=KB && bytes<MB)
        {
            txt = round(bytes/KB,1)+" KB";
        }
        else if(bytes>=MB && bytes<GB)
        {
            txt = round(bytes/MB,1)+" MB";
        } 
        else if(bytes>=GB )
        {
            txt = round(bytes/GB,1)+" GB";
        }
        return txt;
    }
    
    public static double round(double x, int d)
    {
        double pow = Math.pow(10, d);
        return Math.round(x*pow)/pow;
    }
}
