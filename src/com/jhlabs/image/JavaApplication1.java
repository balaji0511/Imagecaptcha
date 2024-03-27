/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jhlabs.image;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author new
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

BufferedImage img1=ImageIO.read(new FileInputStream("f:\\flower1.jpg"));
        BufferedImage img2=ImageIO.read(new FileInputStream("f:\\flower1.jpg"));
         
        //BoxBlurFilter b=new BoxBlurFilter();
       //ConvolveFilter b=new ConvolveFilter();
       // GaussianFilter b=new GaussianFilter();
        GlowFilter b2=new GlowFilter();// 2
        LensBlurFilter b3=new LensBlurFilter();//3
       //MotionBlurFilter b=new MotionBlurFilter();
        //ShadowFilter b=new ShadowFilter();
        //SmartBlurFilter b=new SmartBlurFilter();
        //UnsharpFilter b=new UnsharpFilter();
       // VariableBlurFilter b=new VariableBlurFilter();
        img2=b2.filter(img1, img2);
        img2=b2.filter(img2, img2);

Graphics2D g2d = (Graphics2D)img2.getGraphics();
Shape shape = new java.awt.geom.Ellipse2D.Float(20, 20, 200,200);
g2d.setClip(shape);
int x = 0;
int y = 0;
g2d.drawImage(new ImageIcon("f:\\flower1.jpg").getImage(), x, y, null);


        ImageIO.write(img2,"JPG",new FileOutputStream("f:\\flower3.jpg"));
    }
    
}
