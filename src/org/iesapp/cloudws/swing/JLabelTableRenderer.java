/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.swing;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Josep
 */
public class JLabelTableRenderer extends DefaultTableCellRenderer {  
  
     
    @Override
        public Component getTableCellRendererComponent(JTable table,  
                Object value, boolean isSelected, boolean hasFocus, int row,  
                int column) {  
      
        JLabel label = (JLabel) value;
        label.setOpaque(true);
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        } else {
            label.setOpaque(true);
            label.setBackground(table.getBackground());
            label.setForeground(table.getForeground());


        }

        return label;
    }
}
