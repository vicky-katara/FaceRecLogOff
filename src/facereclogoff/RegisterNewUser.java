/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facereclogoff;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import javax.swing.JOptionPane;

/**
 *
 * @author Vicky Katara
 */
public class RegisterNewUser extends javax.swing.JFrame {
    
    private RegisterNewUser getParentJFrame(){
        return this;
    }
    
    public static final int NUM_TRAINING_IMAGES = 10;
    /**
     * Creates new form RegisterNewUser
     */
    public RegisterNewUser() {
        initComponents();
        jProgressBar1.setMinimum(1);
        jProgressBar1.setMaximum(NUM_TRAINING_IMAGES);
        jProgressBar1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        userNameTF = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Begin Image Capture");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Enter User Name:");

        jProgressBar1.setMaximum(10);
        jProgressBar1.setToolTipText("");
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(userNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String userName = userNameTF.getText();
        if(userName==null || userName.contentEquals("") || userName.contentEquals(" ")){
            JOptionPane.showMessageDialog(this, "Please enter a valid user name");
            return;
        }
        
        userNameTF.setEnabled(false);
        jProgressBar1.setVisible(true);
        new Thread(new ImageCapture(1, userName, new VideoCapture())).start();

    }//GEN-LAST:event_jButton1ActionPerformed

    class ProgressUpdater implements Runnable{
        int count = 0;
        ProgressUpdater(int count){
            this.count = count;
        }
        @Override
        public void run(){
            jProgressBar1.setValue(count);
            jProgressBar1.setString((int)((count*1.0/(NUM_TRAINING_IMAGES))*100)+"%");
            System.out.println("Set string to "+(int)((count*1.0/(NUM_TRAINING_IMAGES))*100)+"%");
        }
    }
    
    class ImageCapture implements Runnable{
        int count = 0;
        String username;
        VideoCapture camera;
        ImageCapture(int count, String username, VideoCapture camera){
            this.count = count;
            this.username = username;
            this.camera = camera;
        }
        @Override
        public void run(){
            try {
                
                if(!camera.isOpened()){
                    camera.open(0); //Useless
//                    System.out.println("Camera Error");
                }
                else{
                    System.out.println("Camera OK?");
                }
                Thread.sleep(1000);
                
                Mat frame = new Mat();

                new Thread(new ProgressUpdater(count)).start();
                //camera.grab();
                //System.out.println("Frame Grabbed");
                //camera.retrieve(frame);
                //System.out.println("Frame Decoded");

                camera.read(frame);
                System.out.println("Frame Saved");

                /* No difference
                camera.release();
                */

                System.out.println(count+" Captured Frame Width " + frame.width());

                Highgui.imwrite(username+count+".png", frame);
                System.out.println("OK");
                camera.release();
                if(count<NUM_TRAINING_IMAGES)
                    new Thread(new ImageCapture(count+1, username, camera)).start();
                else{
                    camera.release();
                    javax.swing.JFrame parentThis = getParentJFrame();
                    parentThis.dispose();
                    MainScreen.main(null);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(RegisterNewUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
            java.util.logging.Logger.getLogger(RegisterNewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterNewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterNewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterNewUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterNewUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField userNameTF;
    // End of variables declaration//GEN-END:variables
}
