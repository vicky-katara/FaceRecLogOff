/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facereclogoff;

import org.opencv.contrib.FaceRecognizer;
/**
 *
 * @author Vicky Katara
 */
public class VRecognizer extends FaceRecognizer{
   VRecognizer(){
       super(10);
   }
   
   
   
   public static void main(String args[]){
       new VRecognizer();
   }
}
