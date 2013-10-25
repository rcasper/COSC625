package Clamshell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
/**
 * @author Andrew Ramsey
 */
public class ClamshellUI extends javax.swing.JFrame {
    private int selectedRow = 0;
   //private Zaes aes;
    public ArrayList<String> table; // must specify String type object
    
    /**
     * UI constructor
     */
    public ClamshellUI() {
        initComponents();
        table = new ArrayList();
        //aes = new Yaes();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        sname = new javax.swing.JTextField();
        uname = new javax.swing.JTextField();
        pass = new javax.swing.JTextField();
        openButton = new javax.swing.JButton();
        passwordTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        unameTextField = new javax.swing.JTextField();
        unameLabel = new javax.swing.JLabel();
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

        jTextPane2.setEditable(false);
        jTextPane2.setEnabled(false);
        jScrollPane2.setViewportView(jTextPane2);

        sname.setToolTipText("Enter Service Name");
        sname.setEnabled(false);

        uname.setToolTipText("Enter User Name");
        uname.setEnabled(false);

        pass.setToolTipText("Enter Password");
        pass.setEnabled(false);

        openButton.setText("Open");
        openButton.setToolTipText("");
        openButton.setEnabled(false);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        passwordTextField.setEnabled(false);

        passwordLabel.setText("Password");

        addButton.setText("Add Entry");
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        unameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                unameTextFieldKeyTyped(evt);
            }
        });

        unameLabel.setText("User Name");

        deleteButton.setText("Delete Entry");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        deleteRowField.setToolTipText("Enter the row you wish to delete.");
        deleteRowField.setEnabled(false);
        deleteRowField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRowFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(unameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(unameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passwordLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteRowField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openButton)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unameLabel)
                    .addComponent(deleteButton)
                    .addComponent(deleteRowField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enableUIFields()
    {
        sname.setEnabled(true);
        
        
    }
    
    private boolean validatePassword()
    {
        try {
            String password = passwordTextField.getText();
            File f = new File("");
            BufferedReader br = new BufferedReader(new FileReader(f));
            
            if (!password.isEmpty()) // make sure user password area is not empty
            {
                // get first two lines of file and ensure they are key and init vector for cbc mode
                String key32 = br.readLine();
                String key64 = br.readLine();
                String initVector = br.readLine();
                // in this configuration, we are open to attack - must encrypt passwords in key file using RC4
                if (key32.length() == 32 && key64.length() == 32 && initVector.length() == 32){
                    // convert password from jTextField4.getText() to bytes and hex
                    String keyIV = key32 + key64 + initVector;
                    //return aes.validatePassword(password, keyIV);
                    return true;
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Key Length Invalid.\nFile may be corrupted.", "Invalid Passord File", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Password Field is Empty\nPlease Enter Valid Password", "No Password Provided", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            System.err.println("Could not read file");
        }
        return false;
    }
    
    /**
     * Open file and validate if password entered is correct
     * @param evt 
     */
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        
        if (unameTextField.getText().isEmpty() == false)
        {
            try {
                File f = new File(unameTextField.getText().toLowerCase() + ".txt");
                
                if (f.exists())
                {
                
                if (validatePassword())
                {
                       //table = aes.decryptFile(f);
                }
                else
                {
                    
                    
                }
                
                }
                else
                {
                   Object[] options = {"Yes, please",
                    "No, thanks",
                    "No eggs, no ham!"};
                int n = JOptionPane.showOptionDialog(null,
                        "Would you like some green eggs to go "
                        + "with that ham?",
                        "A Silly Question",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]); 
                }
            }
            catch (Exception e)
            {
                //JOptionPane.showMessageDialog(null, "Key Length Invalid.\nFile may be corrupted.", "Invalid Passord File", JOptionPane.ERROR_MESSAGE);
                
                openButton.setEnabled(false);
                passwordTextField.setEnabled(false);
                
                unameTextField.setText("");
            }
        }
    }//GEN-LAST:event_openButtonActionPerformed

    /**
     * Remove entry from text area based on the user input (number provided)
     * @param evt 
     */
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
       //String userNameTest = JOptionPane.showInputDialog(null, "Enter User Name", "User Name", JOptionPane.QUESTION_MESSAGE);
        
       if (!table.isEmpty())
       {   
           //jButton4.setEnabled(true);
            try{
                //entryNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Entry Number:", "Delete Entry?", JOptionPane.QUESTION_MESSAGE));
                table.remove((int)selectedRow);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Entry Number Out of Bounds", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            String bigSky = "";
        
            for (int i = 0; i < table.size(); ++i)
            {
                bigSky += i + " " + table.get(i);
            }
            
            jTextPane2.setText(bigSky); // delete entry from flexible object type (arraylist)
            jTextPane2.updateUI(); // update jTextField2 (text area)
       }
       else
       {
           deleteButton.setEnabled(false);
       }
       
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    /**
     * Add Entry From three inputs to jTextPane2
     * with entry number stamp attached
     * @param evt 
     */
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        String newEntry = sname.getText() + " " + uname.getText() + " " + pass.getText() + "\n";
        table.add(newEntry);
        deleteButton.setEnabled(true); // now we can delete entries
        
        String bigSky = "";
        
        for (int i = 0; i < table.size(); ++i)
        {
            bigSky += i + " " + table.get(i);
        }
        jTextPane2.setText(bigSky);
        jTextPane2.updateUI();
        //jTextField6.setText("" + table.size());
    }//GEN-LAST:event_addButtonActionPerformed

    /**
     * When row for deletion is manually set via text editing
     * @param evt 
     */
    private void deleteRowFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowFieldActionPerformed
        selectedRow = Integer.parseInt(deleteRowField.getText()); // update the handler variable
    }//GEN-LAST:event_deleteRowFieldActionPerformed

    private void unameTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_unameTextFieldKeyTyped
        if (unameTextField.getText().length() != 0 && unameTextField.getText() != null)
        {
            openButton.setEnabled(true);
        }
        else
        {
            openButton.setEnabled(false);
        }
    }//GEN-LAST:event_unameTextFieldKeyTyped

    /**
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
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClamshellUI().setVisible(true);
            }
        });
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField deleteRowField;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JButton openButton;
    private javax.swing.JTextField pass;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField passwordTextField;
    private javax.swing.JTextField sname;
    private javax.swing.JTextField uname;
    private javax.swing.JLabel unameLabel;
    private javax.swing.JTextField unameTextField;
    // End of variables declaration//GEN-END:variables
}
