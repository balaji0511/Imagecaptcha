//////////////////////////////////////////////////////////////////
//                                                              //
// ImageLoader 0.9 - A Java class to wait on an image load      //
//                   and provide status updates                 //
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
// Usage:                                                       //
//                                                              //
// Instantiate an object, call addImage() for every image to    //
// track, then call waitForAll() which will block until the     //
// load is complete. The object passed to the constructor is    //
// assumed to implement the ImageLoaderClient interface, and    //
// its imageLoaderUpdate() method will be called at intervals   //
// until the load(s) complete, or an error occurs.              //
//                                                              //
//////////////////////////////////////////////////////////////////

package morph;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Vector;
import java.util.Enumeration;

class ImageRecord
{
   public Image image;
   public int   status;

   ImageRecord(Image i)
   {
      image  = i;
      status = ImageLoaderClient.ILC_READY; 
   }
}

public class ImageLoader implements ImageObserver
{
   static int DEFAULT_POLLING_INTERVAL = 1000; // ms

   private ImageLoaderClient ilc;
   private Component         com;
   private Vector            records;
   private int               pollint;
   private boolean           error = false;

   public ImageLoader(ImageLoaderClient ILC, int interval)
   {
      ilc     = ILC;
      com     = (Component)ilc;
      pollint = interval;
      records = new Vector();
   }

   public ImageLoader(ImageLoaderClient ILC)
   {
      ilc     = ILC;
      com     = (Component)ilc;
      pollint = DEFAULT_POLLING_INTERVAL;
      records = new Vector();
   }

   public synchronized int addImage(Image image)
   {
      records.addElement(new ImageRecord(image));
      return (records.size() - 1);
   }

   public synchronized boolean waitForAll()
   {
      for (Enumeration e = records.elements() ; e.hasMoreElements() ; )
      {
	 ImageRecord i = (ImageRecord)(e.nextElement());
	 
	 if (i.status == ImageLoaderClient.ILC_LOADING)
	    continue;

	 ilc.imageLoaderUpdate(ImageLoaderClient.ILC_LOADING);

	 i.status = ImageLoaderClient.ILC_LOADING;

         if (com.prepareImage(i.image, this))
            i.status = ImageLoaderClient.ILC_COMPLETE;
      }

      while (!error)
      {
	 if (loaded())
	 {
            ilc.imageLoaderUpdate(ImageLoaderClient.ILC_COMPLETE);
            return (true);
	 }

	 ilc.imageLoaderUpdate(ImageLoaderClient.ILC_LOADING);

	 try { wait(pollint); }
         catch (InterruptedException e) { }
      }

      ilc.imageLoaderUpdate(ImageLoaderClient.ILC_ERROR);
      return (false);
   }

   private boolean loaded()
   {
      for (Enumeration e = records.elements() ; e.hasMoreElements() ; )
      {
	 ImageRecord i = (ImageRecord)(e.nextElement());

         if (i.status != ImageLoaderClient.ILC_COMPLETE)
	    return (false);
      }

      return (true);
   }

   public synchronized boolean imageUpdate(Image image, int infoFlags,
                                           int x, int y, int width, int height)
   {
      for (Enumeration e = records.elements() ; e.hasMoreElements() ; )
      {
	 ImageRecord i = (ImageRecord)(e.nextElement());
         
	 if (image != i.image)
	    continue;

         if ((infoFlags & ERROR) != 0)
         {
	    i.status = ImageLoaderClient.ILC_ERROR;
            showError("Error loading image");
	    return (false);
         }
      
         if ((infoFlags & ABORT) != 0)
         {
	    i.status = ImageLoaderClient.ILC_ABORT;
            showError("Image Aborted");
	    return (false);
         }
      
         if ((infoFlags & ALLBITS) != 0)
         {
	    i.status = ImageLoaderClient.ILC_COMPLETE;
	    notifyAll();
	    return (false);
         }

         return (true);
      }

      showError("Unknown image in imageUpdate");
      return(false);
   }
 
   private void showError(Exception e)
   {
      System.err.println("ImageLoader: error: " + e);
      error = true;
   }

   private void showError(String s)
   {
      System.err.println("ImageLoader: error: " + s);
      error = true;
   }
}
