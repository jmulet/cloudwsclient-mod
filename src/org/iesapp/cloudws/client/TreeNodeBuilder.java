/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import com.google.gson.JsonElement;

/**
 *
 * @author Josep
 */
public class TreeNodeBuilder {
    
    private TreeNode node;
    private TreeNode parent;
    
    public TreeNodeBuilder(String content, TreeNode parent)
    {
        this.parent = parent;
        NodeParser parser = new NodeParser(content);
        parser.getRootValue();
        parser.getChildrenArray();
        
        node = new TreeNode(parser.getRootValue()); 
        if(parent!=null)
        {
            parent.addNode(node);
        }
        
        for(int i=0; i<parser.getChildrenArray().size(); i++)
        {
            JsonElement element = parser.getChildrenArray().get(i);
            //Recursive call
            TreeNodeBuilder builder = new TreeNodeBuilder(element.toString(), node);
        }
    }
    
    
    
    
    public TreeNode getTree()
    {
        return node;
    }
   
}
