/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class AttachmentForm extends javax.swing.JFrame {
    
    Socket socket;
    DataOutputStream dos;
    File file;
    String host;
    int port;
    String myusername;
    StringTokenizer st;
    DataInputStream dis;
    MainForm main;
    DecimalFormat df = new DecimalFormat("##,#00");
    
    /**
     * Creates new form AttachmentForm
     */
    public AttachmentForm() {
        initComponents();
    }
    
    public void initFrame(String myusername, MainForm main, String host, int port){
        /**  Start Connecting to Server.. **/
        setTitle("Connecting to Server...");
        this.myusername = myusername;
        this.main = main;
        this.host = host;
        this.port = port;
        connect();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        txtFileURL = new javax.swing.JTextField();
        btnsendFile = new javax.swing.JButton();
        txtSendTo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Send To:");

        txtFileURL.setEditable(false);
        txtFileURL.setBackground(new java.awt.Color(204, 204, 204));
        txtFileURL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtFileURL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFileURLMouseClicked(evt);
            }
        });

        btnsendFile.setBackground(new java.awt.Color(163, 22, 22));
        btnsendFile.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        btnsendFile.setForeground(new java.awt.Color(255, 255, 255));
        btnsendFile.setText("Send File");
        btnsendFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsendFileActionPerformed(evt);
            }
        });

        jLabel2.setText("Select File:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtFileURL, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtSendTo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnsendFile, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFileURL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSendTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnsendFile)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnsendFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsendFileActionPerformed
        // TODO add your handling code here:
        if(txtSendTo.getText().length() > 0 && txtFileURL.getText().length() > 0){
            setMyTitle("Sending File..");
            disableMyGUI(false);
            btnsendFile.setText("Sending....");
            new Thread(new AttachmentThread(socket, file, txtSendTo.getText(), myusername, AttachmentForm.this)).start();
        }else{
            JOptionPane.showMessageDialog(null, "Incomplete Form.!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnsendFileActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_formWindowClosing

    private void txtFileURLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFileURLMouseClicked
        // TODO add your handling code here:
        int browse = fileChooser.showOpenDialog(this);
        if(browse == fileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
            txtFileURL.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_txtFileURLMouseClicked

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
            java.util.logging.Logger.getLogger(AttachmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AttachmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AttachmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AttachmentForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AttachmentForm().setVisible(true);
            }
        });
    }
    
    public void connect(){
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("CMD_SHARINGSOCKET "+ myusername);
            System.out.println("File Sharing Socket was connected..!");
            /*
            Start Thread
            */
            new Thread(new SocketThread()).start();
            setMyTitle("Connected.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*  Set Window title  */
    public void setMyTitle(String title){
        setTitle(title);
    }
    
    /*  Disable the GUI  */
    public void disableMyGUI(boolean disableThis){
        if(disableThis){
            txtFileURL.setEnabled(true);
            txtSendTo.setEnabled(true);
            //btnBrowse.setEnabled(true);
            btnsendFile.setEnabled(true);
        }else{
            txtFileURL.setEnabled(false);
            txtSendTo.setEnabled(false);
            //btnBrowse.setEnabled(false);
            btnsendFile.setEnabled(false);
        }
    }
    
    
    class SocketThread implements Runnable{
        
        @Override
        public void run() {
            try {
                while(!Thread.currentThread().isInterrupted()){
                    String data = dis.readUTF();
                    st = new StringTokenizer(data);
                    /** Get CMD **/
                    String cmd = st.nextToken();
                    /** Check CMD **/
                    switch(cmd){
                        case "CMD_SENDFILE":
                            SoundEffect.FileSharing.play(); //   Play Audio
                            try {
                                String filename = st.nextToken();
                                int confirm  = JOptionPane.showConfirmDialog(null, "File was sent to you '"+ filename +"', do you want to Accept this.?");
                                if(confirm == 0){
                                    //SoundEffect.FileSharing.stop();
                                    setMyTitle("Receiving File....");
                                    disableMyGUI(false);
                                    System.out.println("Receiving File....");
                                    String path = "D:\\"+ filename;
                                    FileOutputStream fos = new FileOutputStream(path);
                                    InputStream input = socket.getInputStream();
                                    /**  Create a temporary file **/
                                    byte[] buffer = new byte[1024];
                                    int count, percent = 1;
                                    while((count = input.read(buffer)) > 0){
                                        percent = percent + 1;
                                        setMyTitle(df.format(percent) +"% Receiving File...");
                                        fos.write(buffer, 0, count);
                                    }
                                    fos.flush();
                                    fos.close();
                                    setMyTitle("Done..!");
                                    JOptionPane.showMessageDialog(null, "File was saved '"+ path +"'");
                                    System.out.println("File was saved: "+ path);
                                }else{
                                    //SoundEffect.FileSharing.stop();
                                    socket.close();
                                }
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        
                        case "CMD_SENDFILEERROR":
                            String msg = "";
                            while(st.hasMoreTokens()){
                                msg = msg +" "+ st.nextToken();
                            }
                            System.out.println(msg);
                            JOptionPane.showMessageDialog(null, msg);
                            setMyTitle("Sending Error.!");
                            disableMyGUI(true);
                            break;
                            
                        default: 
                            System.out.println("Unknown CMD: "+ cmd); 
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Socket was closed..!");
                setVisible(false);
                main.attachmentOpen = false;
            }
        }
        
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnsendFile;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txtFileURL;
    private javax.swing.JTextField txtSendTo;
    // End of variables declaration//GEN-END:variables
}
