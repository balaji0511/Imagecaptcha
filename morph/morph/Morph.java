//////////////////////////////////////////////////////////////////
//                                                              //
// Morph 0.9 - My peculiar image morphing algorithm             //
//                                                              //
// David Tompkins -- 4/22/96                                    // 
//                                                              //
// tompkins@cs.stanford.edu                                     //
// http://www-cs-students.stanford.edu/~tompkins/               //
//                                                              //
// Copyright (c) 1996 by David Tompkins. All Rights Reserved.   //
//                                                              //
// Permission to use, copy, modify, and distribute this         //
// software and its documentation for NON-COMMERCIAL            //
// purposes and without fee is hereby granted provided that     //
// this copyright notice appears in all copies.                 //
//                                                              //
// ------------------------------------------------------------ //
//                                                              //
// <param> list:                                                //
//                                                              //
//		imagesrc	Source Image                    //
//		imagedst	Destination Image               //
//		audio		.au audiofile to play           //
//		framecnt	Number of intermediate frames   //
//		interval	Animation frame interval        //
//		cptN		Control point pair, where N     //
// 				is 1..3 and the format of the   //
// 				value is x1:y1:x2:y2            //
//                                                              //
//////////////////////////////////////////////////////////////////

package morph;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.awt.image.MemoryImageSource;
import java.awt.Graphics;
import java.awt.Color;
import java.util.StringTokenizer;

class ControlPoint
{
   int x1, y1;
   int x2, y2;

   ControlPoint (int X1, int Y1, int X2, int Y2)
   {
      x1 = X1;
      y1 = Y1;
      x2 = X2;
      y2 = Y2;
   }

   ControlPoint (String param)
   {
      StringTokenizer st = new StringTokenizer(param, ":\n\r");

      if (st.countTokens() != 4)
      {
	 System.out.println("ControlPoint: Incorrect control point format");
	 return;
      }

      x1 = Integer.parseInt(st.nextToken());
      y1 = Integer.parseInt(st.nextToken());
      x2 = Integer.parseInt(st.nextToken());
      y2 = Integer.parseInt(st.nextToken());
   }
}

class SimpleTriangle
{
   // A SimpleTriangle has a horizontal edge from v1 to v2, and
   // v3 is above or below this edge. v1 is to the left (lesser x) of v2.

   int x1, x2, y;
   int x3, y3;

   SimpleTriangle(int X1, int X2, int Y, int X3, int Y3)
   {
      x1 = X1;
      x2 = X2;
      y  = Y;
      x3 = X3;
      y3 = Y3;
   }

   public boolean test()
   {
      if (x1 <= x2)
	 return (true);
      
      return (false);
   }

   public boolean type1()
   {
      if (y3 > y)
	 return (true);

      return (false);
   }

   public boolean type2()
   {
      if (y3 < y)
	 return (true);

      return (false);
   }
}

class Triangle
{
   int            x1, y1;
   int            x2, y2;
   int            x3, y3;
   SimpleTriangle t1 = null;
   SimpleTriangle t2 = null;

   Triangle(int X1, int Y1, int X2, int Y2, int X3, int Y3)
   {
      // sort vertices by y coordinate

      if (Y1 <= Y2)
      {
	 if (Y2 <= Y3)
	 {
	    // order v1, v2, v3
            x1 = X1;
            y1 = Y1;
            x2 = X2;
            y2 = Y2;
            x3 = X3;
            y3 = Y3;
	    return;
	 }

	 if (Y1 <= Y3)
	 {
	    // order v1, v3, v2
            x1 = X1;
            y1 = Y1;
            x2 = X3;
            y2 = Y3;
            x3 = X2;
            y3 = Y2;
	    return;
	 }

	 // order v3, v1, v2
         x1 = X3;
         y1 = Y3;
         x2 = X1;
         y2 = Y1;
         x3 = X2;
         y3 = Y2;
	 return;
      }

      if (Y1 <= Y3)
      {
	 // order v2, v1, v3
         x1 = X2;
         y1 = Y2;
         x2 = X1;
         y2 = Y1;
         x3 = X3;
         y3 = Y3;
	 return;
      }

      if (Y2 <= Y3)
      {
	 // order v2, v3, v1
         x1 = X2;
         y1 = Y2;
         x2 = X3;
         y2 = Y3;
         x3 = X1;
         y3 = Y1;
	 return;
      }
      
      // order v3, v2, v1
      x1 = X3;
      y1 = Y3;
      x2 = X2;
      y2 = Y2;
      x3 = X1;
      y3 = Y1;
   }

   public boolean test()
   {
      return ((y1 == y2) || (y2 == y3) || (y1 == y3));
   }

   public void convert()
   {
      double m; /* dx/dy */
      int    x;
 
      m = (double)(x3 - x1) / (double)(y3 - y1);
      x = x1 + (int)Math.round(m * (double)(y2 - y1));
 
      if (x2 <= x)
      {
	 t1 = new SimpleTriangle(x2, x, y2, x3, y3);
	 t2 = new SimpleTriangle(x2, x, y2, x1, y1);
         return;
      }
      
      t1 = new SimpleTriangle(x, x2, y2, x3, y3);
      t2 = new SimpleTriangle(x, x2, y2, x1, y1);
   }
}

public class Morph extends Applet implements Runnable, ImageLoaderClient
{
   static int NUM_TRIANGLES    = 14;
   static int DEFAULT_FRAMECNT = 8;
   static int DEFAULT_INTERVAL = 100;	// ms

   private Thread       thread        = null;
   private AudioClip    audio         = null;
   private Image        frames[]      = null;
   private int          current_frame = 0;
   private int          step          = 1;
   private int          framecnt      = DEFAULT_FRAMECNT;
   private int          interval      = DEFAULT_INTERVAL;
   private boolean      error         = false;
   private String       errmsg        = "Unknown Error.";
   private String       stsmsg        = "";
   private int          clock         = 0;
   private ControlPoint cp1, cp2, cp3;
   private Image        imagesrc, imagedst;
   private String       param, audiofile, imagesrcfile, imagedstfile;
   private Color        currentbg;

   public void init()
   {
      if ((imagesrcfile = getParameter("imagesrc")) == null)
      {
	 showError("Invalid imagesrc parameter");
	 return;
      }

      if ((imagedstfile = getParameter("imagedst")) == null)
      {
	 showError("Invalid imagedst parameter");
	 return;
      }

      audiofile = getParameter("audio");

      if ((param = getParameter("framecnt")) != null)
         framecnt = Integer.parseInt(param);

      if ((param = getParameter("interval")) != null)
         interval = Integer.parseInt(param);

      if ((param = getParameter("cpt1")) == null)
      {
	 showError("Invalid cpt1 parameter");
	 return;
      }
      cp1 = new ControlPoint(param);

      if ((param = getParameter("cpt2")) == null)
      {
	 showError("Invalid cpt2 parameter");
	 return;
      }
      cp2 = new ControlPoint(param);

      if ((param = getParameter("cpt3")) == null)
      {
	 showError("Invalid cpt3 parameter");
	 return;
      }
      cp3 = new ControlPoint(param);
   }
 
   public void start()
   {
      if (thread == null)
      {
         thread = new Thread(this);
         thread.start();
      }
   }

   public void stop()
   {
      if (thread != null && thread.isAlive())
         thread.stop();
      thread = null;
   }

   public void run()
   {
      int          iw, ih;
      int          from[], to[];
      PixelGrabber pg;
      ImageLoader  il;

      currentbg = getBackground();

      il       = new ImageLoader(this);
      imagesrc = getImage(getDocumentBase(), imagesrcfile);
      imagedst = getImage(getDocumentBase(), imagedstfile);
      il.addImage(imagesrc);
      il.addImage(imagedst);

      drawBackground();
      drawClock("Loading...");
      
      if (il.waitForAll() == false)
      {
	 showError("Unable to load images");
	 return;
      }
      il = null;

      drawClock();

      if (audiofile != null)
      {
	 drawClock("Loading Audio...");
	 audio = getAudioClip(getDocumentBase(), audiofile);
	 drawClock();
      }

      drawClock("Processing...");

      iw = imagesrc.getWidth(this);
      ih = imagesrc.getHeight(this);

      from = new int[iw * ih];
      to   = new int[iw * ih];

      pg = new PixelGrabber(imagesrc, 0, 0, iw, ih, from, 0, iw);
      try { pg.grabPixels(); }
      catch (InterruptedException e)
      {
	 showError(e);
         return;
      }
      pg = null;

      drawClock();

      pg = new PixelGrabber(imagedst, 0, 0, iw, ih, to, 0, iw);
      try { pg.grabPixels(); }
      catch (InterruptedException e)
      {
	 showError(e);
         return;
      }
      pg = null;

      drawClock();

      frames             = new Image[framecnt];
      frames[0]          = imagesrc;
      frames[framecnt-1] = imagedst;

      morph(from, to, iw, ih);

      from = null;
      to   = null;
	 
      restoreBackground();

      if (audio != null)
         audio.play();

      current_frame = 0;
      step          = 1;

      do
      {
         repaint(0);

	 if (((current_frame == framecnt-1) && (step == 1)) ||
	     ((current_frame == 0)          && (step == -1)))
	 {
	    step *= -1;

	    try  { thread.sleep(4*interval); }
	    catch (InterruptedException e) { }
	 }

	 current_frame += step;

	 try { thread.sleep(interval); }
	 catch (InterruptedException e) { }
      }
      while (thread != null);
   }

   public void update(Graphics g)
   {
      paint(g);
   }
 
   public void paint(Graphics g)
   {
      if ((frames != null) && (frames[framecnt-2] != null))
         g.drawImage(frames[current_frame], 0, 0, this);
      else if (error)
      {
         drawBackground(Color.white, Color.red);
         drawClock(Color.white, Color.red, errmsg);
      }
      else
         drawClock(stsmsg);
   }

   public void imageLoaderUpdate(int status)
   {
      switch (status)
      {
	 case ILC_LOADING:

		drawClock();
		break;

	 case ILC_ERROR:

		error = true;
		repaint(0);
		break;

	 case ILC_COMPLETE:

		break;
      }
   }

   void restoreBackground()
   {
      Graphics g        = getGraphics();
      Color    previous = g.getColor();
 
      g.setColor(currentbg);
      g.fillRect(0, 0, size().width, size().height);
      g.setColor(previous);
   }
   
   void drawBackground(Color bg, Color fg)
   {
      Graphics g        = getGraphics();
      Color    previous = g.getColor();
      int      width    = size().width;
      int      height   = size().height;
 
      g.setColor(bg);
      g.fillRect(0, 0, width, height);
      g.setColor(fg);
      g.drawRect(0, 0, width-1, height-1);
 
      g.setColor(previous);
   }

   void drawBackground()
   {
      drawBackground(Color.white, Color.blue);
   }

   void drawClock(Color bg, Color fg)
   {
      if (error)
         return;
 
      Graphics g        = getGraphics();
      Color    previous = g.getColor();
      int      x        = 20 + (int)(Math.sin((Math.PI / 8.0) * clock) * 14);
      int      y        = 20 - (int)(Math.cos((Math.PI / 8.0) * clock) * 14);
 
      g.setColor(bg);
      g.fillRect(5, 5, 30, 30);
      g.setColor(fg);
      g.drawOval(5, 5, 30, 30);
      g.drawLine(20, 20, x, y);
 
      g.setColor(previous);
      clock++;
   }

   void drawClock(Color bg, Color fg, String msg)
   {
      if (error)
         return;
 
      Graphics g        = getGraphics();
      Color    previous = g.getColor();
      int      width    = size().width;
      int      x        = 20 + (int)(Math.sin((Math.PI / 8.0) * clock) * 14);
      int      y        = 20 - (int)(Math.cos((Math.PI / 8.0) * clock) * 14);
 
      g.setColor(bg);
      g.fillRect(1, 1, width-2, 30);
      g.setColor(fg);
      g.drawOval(5, 5, 30, 30);
      g.drawLine(20, 20, x, y);
      g.drawString(msg, 40, 24);
      
      System.out.println(msg);
      
      stsmsg = msg;

      g.setColor(previous);
      clock++;
   }

   void drawClock()
   {
      drawClock(Color.white, Color.blue);
   }

   void drawClock(String msg)
   {
      drawClock(Color.white, Color.blue, msg);
   }

   void showError(Exception e)
   {
      errmsg = "Morph: error: " + e;
      showStatus(errmsg);
      System.err.println(errmsg);
      error = true;
      repaint(0);
   }
 
   void showError(String s)
   {
      errmsg = "Morph: error: " + s;
      showStatus(errmsg);
      System.err.println(errmsg);
      error = true;
      repaint(0);
   }

   private void morph(int[] from, int[] to, int iw, int ih)
   {
      int              f;
      int[]            step;
      Triangle         t;
      SimpleTriangle[] t1, t2, t3;

      drawClock();
	 
      t1 = new SimpleTriangle[NUM_TRIANGLES];
      t2 = new SimpleTriangle[NUM_TRIANGLES];
      t3 = new SimpleTriangle[NUM_TRIANGLES];

      // Create static triangle sets

      t = new Triangle(cp1.x1, cp1.y1, cp2.x1, cp2.y1, cp3.x1, cp3.y1);
      t.convert();
      t1[0] = t.t1;
      t1[1] = t.t2;
 
      t = new Triangle(cp1.x1, cp1.y1, cp3.x1, cp3.y1, 0, ih-1);
      t.convert();
      t1[2] = t.t1;
      t1[3] = t.t2;
      
      t = new Triangle(cp2.x1, cp2.y1, cp3.x1, cp3.y1, iw-1, ih-1);
      t.convert();
      t1[4] = t.t1;
      t1[5] = t.t2;
      
      t = new Triangle(0, 0, cp1.x1, cp1.y1, 0, ih-1);
      t.convert();
      t1[6] = t.t1;
      t1[7] = t.t2;
      
      t = new Triangle(iw-1, 0, cp2.x1, cp2.y1, iw-1, ih-1);
      t.convert();
      t1[8] = t.t1;
      t1[9] = t.t2;
      
      t = new Triangle(iw-1, 0, cp1.x1, cp1.y1, cp2.x1, cp2.y1);
      t.convert();
      t1[10] = t.t1;
      t1[11] = t.t2;
     
      t1[12] = new SimpleTriangle(0, iw-1, 0, cp1.x1, cp1.y1);
      t1[13] = new SimpleTriangle(0, iw-1, ih-1, cp3.x1, cp3.y1);

      t = new Triangle(cp1.x2, cp1.y2, cp2.x2, cp2.y2, cp3.x2, cp3.y2);
      t.convert();
      t2[0] = t.t1;
      t2[1] = t.t2;
 
      t = new Triangle(cp1.x2, cp1.y2, cp3.x2, cp3.y2, 0, ih-1);
      t.convert();
      t2[2] = t.t1;
      t2[3] = t.t2;
      
      t = new Triangle(cp2.x2, cp2.y2, cp3.x2, cp3.y2, iw-1, ih-1);
      t.convert();
      t2[4] = t.t1;
      t2[5] = t.t2;
      
      t = new Triangle(0, 0, cp1.x2, cp1.y2, 0, ih-1);
      t.convert();
      t2[6] = t.t1;
      t2[7] = t.t2;
      
      t = new Triangle(iw-1, 0, cp2.x2, cp2.y2, iw-1, ih-1);
      t.convert();
      t2[8] = t.t1;
      t2[9] = t.t2;
      
      t = new Triangle(iw-1, 0, cp1.x2, cp1.y2, cp2.x2, cp2.y2);
      t.convert();
      t2[10] = t.t1;
      t2[11] = t.t2;
     
      t2[12] = new SimpleTriangle(0, iw-1, 0, cp1.x2, cp1.y2);
      t2[13] = new SimpleTriangle(0, iw-1, ih-1, cp3.x2, cp3.y2);
	 
      for (f = 1 ; f < (framecnt-1) ; f++)
      {
         int i, x1, y1, x2, y2, x3, y3;
	 double ratio;
    
	 drawClock("Frame "+f);

	 step  = new int[iw * ih];
         ratio = (double)f / (double)(framecnt-1);

         x1 = (int)Math.round((double)cp1.x1 + ((double)(cp1.x2 - cp1.x1) * ratio));
         y1 = (int)Math.round((double)cp1.y1 + ((double)(cp1.y2 - cp1.y1) * ratio));
         x2 = (int)Math.round((double)cp2.x1 + ((double)(cp2.x2 - cp2.x1) * ratio));
         y2 = (int)Math.round((double)cp2.y1 + ((double)(cp2.y2 - cp2.y1) * ratio));
         x3 = (int)Math.round((double)cp3.x1 + ((double)(cp3.x2 - cp3.x1) * ratio));
         y3 = (int)Math.round((double)cp3.y1 + ((double)(cp3.y2 - cp3.y1) * ratio));

         t = new Triangle(x1, y1, x2, y2, x3, y3);
         t.convert();
         t3[0] = t.t1;
         t3[1] = t.t2;
 
         t = new Triangle(x1, y1, x3, y3, 0, ih-1);
         t.convert();
         t3[2] = t.t1;
         t3[3] = t.t2;
      
         t = new Triangle(x2, y2, x3, y3, iw-1, ih-1);
         t.convert();
         t3[4] = t.t1;
         t3[5] = t.t2;
      
         t = new Triangle(0, 0, x1, y1, 0, ih-1);
         t.convert();
         t3[6] = t.t1;
         t3[7] = t.t2;
      
         t = new Triangle(iw-1, 0, x2, y2, iw-1, ih-1);
         t.convert();
         t3[8] = t.t1;
         t3[9] = t.t2;
      
         t = new Triangle(iw-1, 0, x1, y1, x2, y2);
         t.convert();
         t3[10] = t.t1;
         t3[11] = t.t2;
     
         t3[12] = new SimpleTriangle(0, iw-1, 0, x1, y1);
         t3[13] = new SimpleTriangle(0, iw-1, ih-1, x3, y3);
        
	 drawClock();

	 for (i = 0 ; i < NUM_TRIANGLES ; i+=2)
	 {
	    morph_triangles1(t1[i], t2[i], t3[i], from, to, step, iw, ratio);
	    morph_triangles2(t1[i+1],t2[i+1],t3[i+1],from, to, step, iw, ratio);
	    drawClock();
	 }

	 frames[f] = createImage(new MemoryImageSource(iw, ih, step, 0, iw));
	 drawClock();
      }

      t1 = null;
      t2 = null;
      t3 = null;
   }

   private void morph_triangles1(SimpleTriangle t1, SimpleTriangle t2, 
				 SimpleTriangle t3, int[] from, int[] to,
				 int[] step, int iw, double ratio)
   {
      int    x, y;
      double m11, m12, m21, m22, m31, m32;
      double x11, x12, x21, x22, x31, x32, y1, dy1, y2, dy2;

      m11 = (double)(t1.x3 - t1.x1) / (double)(t1.y3 - t1.y);
      m12 = (double)(t1.x3 - t1.x2) / (double)(t1.y3 - t1.y);
 
      m21 = (double)(t2.x3 - t2.x1) / (double)(t2.y3 - t2.y);
      m22 = (double)(t2.x3 - t2.x2) / (double)(t2.y3 - t2.y);
 
      m31 = (double)(t3.x3 - t3.x1) / (double)(t3.y3 - t3.y);
      m32 = (double)(t3.x3 - t3.x2) / (double)(t3.y3 - t3.y);
 
      x11 = (double)(t1.x1);
      x12 = (double)(t1.x2);
      y1  = (double)(t1.y);
      dy1 = (double)(t1.y3 - t1.y) / (double)(t3.y3 - t3.y);
 
      x21 = (double)(t2.x1);
      x22 = (double)(t2.x2);
      y2  = (double)(t2.y);
      dy2 = (double)(t2.y3 - t2.y) / (double)(t3.y3 - t3.y);
 
      x31 = (double)(t3.x1);
      x32 = (double)(t3.x2);
 
      for (y = t3.y ; y < t3.y3 ; y++)
      {
         for (x = (int)x31 ; x <= (int)x32 ; x++)
         {
	    int p1, p2, p3, a1, a2, a3, r1, r2, r3, g1, g2, g3, b1, b2, b3;

	    double fract = ((double)x - x31) / (x32 - x31);

	    p1 = from[((int)Math.round(y1) * iw) + (int)Math.round(x11 + (fract * (x12 - x11)))];
	    p2 = to  [((int)Math.round(y2) * iw) + (int)Math.round(x21 + (fract * (x22 - x21)))];

            a1 = (p1 >> 24) & 0xFF;
            r1 = (p1 >> 16) & 0xFF;
            g1 = (p1 >>  8) & 0xFF;
            b1 = (p1)       & 0xFF;
            a2 = (p2 >> 24) & 0xFF;
            r2 = (p2 >> 16) & 0xFF;
            g2 = (p2 >>  8) & 0xFF;
            b2 = (p2)       & 0xFF;
         
            a3 = (int)((double)a1 + ((double)(a2 - a1) * ratio));
            r3 = (int)((double)r1 + ((double)(r2 - r1) * ratio));
            g3 = (int)((double)g1 + ((double)(g2 - g1) * ratio));
            b3 = (int)((double)b1 + ((double)(b2 - b1) * ratio));

	    step[(y * iw) + x] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
         }
 
         x11 += m11;
         x12 += m12;
         x21 += m21;
         x22 += m22;
         x31 += m31;
         x32 += m32;
         y1  += dy1;
         y2  += dy2;
      }
   }

   private void morph_triangles2(SimpleTriangle t1, SimpleTriangle t2, 
				 SimpleTriangle t3, int[] from, int[] to,
				 int[] step, int iw, double ratio)
   {
      int    x, y;
      double m11, m12, m21, m22, m31, m32;
      double x11, x12, x21, x22, x31, x32, y1, dy1, y2, dy2;
 
      m11 = (double)(t1.x3 - t1.x1) / (double)(t1.y - t1.y3);
      m12 = (double)(t1.x3 - t1.x2) / (double)(t1.y - t1.y3);
 
      m21 = (double)(t2.x3 - t2.x1) / (double)(t2.y - t2.y3);
      m22 = (double)(t2.x3 - t2.x2) / (double)(t2.y - t2.y3);
 
      m31 = (double)(t3.x3 - t3.x1) / (double)(t3.y - t3.y3);
      m32 = (double)(t3.x3 - t3.x2) / (double)(t3.y - t3.y3);
 
      x11 = (double)(t1.x1);
      x12 = (double)(t1.x2);
      y1  = (double)(t1.y);
      dy1 = (double)(t1.y - t1.y3) / (double)(t3.y - t3.y3);
 
      x21 = (double)(t2.x1);
      x22 = (double)(t2.x2);
      y2  = (double)(t2.y);
      dy2 = (double)(t2.y - t2.y3) / (double)(t3.y - t3.y3);
 
      x31 = (double)(t3.x1);
      x32 = (double)(t3.x2);
 
      for (y = t3.y ; y > t3.y3 ; y--)
      {
         for (x = (int)x31 ; x <= (int)x32 ; x++)
         {
            int p1, p2, p3, a1, a2, a3, r1, r2, r3, g1, g2, g3, b1, b2, b3;

	    double fract = ((double)x - x31) / (x32 - x31);

	    p1 = from[((int)Math.round(y1) * iw) + (int)Math.round(x11 + (fract * (x12 - x11)))];
	    p2 = to  [((int)Math.round(y2) * iw) + (int)Math.round(x21 + (fract * (x22 - x21)))];

            a1 = (p1 >> 24) & 0xFF;
            r1 = (p1 >> 16) & 0xFF;
            g1 = (p1 >>  8) & 0xFF;
            b1 = (p1)       & 0xFF;
            a2 = (p2 >> 24) & 0xFF;
            r2 = (p2 >> 16) & 0xFF;
            g2 = (p2 >>  8) & 0xFF;
            b2 = (p2)       & 0xFF;
         
            a3 = (int)((double)a1 + ((double)(a2 - a1) * ratio));
            r3 = (int)((double)r1 + ((double)(r2 - r1) * ratio));
            g3 = (int)((double)g1 + ((double)(g2 - g1) * ratio));
            b3 = (int)((double)b1 + ((double)(b2 - b1) * ratio));

	    step[(y * iw) + x] = (a3 << 24) | (r3 << 16) | (g3 << 8) | b3;
         }
 
         x11 += m11;
         x12 += m12;
         x21 += m21;
         x22 += m22;
         x31 += m31;
         x32 += m32;
         y1  -= dy1;
         y2  -= dy2;
      }
   }
}
