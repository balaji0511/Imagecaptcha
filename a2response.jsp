<html>

<head>
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>New Page 1</title>
</head>

<body>

<%@page import='java.sql.*'%>
<jsp:useBean id='conn' class='honey.DBConnection' scope='application'/>
<%
String userid=request.getParameter("txtUser");
String password=request.getParameter("txtPass");
String code=request.getParameter("a");
//String phone=request.getParameter("txtPhone");
//String email=request.getParameter("txtEmail");
//String gender=request.getParameter("gender");
String tempstr=(String)session.getAttribute("temp");
System.out.println(tempstr);
int rf=0;
String strOpt1="",username="",phone="",email="",gender="";
try{
Connection con=conn.getConnection();
PreparedStatement ps1=con.prepareStatement("SELECT   USERID,PASSWORD,USERNAME,PHONENO,EMAIL,GENDER,opt FROM UserDetails WHERE userid=? and password=? and opt=?");

ps1.setString(1,userid);
ps1.setString(2,password);
ps1.setString(3,Integer.toHexString(code.hashCode()));

ResultSet rs=ps1.executeQuery();
if(rs.next())
{
username=rs.getString(3);
phone=rs.getString(4);
email=rs.getString(5);
gender=rs.getString(6);
strOpt1=rs.getString(7);
}
con.close();
}catch(Exception exx){exx.printStackTrace();}

if(strOpt1=="")
{
%>
<jsp:forward page='login_fail1.jsp'/>
<%
}
else 
{
	%>
<jsp:forward page='success_log.jsp'/>
<%

tempstr=tempstr.replace("[","").replace("]","");
strOpt1=strOpt1.replace("[","").replace("]","");

out.println("<br>strOpt1->"+strOpt1);
out.println("<br>tempstr->"+tempstr);

tempstr=tempstr.trim();

String su[]=strOpt1.split(",");
String tu[]=tempstr.split(",");

int s[]=new int[10];
int t[]=new int[10];

for(int i=0;i<=9;i++)
{
s[i]=Integer.parseInt(su[i].trim());
t[i]=Integer.parseInt(tu[i].trim());
}


for(int i=0;i<=9;i++)
{
	for(int j=0;j<=9;j++)
	{
		if(t[i]==s[j])
			{
				t[i]=j;
				//out.println("<br>"+t[i]+"-"+j);
				break;
			}
	}
}
tempstr=java.util.Arrays.toString(t);
tempstr=tempstr.replace("[","").replace("]","");

out.println("<br>tempstr->"+tempstr);

String st[]=tempstr.split(",");

int c0=Integer.parseInt(st[0].trim());
int a0=Integer.parseInt(st[1].trim());
int c1=Integer.parseInt(st[2].trim());
int a1=Integer.parseInt(st[3].trim());
int c2=Integer.parseInt(st[4].trim());
int a2=Integer.parseInt(st[5].trim());
int c3=Integer.parseInt(st[6].trim());
int a3=Integer.parseInt(st[7].trim());
int c4=Integer.parseInt(st[8].trim());
int a4=Integer.parseInt(st[9].trim());


int tp=t[9];
out.println("<br>opt9->"+tp);

int s0=c0+a0+tp;
int s1=c1+a1+tp;
int s2=c2+a2+tp;;
int s3=c3+a3+tp;;
int s4=c4+a4+tp;;

String pc=s0+","+s1+","+s2+","+s3+","+s4;
session.setAttribute("pc",pc);

out.println("<br>PC->"+pc);

if(pc.trim().equals(code.trim()))
{
System.out.println("Successfully Loged in");
%>
<jsp:forward page='success_log.jsp'/>
<%

}
else 
{
%>
<jsp:forward page='login_fail1.jsp'/>
<%

System.out.println("Sorry .....");
}

}




%>


</body>

</html>