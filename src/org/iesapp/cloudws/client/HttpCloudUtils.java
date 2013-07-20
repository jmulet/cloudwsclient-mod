/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Josep
 */
public class HttpCloudUtils {
  
    public static int POST = 0;
    public static int PUT = 1;
    public static int GET = 2;
    public static int DELETE = 3;
    public static int OK = 200;
     
    public HttpCloudUtils()
    {
      
    }
    
    public void dispose()
    {
    }
    
    public CachedHttpResponse httpGET(String query) throws IOException
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //System.out.println("httpGET query="+query);
        HttpGet httpget = new HttpGet(query);
        HttpResponse response = httpclient.execute(httpget);
        CachedHttpResponse cachedHttpResponse = new CachedHttpResponse(response);
        httpget.releaseConnection();
        httpclient.getConnectionManager().shutdown();
        return cachedHttpResponse;        
    }
    
    public CachedHttpResponse httpGET(String query, HashMap<String, String> params) throws IOException, URISyntaxException
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
      
        URIBuilder builder = new URIBuilder();
        builder.setPath(query);
        for(String key: params.keySet())
        {
            builder.addParameter(key, params.get(key));
        }
        URI uri = builder.build();
        //System.out.println("httpGET query="+uri.toString());
        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpclient.execute(httpget);
        CachedHttpResponse cachedHttpResponse = new CachedHttpResponse(response);
        httpget.releaseConnection();
        httpclient.getConnectionManager().shutdown();
        return cachedHttpResponse;        
    }
    
    
    
    public CachedHttpResponse httpPOST(String query, HashMap<String, Object> objects) throws UnsupportedEncodingException, IOException
    {
        DefaultHttpClient httpclient = new DefaultHttpClient();
       
        HttpPost httppost = new HttpPost(query);

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        for(String key: objects.keySet())
        {
            Object obj = objects.get(key);
            //System.out.println("Passing obj "+obj.getClass()+" "+key+"\n");
            if(obj.getClass().equals(java.io.File.class))
            {
                //System.out.println("as FileBody");
                FileBody fileBody  = new FileBody((File) obj);
                entity.addPart(key, fileBody);
//                fileBody.setListener(new IStreamListener() {
//                    @Override
//                    public void counterChanged(int delta) {
//                        // do something
//                        //System.out.println("Upload status ->"+delta);
//                    }
//                });
            }
            else if(obj.getClass().equals(ByteArrayBody.class))
            {
                //System.out.println("as byteArrayBody");
                entity.addPart(key, (ByteArrayBody) obj);
            }
            else
            {
                //System.out.println("as stringbody");
                entity.addPart(key, new StringBody(obj.toString()));
            }
        }
    
      //  httppost.addHeader("Content-type", "multipart/form-data");
        httppost.setEntity(entity);
        
        HttpResponse response= httpclient.execute(httppost);
  //      EntityUtils.consume(response.getEntity());
        CachedHttpResponse cachedHttpResponse = new CachedHttpResponse(response);
        httppost.releaseConnection();
        httpclient.getConnectionManager().shutdown();
        return cachedHttpResponse;
    }
    
    public static String getContent(HttpResponse response) throws IOException
    {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
    
    
    // save download file to new location
    public static void writeToFile(InputStream uploadedInputStream,
		String downloadFileLocation) {
 
                if(uploadedInputStream==null)
                {
                    return;
                }
                
		try {
			int read = 0;
			byte[] bytes = new byte[1024];
 
			FileOutputStream out = new FileOutputStream(new File(downloadFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
		}
 
	}
    
    public static DefaultTreeModel getDefaultTreeModelFrom(TreeNode treeNode)
    {
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        root.setUserObject(treeNode.value);
        
        for(TreeNode node: treeNode.getChildren())
        {
            DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode();
             
            dmtn.setUserObject(node.value);
            root.add(dmtn);
            addNodesRecursive(dmtn, node);
        }
        
        DefaultTreeModel model = new DefaultTreeModel(root);
        return model;
    }

    private static void addNodesRecursive(DefaultMutableTreeNode dmtn, TreeNode node) {
        for(TreeNode n: node.getChildren())
        {
            DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode();
            
            dmtn2.setUserObject(n.value);
            dmtn.add(dmtn2);
            addNodesRecursive(dmtn2, n);
        }
    }
   
}
