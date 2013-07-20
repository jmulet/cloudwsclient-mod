/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josep
 */
public class CloudClientSession {
    
    public static String baseURL = "http://localhost:8080/pdaweb/webresources/cloud/";
    protected String sessionId=null;
    private String user="";
    private String pwd="";
    
    private static final String AUTHENTICATE = "authenticate";
    private static final String CLOSE = "close";
    private static final String UPLOAD = "upload";
    private static final String DOWNLOAD = "download";
    private static final String LISTDIR = "dir";
    private static final String TREE = "tree";
    private static final String DELETE = "delete";
    private static final String MKDIR = "mkdir";
    private static final String COPY = "copy";
    private static final String MOVE = "move";
    private static final String RENAME = "rename";
    protected HashMap sessionMap;
    protected String home;
    private  HttpCloudUtils httpCloudUtils;
    protected long sessionUploadLimit;
    
    public CloudClientSession(String user, String pwd) 
    {
        try {
            this.user = user;
            this.home = user;
            this.pwd = pwd;
            //athenticate
            httpCloudUtils = new HttpCloudUtils();
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL+AUTHENTICATE+"?user="+user+"&pwd="+pwd);
            if(httpGET.getStatusLine().getStatusCode()==HttpCloudUtils.OK)
            {
                String content = httpGET.getContent();
                Gson gson = new Gson();
                HashMap map = gson.fromJson(content, HashMap.class);
                sessionId = (String) map.get("id");
                if(map.containsKey("uploadlimit"))
                {
                    sessionUploadLimit = Long.parseLong((String) map.get("uploadlimit"));
                    //0 = unlimited otherwise in bytes
                }
                sessionMap = map;
                //System.out.println("Session map is : "+sessionMap);
            }
            
           
        } catch (IOException ex) {
            //Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public TreeNode getTreeFrom(String path, boolean onlyDir)
    {
        TreeNode list = null;
        if (getSessionId() == null) {
            return list;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("sessionId", getSessionId());
            map.put("path", path);
            map.put("onlyDir", onlyDir?"yes":"no");
             
           CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + TREE, map);
           
           if(httpGET.getStatusLine().getStatusCode()==HttpCloudUtils.OK){
               String content = httpGET.getContent();
               //System.out.print("content:"+content+"\n\n");
           
               TreeNodeBuilder treeBuilder = new TreeNodeBuilder(content, null);
               list = treeBuilder.getTree();
               //System.out.println(list.toString(0));
               
         }
           else
           {
               //System.out.println(CloudClientSession.class.getName()+": "+httpGET.getStatusLine().getStatusCode()+
               //        httpGET.getStatusLine().getReasonPhrase());
           }
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public ArrayList<FileInfo> listDir(String path)
    {
        ArrayList<FileInfo> list = null;
        if (getSessionId() == null) {
            return list;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("sessionId", getSessionId());
            map.put("path", path);
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + LISTDIR, map);
           
           if(httpGET.getStatusLine().getStatusCode()==HttpCloudUtils.OK){
               String content = httpGET.getContent();
               //System.out.print("content:"+content);
               JsonParser parser = new JsonParser();
               JsonArray array = parser.parse(content).getAsJsonArray();
               Gson gson = new Gson();
               list = new ArrayList<FileInfo>();
               for (JsonElement element : array) {
                   FileInfo fd = gson.fromJson(element, FileInfo.class);
                   list.add(fd);
               }

           }
           else
           {
               //System.out.println(CloudClientSession.class.getName()+": "+httpGET.getStatusLine().getStatusCode()+
               //        httpGET.getStatusLine().getReasonPhrase());
           }
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public int delete(String path)
    {
       if (getSessionId() == null) {
            return -1;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("sessionId", getSessionId());
            map.put("path", path);
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + DELETE, map);
           
           return httpGET.getStatusLine().getStatusCode();
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public HashMap uploadFile(java.io.File file, String to_path)
    {
        HashMap fromJson = null;
        if (getSessionId() == null) {
            return fromJson;
        }
        try {
            
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("file",file);
            map.put("filename",file.getName());
            map.put("path",to_path);
            map.put("sessionId", getSessionId());
            
           //System.out.println("httpPOST: "+ CloudClientSession.baseURL + UPLOAD +" with map "+map);
            
           CachedHttpResponse httpPOST = httpCloudUtils.httpPOST(CloudClientSession.baseURL + UPLOAD, map);
           //Retrieve response from post
           String content = httpPOST.getContent();
           Gson gson = new Gson();
           //System.out.println("\n\ncontent unparsed->"+content);
           fromJson = gson.fromJson(content, HashMap.class);
           fromJson.put("upload.status", httpPOST.getStatusLine().getStatusCode()+"");
           if(httpPOST.getStatusLine().getStatusCode()==HttpCloudUtils.OK &&
              fromJson.containsKey("upload.filedescriptor"))
           {
                String fileInfoJson = (String) fromJson.get("upload.filedescriptor");
                FileInfo fromJson1 = gson.fromJson(fileInfoJson, FileInfo.class);
                fromJson.put("upload.fileinfo",fromJson1); //overwrite with parsed bean
           }
           //System.out.println("\n\ncontent map->"+fromJson);
           
           return fromJson;
        } catch (IOException ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fromJson;
    }
    
    /**
     * Closes the current session
     * @return 
     */
    public int close()
    {
        if (getSessionId() == null) {
            return -1;
        }
        try {
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + CLOSE + "?sessionId=" + getSessionId());
            httpCloudUtils.dispose();
            return httpGET.getStatusLine().getStatusCode();
        } catch (IOException ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return -1;
    }

    public int ping() {
        if(this.getSessionId()==null)
        {
            return -1;
        }
        return 200;
    }

    public String getHome() {
        return home;
    }

    public int mkdir(String path) {
        if (getSessionId() == null) {
            return -1;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("sessionId", getSessionId());
            map.put("path", path);
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + MKDIR, map);
           
            return httpGET.getStatusLine().getStatusCode();
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public InputStream download(String path) {
        InputStream content = null;
        if (getSessionId() == null) {
            return content;
        }
        
        try {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("sessionId", getSessionId());
            map.put("path", path);
            map.put("disposition","attachment");
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + DOWNLOAD, map);
           
            if(httpGET.getStatusLine().getStatusCode()==HttpCloudUtils.OK)
            {
                 content = httpGET.getInputStream();
            }
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
         
    }

    public int copy(String from_path, String to_path) {
          if (getSessionId() == null) {
            return -1;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("from_path", from_path);
            map.put("to_path", to_path);
            map.put("sessionId", getSessionId());
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + COPY, map);
           
            return httpGET.getStatusLine().getStatusCode();
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    
      public int move(String from_path, String to_path) {
          if (getSessionId() == null) {
            return -1;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("from_path", from_path);
            map.put("to_path", to_path);
            map.put("sessionId", getSessionId());
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + MOVE, map);
           
            return httpGET.getStatusLine().getStatusCode();
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int rename(String from_path, String to_path) {
          if (getSessionId() == null) {
            return -1;
        }
        
        try {
           
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("from_path", from_path);
            map.put("to_path", to_path);
            map.put("sessionId", getSessionId());
             
            CachedHttpResponse httpGET = httpCloudUtils.httpGET(CloudClientSession.baseURL + RENAME, map);
           
            return httpGET.getStatusLine().getStatusCode();
           
        } catch (Exception ex) {
            Logger.getLogger(CloudClientSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public HashMap getSessionMap() {
        return sessionMap;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getSessionUploadLimit() {
        return sessionUploadLimit;
    }
    
}
