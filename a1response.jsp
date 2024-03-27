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
String username=request.getParameter("txtName");
String phone=request.getParameter("txtPhone");
String email=request.getParameter("txtEmail");
String gender=request.getParameter("gender");
//String opt=(String)session.getAttribute("opt");
String opt=request.getParameter("a");
System.out.println(opt);
int rf=0;
try{
Connection con=conn.getConnection();
PreparedStatement ps1=con.prepareStatement("insert into UserDetails(USERID,PASSWORD,USERNAME,PHONENO,EMAIL,GENDER,opt) values(?,?,?,?,?,?,?)");

ps1.setString(1,userid);
ps1.setString(2,password);
ps1.setString(3,username);
ps1.setString(4,phone);
ps1.setString(5,email);
ps1.setString(6,gender);
ps1.setString(7,Integer.toHexString(opt.hashCode()));

rf=ps1.executeUpdate();
con.close();
}catch(Exception exx){out.println(exx.getMessage());}

if(rf>0)
{
%>
<jsp:forward page='success_reg.jsp'/>
<%
}

%>


</body>

</html>