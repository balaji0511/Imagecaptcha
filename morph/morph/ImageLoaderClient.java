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
// Instantiate an object, call addImage() for every image to    //
// track, then call waitForAll() which will block until the     //
// load is complete. The object passed to the constructor is    //
// assumed to implement the ImageLoaderClient interface, and    //
// its imageLoaderUpdate() method will be called at intervals   //
// until the load(s) complete, or an error occurs.              //
//                                                              //
//////////////////////////////////////////////////////////////////

package morph;

public interface ImageLoaderClient
{
   public static int ILC_READY    = 0;
   public static int ILC_LOADING  = 1;
   public static int ILC_ERROR    = 2;
   public static int ILC_ABORT    = 3;
   public static int ILC_COMPLETE = 4;

   public void imageLoaderUpdate(int status);
}
