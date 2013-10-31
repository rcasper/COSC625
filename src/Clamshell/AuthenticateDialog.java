package Clamshell;

import java.io.*;
import javax.swing.JOptionPane;
//import java.nio.file.*;

public class AuthenticateDialog extends javax.swing.JDialog {

    private boolean isAuthenticated = false;
    private File authFile;
    private String userName;
    
    /**
     * Creates new form AuthenticateDialog
     */
    public AuthenticateDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /**
     * Return authenticated status
     * @return 
     */
    public boolean getAuthenticated()
    {
        return isAuthenticated;
    }
    
    /**
     * Pass File which has been validated via authentication to main UI
     * @return 
     */
    public File getAuthFile()
    {
        if (!authFile.exists())
        {
            return null;
        }
        return authFile;
    }
    
    /**
     * Return user name
     * @return 
     */
    public String getUsername()
    {
        return userName;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        username = new javax.swing.JTextField();
        password = new javax.swing.JTextField();
        openButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        newButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Authentication");
        setResizable(false);

        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameKeyTyped(evt);
            }
        });

        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameKeyTyped(evt);
            }
        });

        openButton.setText("Open");
        openButton.setEnabled(false);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        jLabel3.setText("User Name");

        newButton.setText("New");
        newButton.setEnabled(false);
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newButton))
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openButton)
                    .addComponent(newButton)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Check if password decrypted matched user input for given password file
     * @param s
     * @return 
     */
    private boolean passwordChecked(String pw)
    {
        //eventually we must ensure that the filename (encrypted) matches 
        //the uname & password supplied by the user, when uname is encrypted
        //it is checked to make sure there is a match or not
        return fileExists(pw);
    }
    
    /**
     * Attempt to Authenticate user
     * @param evt 
     */
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        
        // check if file exists, then check if password is matching
        if (fileExists(username.getText())){
            isAuthenticated = true;
            authFile = new File(username.getText() + ".txt");
            this.dispose();// get rid of this instance
        }
        else // otherwise, throw error
        {
            JOptionPane.showMessageDialog(null, "User Name Does Not Exist", "Invalid User Name", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_openButtonActionPerformed

    /**
     * Create new password file with user input if does not exist
     * @param evt
     */
    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        // first check if file exists, if so report error
        if (fileExists(username.getText())){
            JOptionPane.showMessageDialog(null, "User Name Already Exists", "Invalid User Name", JOptionPane.ERROR_MESSAGE);
        }
        else // otherwise, proceed to create new file
        {
            // pop up window to confirm to user
            int confirm = JOptionPane.showConfirmDialog(null, "Create New Password File?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
            
            if (confirm == JOptionPane.OK_OPTION) {
       
                isAuthenticated = true;
                userName = username.getText();
                authFile = new File(encryptUnameRC4());

                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(authFile));
                    bw.write(userName);
                    bw.write(password.getText());
                    bw.close();
                } catch (Exception o) {
                }
                this.dispose(); // run main UI
            } else {
                // return to auhentication UI
            }
        }
    }//GEN-LAST:event_newButtonActionPerformed

    /**
     * If text typed in username and password, enable authentication buttons
     * @param evt 
     */
    private void usernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyTyped
        // has to be length 2 or more because UI elements mess up
        if (username.getText().isEmpty() == false && password.getText().isEmpty() == false
              &&  username.getText().length() > 1 && username.getText().length() > 1) 
        {
            openButton.setEnabled(true);
            newButton.setEnabled(true);
        }
        else
        {
            openButton.setEnabled(false);
            newButton.setEnabled(false);
        }
    }//GEN-LAST:event_usernameKeyTyped
    
    /**
     * Convert user name input to RC4-encrypted string
     * @return 
     */
    private String encryptUnameRC4()
    {
        String uname = username.getText();
        String pass = password.getText();
        RC4 rc = new RC4();
        String encryptedFileName = rc.cipher(uname, pass);
        return encryptedFileName;
    }
        
    /**
     * Check to see if file exists in default directory
     * @param s
     * @return 
     */
    private boolean fileExists(String s) {
        File f = new File(s + ".txt");

        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton newButton;
    private javax.swing.JButton openButton;
    private javax.swing.JTextField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
