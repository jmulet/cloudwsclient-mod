/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Josep
 */
public class Test {

     
     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        
            CloudClientSession.baseURL = "http://localhost:8080/pdaweb/webresources/cloud/";
            CloudClientSession session = new CloudClientSession("jmulet","5109");
            if(session.ping()!=HttpCloudUtils.OK)
            {
                System.out.println("Server not responding");
                System.exit(1);
            }
            ArrayList<FileInfo> listDir = session.listDir("");
            System.out.print("Contents of dir : root\n");
            
            if(listDir!=null)
            {
                for(FileInfo fd: listDir)
                {
                    System.out.println(fd.toString());
                }
            }
            listDir = session.listDir("jmulet");
            System.out.print("Contents of dir : jmulet\n");
            if(listDir!=null)
            {
                for(FileInfo fd: listDir)
                {
                    System.out.println(fd.toString());
                }
            }
        //Upload file
        HashMap uploadFile = session.uploadFile(new File("C:\\new2.txt"), "jmulet");
        System.out.println("First file "+uploadFile);
        HashMap uploadFile1 = session.uploadFile(new File("C:\\pdaweb.ico"), "jmulet\\Public");
        System.out.println("First file "+uploadFile1);
            
            int close = session.close();
            System.out.println("close = "+close);
            System.exit(0);
            
        }
}
    
