package honey;

import java.io.*;

import javax.servlet.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;

public class GetKnowledge extends HttpServlet
{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) 
					throws ServletException,IOException 
	{       
       	try
		{
//				res.setContentType("application/msword");
//			res.setHeader("Content-Disposition:","inline");
		String filename = req.getParameter("filename");

HttpSession session=req.getSession(false);
String userid=session.getAttribute("userid").toString();
Object oSeedBlock=session.getAttribute("SeedBlock");
int SeedBlock=Integer.parseInt(oSeedBlock.toString());
String cpath=getServletConfig().getServletContext().getRealPath("/");
cpath=cpath+"\\CloudStore\\";
String bpath=getServletConfig().getServletContext().getRealPath("/");
bpath=bpath+"\\RemoteBackup\\";




try{

FileInputStream fin=new FileInputStream(cpath+"\\"+filename);
byte b[]=new byte[fin.available()];
fin.read(b);
fin.close();
String tempC=new String(b);
String signature=String.valueOf(tempC.hashCode());
honey.DBConnection conn=new honey.DBConnection();
Connection con=conn.getConnection();
PreparedStatement ps1=con.prepareStatement("select * from knowledge where know_file=?");
ps1.setString(1,filename);
ResultSet rs=ps1.executeQuery();
if(rs.next())
{
String osig=rs.getString(8);
String sponser=rs.getString(5);
if(!osig.equals(signature))
{
if(userid.equals(sponser))
{
getServletContext().log("\n The User tried to retrieve a file   in "+cpath+",But it is currepted , requested from  from remoteIP "+req.getRemoteAddr()+" on "+new java.util.Date()+"\n");
res.sendRedirect("Knowledge_hacked_err.jsp?file="+filename);
}
else
{
res.sendRedirect("Knowledge_hacked_err1.jsp?userid="+userid+"&file="+filename);
}
}
}



}catch(Exception eee){eee.printStackTrace();}

res.sendRedirect("CloudStore//"+filename);

/*		
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
*/
getServletContext().log("\n The User retrieved a file --"+cpath+", attempted  from remoteIP "+req.getRemoteAddr()+" on "+new java.util.Date()+"\n");

//fis.close();
//res.getOutputStream().write(b);			
			
		}catch(Exception e2){
		System.out.println("Er:GetKnowledge");
           //e2.printStackTrace();
		}	
	}
	
}
