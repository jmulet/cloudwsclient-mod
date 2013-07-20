/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.client;

import java.util.ArrayList;

/**
 *
 * @author Josep
 */
public class TreeNode {
protected ArrayList<TreeNode> children = new ArrayList<TreeNode>();
protected Object value;
    //methods

    public TreeNode(Object value)
    {
        this.value = value;
    }
    
    public void addNode(TreeNode node)
    {
        children.add(node);
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public String toString(int indent)
    {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<indent; i++)
        {
            builder.append("\t");
        }
        builder.append(value.toString());
        builder.append("\n");
        for(TreeNode child: children)
        {
             builder.append(child.toString(indent+1));
        }
        return builder.toString();
    }
}
