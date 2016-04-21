/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facereclogoff;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Vicky Katara
 */
public class FaceDetection extends javax.swing.JFrame {

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    MatOfRect frontalFaceMatRect = new MatOfRect();
    MatOfRect eyeMatRect = new MatOfRect();
    MatOfRect haarProfileFaceMatRect = new MatOfRect();
    MatOfRect lbpcascadeProfilefaceMatRect = new MatOfRect();
    
    CascadeClassifier frontalFaceDetector = new CascadeClassifier(FaceDetection.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1).replace("%20", " "));
    CascadeClassifier haarcascadeEyeDetector = new CascadeClassifier(FaceDetection.class.getResource("haarcascade_eye.xml").getPath().substring(1).replace("%20", " "));
    CascadeClassifier haarcascadeProfilefaceDetector = new CascadeClassifier(FaceDetection.class.getResource("haarcascade_profileface.xml").getPath().substring(1).replace("%20", " "));
    CascadeClassifier lbpcascadeProfilefaceDetector = new CascadeClassifier(FaceDetection.class.getResource("lbpcascade_profileface.xml").getPath().substring(1).replace("%20", " "));
    
    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    if (webSource.grab()) {
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            //
//                            frontalFaceDetector.detectMultiScale(frame, faceDetections);
//                            //System.out.println(Arrays.toString(faceDetections.toArray()));
//                            for (Rect rect : faceDetections.toArray()) {
//                               System.out.println("Frontal Face Detected at "+rect.x+", "+rect.y);
//                                Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
//                                        new Scalar(255, 0 ,0));
//                            }
                            //
                            detectUsingClassifier(frontalFaceDetector, frame, frontalFaceMatRect, 0);
                            detectUsingClassifier(haarcascadeEyeDetector, frame, eyeMatRect, 1);
                            detectUsingClassifier(haarcascadeProfilefaceDetector, frame, haarProfileFaceMatRect, 2);
                            detectUsingClassifier(lbpcascadeProfilefaceDetector, frame, lbpcascadeProfilefaceMatRect, 3);
                            //
                            Highgui.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error");
                        }
                    }
                }
            }
        }
    }
    
    void detectUsingClassifier(CascadeClassifier classifier, Mat frame, MatOfRect faceDetections, int classifierNum ) {
        Scalar scalar = getScalar(classifierNum);
        classifier.detectMultiScale(frame, faceDetections);
        //System.out.println(Arrays.toString(faceDetections.toArray()));
        for (Rect rect : faceDetections.toArray()) {
           System.out.println(classifierName(classifierNum)+" detected at "+rect.x+", "+rect.y);
            Core.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), scalar);
        }
    }
    
    Scalar getScalar(int classifierNum){
        switch(classifierNum){
            case 0: // face
                return new Scalar(255, 0, 0);
            case 1: // eye
                return new Scalar(0, 255, 0);
            case 2: // haarProfile
                return new Scalar(0, 0, 255);
            case 3: // lbpProfile
                return new Scalar(255, 255, 0);
            default:
                return null;
        }
    }
    
    String classifierName(int classifierNum){
        switch(classifierNum){
            case 0: // face
                return "Haar Frontal Face";
            case 1: // eye
                return "Haar Eye";
            case 2: // haarProfile
                return "Haar Profile Face";
            case 3: // lbpProfile
                return "LBP Frontal Face";
            default:
                return null;
        }
    }  
    
    /**
     * Creates new form FaceDetection
     */
    public FaceDetection() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                .addComponent(stopButton)
                .addGap(164, 164, 164))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        
        webSource = new VideoCapture(0); // video capture from default cam
        myThread = new DaemonThread(); //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thrad
        startButton.setEnabled(false);  // deactivate start button
        stopButton.setEnabled(true);  //  activate stop button

    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        myThread.runnable = false;            // stop thread
        startButton.setEnabled(false);   // activate start button 
        stopButton.setEnabled(true);     // deactivate stop button

        webSource.release();  // stop caturing fron cam
    }//GEN-LAST:event_stopButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.out.println("Working Directory = " + System.getProperty("user.dir")+"\n classifier: "+FaceDetection.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1).replace("%20", " "));
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
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
