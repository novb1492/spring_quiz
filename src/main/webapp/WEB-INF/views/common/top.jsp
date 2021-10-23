<%@ page contentType="text/html; charset=UTF-8"%>
<%
String email=(String)session.getAttribute("email");
boolean flag=false;
if(email==null){
	flag=false;
}else{
	flag=true;
}
%>
<style type="text/css">



a:link{color:black;font-family:sans-serif;text-decoration:none;}
a:visited{color:black;font-family:sans-serif;text-decoration:none;}
a:hover{color:#cc3300; font-weight:bold; }
a:active{color:#ff00cc; text-decoration:underline; }
-->
</style>
 <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="/demo4/resources/jslib.js"></script>
<table width=800>
	<tr><td align="center" colspan=5><h1>CARE Lab</h1></td></tr>
	<tr align="right">
		<td width=600></td>
		<td><a href="index.jsp">홈</a></td>
		<%if(flag){
		%>
		<td><%=email %></td>
		<td><a href="/demo4/user/logout">로그아웃</a></td>
		<td><a href="index.jsp">게시판</a></td>
		<% }else{
			%>
			<td><a href="/demo4/loginPage">로그인</a></td>
			<td><a href="/demo4/joinPage">회원가입</a></td>
			<td><a href="index.jsp">게시판</a></td>
		<% }
			%>
		
	
	</tr>
	<tr><td align="center" colspan=5><hr/></td></tr>
</table>

