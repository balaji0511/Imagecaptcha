<%@ page import='java.awt.*,java.net.*,java.awt.image.*,javax.imageio.ImageIO,java.io.*,jntu.*'%>
<%@ include file='opt.jsp'%>

<style type="text/css">
input {
width: 125px;
font-family: Verdana;
font-size: 8pt;
}
</style>
</head>
<body bgcolor="#F4FFE4">
<table width="103%" border="0" cellspacing="0" cellpadding="0">
  <tr bgcolor="#D5EDB3">
    <td height="51" colspan="2" bgcolor="#0D4961"><img src="images/header1.jpg" alt="s" width="400" height="139" /><img src="images/header1.jpg" alt="s" width="400" height="139" /><img src="images/header1.jpg" alt="s" width="400" height="139" /><img src="images/header1.jpg" alt="s" width="400" height="139" /></td>
  </tr>


  <tr>
    <td colspan="2" bgcolor="red"><img src="images/header.jpg" alt="" width="1" height="2" border="0" /></td>
  </tr>

  <tr>
    <td height="10" colspan="2" bgcolor="yellow">&nbsp;</td>

<tr><td><a href='Login.jsp' >Login Authentication</a> | 
<a href='Registration.jsp' >New User Registration</a>
  </td></tr>

  <tr>
    <td width="100%" align="left" valign="top" bgcolor="#A4C2C2">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#B4C2C2">
  <tr>
    <td width="5%" bgcolor="#a4c2c2">&nbsp;</td>
    <td height="5%" colspan="0" align="left" valign="top" bgcolor="#a4c2c2" class="navText" id="navigation">
	<%--<jsp:include page="adminoptions.html"/></td>--%>
    <td width="5%" bgcolor="#a4c2c2">&nbsp;</td>
  </tr>
</table>
</td>
  </tr>



<%
int opt[]=getOPT();
int temp[]=new int[10];
temp[0]=opt[0];temp[1]=opt[5];
temp[2]=opt[1];temp[3]=opt[6];
temp[4]=opt[2];temp[5]=opt[7];
temp[6]=opt[3];temp[7]=opt[8];
temp[8]=opt[4];temp[9]=opt[9];

session.setAttribute("temp",java.util.Arrays.toString(temp));


%>


<form name="form1" method="get" action="a2response.jsp">
  <center>

  <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
<tr><td></td><td width="25%" align=right><br><h2>User Login  </h2><br></td><td><br><h2>&nbsp;Authentication</h2></br></td></tr>
    <tr>
      <td width="25%">&nbsp;</td>
      <td width="25%"><font color="black"><b>User ID</b></font></td>
      <td width="25%"><b><input type="text" name="txtUser" size="20"></b></td>
      <td width="25%">&nbsp;</td>
    </tr>
    <tr>
      <td width="25%">&nbsp;</td>
      <td width="25%"><font color="black"><b>Password</b></font></td>
      <td width="25%"><b><input type="password" name="txtPass" size="20"></b></td>
      <td width="25%">&nbsp;</td>
    </tr>
    


    <tr>
      <td width="25%" align=center>&nbsp;</td>

<table>
<tr>
<td width="15%"></td>
<td></td>

<td><img src="images/<%=temp[0]%>.jpg" height=100 width=100>+</td>
<td><img src="images/<%=temp[1]%>.jpg" height=100 width=100>|</td>
<td><img src="images/<%=temp[2]%>.jpg" height=100 width=100>+</td>
<td><img src="images/<%=temp[3]%>.jpg" height=100 width=100>|</td>
<td><img src="images/<%=temp[4]%>.jpg" height=100 width=100>+</td>
<td><img src="images/<%=temp[5]%>.jpg" height=100 width=100>|</td>
<td><img src="images/<%=temp[6]%>.jpg" height=100 width=100>+</td>
<td><img src="images/<%=temp[7]%>.jpg" height=100 width=100>|</td>
<td><img src="images/<%=temp[8]%>.jpg" height=100 width=100>+</td>
<td><img src="images/<%=temp[9]%>.jpg" height=100 width=100><br>

</tr>
<tr>
<td>
<td><td><td><td><td><td>
<td><center>+</center><br><img src="images/<%=temp[9]%>.jpg" height=100 width=100><br>
</tr>
<tr>

<td align=center><b></b></td>
<td align=center><b></b></td>
</tr>

</table>
<table>
<tr>
<td width=25%>
      
<%
session.setAttribute("opt9",temp[9]);
%>
<td width="30%"><td><td><td>Enter <td>Your
<td>Passcode<td><b><input type="text" name="t" ></b><td><b><input type="submit" value="Login" name="B1"OnClick='return f1()'></b></td>
    </tr>
  </table>  
</form>
<script language='javaScript'>
function f1()
{
   if(form1.txtUser.value=="")
   {
     alert("user name must be entered");
     return false; 
   }
   if(form1.txtPass.value=="")
   {
     alert("password must be entered");
     return false;;
   }
   return true;
}
</script> 
     
     
     
     
     
     





     
     
<tr>
    <td height="25" colspan="2" bgcolor="#0D4961" align="center"><font color="#ffffff"><span class="style1">&copy;All rights reserved</span></font></td>
  </tr>
</table>

</body>
</html>
