package Clamshell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;

public class ClamshellUI extends javax.swing.JFrame {
    
    private ArrayList<Entry> passList; // must specify Entry type object
    private int selectedRow = 0; // row to delete
    
    // the values passed in from initial UI window (authentication procedure)
    private File authFile;
    private String userName;
    
    /**
     * UI constructor
     */
    public ClamshellUI() {
        initComponents();
        passList = new ArrayList();
    }
    
    /*
     * Auto-Generated Code from Java Swing GUI builder
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        sname = new javax.swing.JTextField();
        uname = new javax.swing.JTextField();
        pass = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        deleteRowField = new javax.swing.JTextField();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(554, 255));
        setName("clamshell"); // NOI18N

        jTextPane2.setEditable(false);
        jTextPane2.setDoubleBuffered(true);
        jTextPane2.setMaximumSize(new java.awt.Dimension(1280, 1280));
        jScrollPane2.setViewportView(jTextPane2);

        sname.setToolTipText("Enter Service Name");
        sname.setName(""); // NOI18N

        uname.setToolTipText("Enter User Name");

        pass.setToolTipText("Enter Password");

        addButton.setText("Add Entry");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete Entry");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        deleteRowField.setToolTipText("Enter the row you wish to delete.");
        deleteRowField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                deleteRowFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sname, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteRowField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton)
                    .addComponent(deleteRowField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Remove entry from text area based on the user input (number provided)
     * @param evt 
     */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
       //String userNameTest = JOptionPane.showInputDialog(null, "Enter User Name", "User Name", JOptionPane.QUESTION_MESSAGE);
        
       if (!passList.isEmpty())
       {   
           //jButton4.setEnabled(true);
            try{
                int entryNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Entry Number:", "Delete Entry?", JOptionPane.QUESTION_MESSAGE));
                passList.remove((int)selectedRow);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Entry Number Out of Bounds", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateTextArea();
       }
       else
       {
           deleteButton.setEnabled(false);
       }
       
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    /**
     * Update the text area field when passList is updated
     */
    private void updateTextArea()
    {
        String bigSky = "";
            for (int i = 0; i < passList.size(); ++i)
            {
                bigSky += i + " " + passList.get(i).getServiceName()+ " " + passList.get(i).getUserName()+ " " + passList.get(i).getPassword();
            }

            jTextPane2.setText(bigSky); // delete entry from flexible object type (arraylist)
            jTextPane2.updateUI(); // update jTextField2 (text area)
    }
    
    /**
     * Add Entry From three inputs to jTextPane2
     * with entry number stamp attached
     * @param evt 
     */
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        
        if (passList.size() > 0)
        {
            deleteButton.setEnabled(true); // now we can delete entries
        }
        String newEntry = sname.getText() + " " + uname.getText() + " " + pass.getText() + "\n";
        passList.add(new Entry(sname.getText(), uname.getText(), pass.getText()));
        
        String bigSky = "";
        for (int i = 0; i < passList.size(); ++i)
        {
            bigSky += i + " " + newEntry;
        }
        jTextPane2.setText(bigSky);
        jTextPane2.updateUI();
        //jTextField6.setText("" + passList.size());
    }//GEN-LAST:event_addButtonActionPerformed

        
    /**
     * When row for deletion is manually set via text editing - updates selectedRow variable
     * @param evt 
     */
    private void deleteRowFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deleteRowFieldKeyTyped
        selectedRow = Integer.parseInt(deleteRowField.getText()); // update the handler variable
    }//GEN-LAST:event_deleteRowFieldKeyTyped
    
    /**
     * Beginning insertion point of program
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClamshellUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClamshellUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClamshellUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClamshellUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form via dispatch thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            @Override
            public void run() {
            //
                ClamshellUI clam = new ClamshellUI();
                AuthenticateDialog dialog = new AuthenticateDialog(new javax.swing.JFrame(), true);
                dialog.setLocationRelativeTo(null);// center the auth dialog window in middle of screen
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                
                if (dialog.getAuthenticated() == true) { // ensure user is validated before continuing
                    
                    // make sure we pass the relevant information user provided
                    //  to new UI screen before disposing of old UI screen
                    
                    //authFile = dialog.getAuthFile();
                    //userName = dialog.getUsername();
                    
                    dialog.dispose();
                    clam.setVisible(true);
                } else {
                    System.exit(0); // otherwise, deny access - shutdown program
                }
            }
            //
        });
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField deleteRowField;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextField pass;
    private javax.swing.JTextField sname;
    private javax.swing.JTextField uname;
    // End of variables declaration//GEN-END:variables
}
