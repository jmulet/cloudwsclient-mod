/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 *
 * @author Josep
 */
public class NodeParser {
    protected FileInfo rootValue;
    protected JsonElement root;
    protected JsonArray childrenArray;
    
    public NodeParser(String content)
    {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(content);
        JsonElement rootChildren = element.getAsJsonObject().get("children");
        root = element.getAsJsonObject().get("value");
        //rootValue is always a fileDescriptor
        Gson gson = new Gson();
        rootValue = gson.fromJson(root, FileInfo.class);
        childrenArray = parser.parse(rootChildren.toString()).getAsJsonArray();
        
    }

    public FileInfo getRootValue() {
        return rootValue;
    }

    public void setRootValue(FileInfo rootValue) {
        this.rootValue = rootValue;
    }

    public JsonElement getRoot() {
        return root;
    }

    public void setRoot(JsonElement root) {
        this.root = root;
    }

    public JsonArray getChildrenArray() {
        return childrenArray;
    }

    public void setChildrenArray(JsonArray childrenArray) {
        this.childrenArray = childrenArray;
    }
}
