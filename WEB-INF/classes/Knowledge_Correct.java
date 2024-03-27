package skar;

import java.io.*;

import javax.servlet.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;

public class Knowledge_Correct extends HttpServlet
{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
					throws ServletException,IOException 
	{       
       	try
		{
		res.setContentType("application/msword");
			res.setHeader("Content-Disposition:","inline");
		String filename = req.getParameter("filename");
ResourceBundle resource=ResourceBundle.getBundle("application");
String path=resource.getString("kcenter");
String path1 = (path+File.separator+filename).toLowerCase();
String spath=resource.getString("Rcenter");
String spath1 = (spath+File.separator+filename).toLowerCase();
System.out.println("-000000000-"+spath1);
System.out.println("-000000000-"+path1);

FileInputStream fi=new FileInputStream(spath1);
FileOutputStream fo=new FileOutputStream(path1);
byte bb[]=new byte[fi.available()];
fi.read(bb);
fo.write(bb);
fi.close();
fo.close();
 
try{

FileInputStream fin=new FileInputStream(path1);
byte b[]=new byte[fin.available()];
fin.read(b);
String tempC=new String(b);
String signature=String.valueOf(tempC.hashCode());
honey.DBConnection conn=new honey.DBConnection();
Connection con=conn.getConnection();
PreparedStatement ps2=con.prepareStatement("update knowledge set ksignature=? where know_file=?");
ps2.setString(2,filename);
ps2.setString(1,signature);
//out.println(fname+"."+version+"."+kname+"."+signature);
ps2.executeUpdate();


fin.close();
}catch(Exception eee){System.out.println(eee);}


		
if(path1.endsWith(".doc"))
				res.setContentType("application/msword");
else if(path1.endsWith(".txt"))
				res.setContentType("text/plain");
else if(path1.endsWith(".pdf"))
				res.setContentType("application/pdf");
else if(path1.endsWith(".ppt"))
				res.setContentType("application/powerpoint");
else if(path1.endsWith(".xml"))
				res.setContentType("text/xml");
else if(path1.endsWith(".mp3"))
				res.setContentType("audio/mpeg");
else if(path1.endsWith(".jpg"))
				res.setContentType("image/jpeg");
else if(path1.endsWith(".gif"))
				res.setContentType("image/gif");

else
			res.setContentType("binary/"+path1.substring(path1.indexOf(".")+1,path1.length()));

res.addHeader("Content-Disposition", "attachment; filename="+ path);
		File file=new File(path1);
			FileInputStream fis=new FileInputStream(file);
byte b[]=new byte[fis.available()];
fis.read(b);
fis.close();

getServletContext().log("\n The User retrieved a Knoledge --"+path+", after correction attempted  from remoteIP "+req.getRemoteAddr()+" on "+new java.util.Date()+"\n");


res.getOutputStream().write(b);			
			
		}catch(Exception e2){
			System.out.println(e2);
		}	
	}
	
}
