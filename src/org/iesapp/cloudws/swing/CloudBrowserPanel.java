/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iesapp.cloudws.swing;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.iesapp.cloudws.client.CloudClientSession;
import org.iesapp.cloudws.client.FileInfo;
import org.iesapp.cloudws.client.HttpCloudUtils;
import org.iesapp.cloudws.client.TreeNode;
import org.iesapp.framework.util.CoreCfg;

/**
 *
 * @author Josep
 */
public class CloudBrowserPanel extends javax.swing.JPanel {

    private DefaultTableModel modelTable1;
    protected CloudClientSession session;
    private ArrayList<FileInfo> listDir;
    private String currentPath;
    private String action;
    private ArrayList<FileInfo> actionClipboard;
   
    
    protected static List<String> images = Arrays.asList(new String[]{"jpg","jpeg","gif","png","tif","bmp"});
    protected static List<String> document = Arrays.asList(new String[]{"txt","doc","docx","log","odt"});
    protected static List<String> movies = Arrays.asList(new String[]{"mp4","qt","avi","mov"});
    protected static List<String> compressed = Arrays.asList(new String[]{"zip","7z","tar","rar"});
    private  DefaultComboBoxModel modelComboBox1;
    private File localCurrentDir;
    private ResourceBundle bundle;
    private boolean errorAlreadyShown;
   
    /**
     * Creates new form CloudBrowser
     */
    public CloudBrowserPanel() {
        initComponents();
        enableBrowser(false);
        
        ButtonGroup group = new ButtonGroup();
        group.add(jRadioButton1);
        group.add(jRadioButton2);
        
        modelComboBox1 = new DefaultComboBoxModel();
        jComboBox1.setModel(modelComboBox1);
        
        jTree1.setModel(new DefaultTreeModel(null));
    }

    
    public ArrayList<FileInfo> getSelectedFiles()
    {
        ArrayList<FileInfo> list  = new ArrayList<FileInfo>();
        
        for(int row : jTable1.getSelectedRows())
        {
            list.add( listDir.get(row) );
        }
        
        return list;
    }
    
    public FileInfo getSelectedFile() {
        int row = jTable1.getSelectedRow();
        FileInfo fi = null;
        if(row>=0)
        {
            fi =  listDir.get(row) ;
        }
        return fi;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int row, int col)
            {
                return false;
            }
        };
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPanel1KeyReleased(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/cloudws/icons/newfolder2.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/iesapp/cloudws/bundle"); // NOI18N
        jButton1.setToolTipText(bundle.getString("newfolder")); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/cloudws/icons/parentdir.png"))); // NOI18N
        jButton2.setToolTipText(bundle.getString("parentdir")); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/cloudws/icons/home.png"))); // NOI18N
        jButton5.setToolTipText(bundle.getString("home")); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.setFocusable(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/iesapp/cloudws/icons/upload.png"))); // NOI18N
        jButton3.setToolTipText(bundle.getString("upload")); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jRadioButton1.setSelected(true);
        java.util.ResourceBundle bundle1 = java.util.ResourceBundle.getBundle("org/iesapp/cloudws/bundle"); // NOI18N
        jRadioButton1.setText(bundle1.getString("open")); // NOI18N

        jRadioButton2.setText(bundle1.getString("save")); // NOI18N

        jLabel1.setText(bundle1.getString("donwloadoptions")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)))
        );

        jSplitPane1.setResizeWeight(0.3);

        this.bundle = bundle;
        modelTable1 = new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                bundle.getString("nombre"), bundle.getString("tamanyo"), bundle.getString("tipo"), bundle.getString("fechamodificado"),"URL"
            }
        );
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(modelTable1);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jTable1.setRowHeight(30);
        jTable1.getColumnModel().getColumn(0).setCellRenderer(new JLabelTableRenderer());
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new JLabelTableRenderer());
        jScrollPane1.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane1);

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 294, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                    .addGap(32, 32, 32)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        if(row<0 || col<0)
        {
            return;
        }
        if(evt.getClickCount()==2)
        {
            if(jTable1.getColumnName(col).equals(bundle.getString("nombre")))
            {
                if(listDir.get(row).isDir())
                {
                    fillTable(listDir.get(row).getName());
                }
                else
                {
                    download();
                }
            }
            else if(jTable1.getColumnName(col).equals("URL") && !listDir.get(row).isDir())
            {
                //Copy to clipboard
                StringSelection stringSelection = new StringSelection(listDir.get(row).getUrl());

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, new ClipboardOwner() {
                    @Override
                    public void lostOwnership(Clipboard clipboard, Transferable contents) {
                        //
                    }
                });
               JOptionPane.showMessageDialog(this,bundle.getString("urlcopied"));
      
          
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String tmp = currentPath;
        tmp = tmp.replaceAll("/", File.separator);
        if(tmp.endsWith(File.separator))
        {
            tmp = tmp.substring(0, tmp.length()-2);
        }
        
        if(tmp.contains(File.separator))
        {
            int idx = tmp.lastIndexOf(File.separator);
            tmp = tmp.substring(0, idx);
        }
        else
        {
            tmp = "";  //this is root
        }
        
        fillTable(tmp);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyReleased
         
    }//GEN-LAST:event_jPanel1KeyReleased

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        int[] rows = jTable1.getSelectedRows();
       
     
        if (evt.getKeyCode()==KeyEvent.VK_C && evt.isControlDown()) //Copy
        {       
            if (rows.length == 0) {
                return;
            }
            action = "COPY";
            actionClipboard = new ArrayList<FileInfo>();
            for(int row: rows)
            {
                actionClipboard.add(listDir.get(row).clone());
            }
           }
        else if (evt.getKeyCode()==KeyEvent.VK_X && evt.isControlDown()) //Cut
        {       
            if (rows.length == 0) {
                return;
            }
            actionClipboard = new ArrayList<FileInfo>();
          
            for(int row: rows)
            {
                String aname = listDir.get(row).getName();
                //cut has important resctrictions: can't cut protected folders such as public
                if (row < 0 || !aname.startsWith(session.getHome()) 
                        || aname.equalsIgnoreCase(session.getHome()+File.separator+"Public")) {
                    return;
                }
                action = "CUT";
                actionClipboard.add(listDir.get(row).clone());
            }
           }
        else if (evt.getKeyCode()==KeyEvent.VK_V && evt.isControlDown()) //paste
        {       
            if(actionClipboard!=null)
            {
                if(action.equals("CUT"))
                {
                    for(FileInfo fd: actionClipboard)
                    {
                        session.move(fd.getName(), currentPath);
                    }
                }
                else
                {
                   for(FileInfo fd: actionClipboard)
                    {
                        session.copy(fd.getName(), currentPath);
                    }
                }
                actionClipboard = null;
                fillTable(currentPath);
            }
        }
        if(evt.getKeyCode()==KeyEvent.VK_F2)
        {
            //Rename
            int selectedRow = jTable1.getSelectedRow();
            if(selectedRow<0)
            {
                return;
            }
            String name = listDir.get(selectedRow).getName();
            if(!name.startsWith(session.getHome()) ||
                name.equalsIgnoreCase(session.getHome()+File.separator+"Public")
                || !name.contains(File.separator))
            { 
                //Can't rename files outside my home folder
                return;
            }
            String onlyName = name;
            int idx  = name.lastIndexOf(File.separator);
            String relative = onlyName.substring(0,idx);
            onlyName = onlyName.substring(idx+1);
            
            if(!onlyName.isEmpty())
            {
                String newname = JOptionPane.showInputDialog(this,bundle.getString("rename"), onlyName);
                if(newname!=null && !newname.isEmpty())
                {
                    //System.out.println("Trying to rename to "+relative+File.separator+newname);
                    session.rename(listDir.get(selectedRow).getName(), relative+File.separator+newname);
                    fillTable(currentPath);
                }
            }
            
        }
        else if(evt.getKeyCode()==KeyEvent.VK_DELETE)
        {
            //delete
            int[] selectedRows = jTable1.getSelectedRows();
           
            //Ask confirmation
            int option = JOptionPane.showConfirmDialog(this, bundle.getString("doyouwant")+" "+selectedRows.length+" "+bundle.getString("files")+"?", bundle.getString("confirmation"), JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION)
            {

                for (int row0 : selectedRows) {
                    FileInfo fd = listDir.get(row0);
                    int delete = session.delete(fd.getName());
                }
                 fillTable(currentPath);
            }
            
            
        }
    }//GEN-LAST:event_jTable1KeyReleased

    //UPLOAD
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         JFileChooser fchooser = new JFileChooser();
         if(localCurrentDir!=null)
         {
            fchooser.setCurrentDirectory(localCurrentDir);
         }
         fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
         fchooser.setMultiSelectionEnabled(true);
         int showOpenDialog = fchooser.showOpenDialog(this);
         if(showOpenDialog==JFileChooser.APPROVE_OPTION)
         {
            localCurrentDir = fchooser.getCurrentDirectory();
            java.io.File[] selectedFiles= fchooser.getSelectedFiles();
            for(File tmp: selectedFiles)
            {
                jLabel2.setText(bundle.getString("uploading") +tmp.getAbsolutePath());
                java.io.File selectedFile = new File(tmp.getAbsolutePath());
                //System.out.println("in broser file is "+selectedFile.getClass());
                HashMap uploadFile = session.uploadFile(selectedFile, currentPath);
                if(!uploadFile.get("upload.status").equals("200"))
                {
                    //Show reasons why upload failed
                    String message = uploadFile.get("upload.result")+"\n"+"Virus scan: "+uploadFile.get("clamscan.result")+
                            "\n"+uploadFile.get("clamscan.status");
                    JOptionPane.showMessageDialog(this, message,"Error: "+bundle.getString("uploadfailed"), JOptionPane.WARNING_MESSAGE);
                }
            }    
            jLabel2.setText("");
             
            if(currentPath.startsWith(session.getHome()))
            {
                fillTable(currentPath);
            }
            else
            {
                fillTable(session.getHome());
            }
         }
    }//GEN-LAST:event_jButton3ActionPerformed

    //mkdir
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         session.mkdir(currentPath+File.separator+bundle.getString("newfolder2"));
         fillTable(currentPath);
    }//GEN-LAST:event_jButton1ActionPerformed

    //method download
    private void download()
    {
        int row = jTable1.getSelectedRow();
        InputStream stream = session.download(listDir.get(row).getName());
        if(stream==null)
        {
            //System.out.println("ERROR downloaded stream is null");
        }
        String onlyName = listDir.get(row).getName();
        if (onlyName.contains(File.separator)) {
            int idx = onlyName.lastIndexOf(File.separator);
            onlyName = onlyName.substring(idx + 1);
        }
        if (jRadioButton1.isSelected()) {  //only open the file

            File file2 = new File(System.getProperty("java.io.tmpdir") );
            if (!file2.exists()) {
                file2.mkdir();
            }
            String outputName = System.getProperty("java.io.tmpdir") + File.separator + onlyName;

            HttpCloudUtils.writeToFile(stream, outputName);
           
            try {
                Desktop.getDesktop().open(new File(outputName));
            } catch (IOException ex) {
                Logger.getLogger(CloudBrowserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { //download it

            File file2 = new File(System.getProperty("user.home") + File.separator + "Downloads");
            if (!file2.exists()) {
                file2.mkdir();
            }
            String outputName = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + onlyName;

            HttpCloudUtils.writeToFile(stream, outputName);
            try {
                Desktop.getDesktop().browse(new URL("file://"+System.getProperty("user.home") + File.separator + "Downloads").toURI());
            } catch (Exception ex) {
                Logger.getLogger(CloudBrowserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Home
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        fillTable(session.getHome());
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        TreePath newPath = evt.getNewLeadSelectionPath();
        if(newPath != null) { 
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) newPath.getLastPathComponent(); 
            FileInfo fi = ((FileInfo) node.getUserObject());
            if(fi.isDir())
            {
                fillTable(fi.getName());
            }
            else
            {
                //
            }
        }
    }//GEN-LAST:event_jTree1ValueChanged

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        fillTable((String) jComboBox1.getSelectedItem());
    }//GEN-LAST:event_jComboBox1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    public CloudClientSession getSession() {
        return session;
    }

    public void setSession(CloudClientSession session) {
        this.session = session;
        updateComponents();
    }

    private void updateComponents() {
        if (session==null || session.ping() != 200) {
            enableBrowser(false);
            if(!errorAlreadyShown)
            {
                 String sms = bundle.getString("noconection")+"\n"+CoreCfg.cloudBaseURL;
                 INotificationStyle style = new DarkDefaultNotification().withWindowCornerRadius(8).withWidth(300).withAlpha(0.86f);

                // Now lets build the notification
                new NotificationBuilder().withStyle(style) // Required. here we set the previously set style
                    .withTitle("Error") // Required.
                    .withMessage(sms) // Optional
                    .withIcon(new ImageIcon(NotificationBuilder.ICON_ERROR)) // Optional. You could also use a String path
                    .withDisplayTime(5000) // Optional
                    .withPosition(Positions.NORTH_WEST) // Optional. Show it at the center of the screen
                    .showNotification(); // this retu
                
                errorAlreadyShown = true;
            }
        } else {
            enableBrowser(true);
            fillTable(session.getHome());
        }

    }

    private void enableBrowser(boolean enabled) {
        jButton1.setEnabled(enabled);
        jButton2.setEnabled(enabled);
        jButton3.setEnabled(enabled);
        jButton5.setEnabled(enabled);
        jRadioButton1.setEnabled(enabled);
        jRadioButton2.setEnabled(enabled);
        jTable1.setEnabled(enabled);
        if (!enabled) {
            while (jTable1.getRowCount() > 0) {
                modelTable1.removeRow(0);
            }
        }
    }

    private void fillTable(String path) {
        
        path =path==null?"":path;
        
        if(session==null || session.ping()!=200)
        {
            return;
        }
        currentPath = path;
        
        if(modelComboBox1.getSize()>10)
        {
            modelComboBox1.removeElementAt(modelComboBox1.getSize()-1);
        }
         for(int i=0; i<modelComboBox1.getSize(); i++)
        {
            if(((String) modelComboBox1.getElementAt(i)).equals(path))
            {
                modelComboBox1.removeElementAt(i);
                
            }
        }
        modelComboBox1.insertElementAt(path,0);
        jComboBox1.setSelectedIndex(0);
            
        TreeNode treeFrom = session.getTreeFrom("", true);      
        DefaultTreeModel model = HttpCloudUtils.getDefaultTreeModelFrom(treeFrom);
        jTree1.setModel(model);
   
        while (jTable1.getRowCount() > 0) {
            modelTable1.removeRow(0);
        }
        
        
        //Set model according it is a public folder or not
        boolean isPublic = false;
        if(path.contains(File.separator))
        {
            int idx = path.indexOf(File.separator);
            if(path.substring(idx+1).toLowerCase().startsWith("public"))
            {
                 isPublic = true;
            }
        }
        
        if(isPublic)
        {
             modelTable1 = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                   bundle.getString("nombre"), bundle.getString("tamanyo"), bundle.getString("tipo"), bundle.getString("fechamodificado"), "URL"
                });
             jTable1.setModel(modelTable1);
             jTable1.getColumnModel().getColumn(0).setCellRenderer(new JLabelTableRenderer());
             jTable1.getColumnModel().getColumn(4).setCellRenderer(new JLabelTableRenderer());

        }
        else
        {
             modelTable1 = new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    bundle.getString("nombre"), bundle.getString("tamanyo"), bundle.getString("tipo"), bundle.getString("fechamodificado")
                });
             jTable1.setModel(modelTable1);
             jTable1.getColumnModel().getColumn(0).setCellRenderer(new JLabelTableRenderer());

        }
        listDir = session.listDir(path);
        if (listDir == null) {
            //System.out.println("Error listDir is null");
            return;
        }

        for (FileInfo fd : listDir) {

           String onlyname = fd.getName();
           if(onlyname.contains(File.separator))
           {
               int idx = onlyname.lastIndexOf(File.separator);
               onlyname = onlyname.substring(idx+1);
           }
           
//           String iconname;
//           if(fd.isDir())
//           {
//               iconname = "folder2.png";
//           }
//           else
//           {
//           
//            if(images.contains(fd.getType()))
//            {
//                iconname = "images.png";
//            }
//            else if(document.contains(fd.getType()))
//            {
//                iconname = "document.png";
//            }
//            else if(movies.contains(fd.getType()))
//            {
//                iconname = "movies.png";
//            }
//            else if(compressed.contains(fd.getType()))
//            {
//                iconname = "zip.png";
//            }
//            else if(fd.getType().equals("xls"))
//            {
//                iconname = "xls.png";
//            }
//            else if(fd.getType().equals("pdf"))
//            {
//                iconname = "pdf.png";
//            }
//            else  
//            {
//                iconname = "file.png";
//            }
//        }
           
           JLabel label= new JLabel(onlyname);
            try {
                //label.setIcon(new ImageIcon(CloudBrowserPanel.class.getResource("/org/iesapp/cloudws/icons/"+iconname)));
                label.setIcon(fd.getImageIcon());
            } catch (IOException ex) {
                Logger.getLogger(CloudBrowserPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            if(isPublic)
            {           
                JLabel label2 = new JLabel(fd.getUrl());
                if(!fd.isDir())
                {
                    label2.setIcon(new ImageIcon(CloudBrowserPanel.class.getResource("/org/iesapp/cloudws/icons/clipboard.png")));
                }
                modelTable1.addRow(new Object[]{label, fd.getSize(), fd.getType(), fd.getLastModified(), label2});
            }
            else
            {
                modelTable1.addRow(new Object[]{label, fd.getSize(), fd.getType(), fd.getLastModified()});                
            }
           }
    }

   
 
}
