<%@page import="com.kim.demo4.member.memberDto"%>
<%@page import="com.kim.demo4.board.getAllBoardDto"%>
<%@page import="com.kim.demo4.stringEnums"%>
<%@page import="com.kim.demo4.board.boardDto"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<center>
<%@ include file="../common/top.jsp" %>
<%
memberDto dto=(memberDto)request.getAttribute("dto");

%>

<table style="width: 650px;">
	<thead>
	<tr>
		<th style="width: 330px; height:20px;" align="center">이메일</th>
		<th style="width: 80px; height:20px;" align="center">가입일</th>
		<th style="width: 120px; height:20px;" align="center">주소</th>
		<th style="width: 80px; height:20px;" align="center">성별</th>
	</tr>
	</thead>
	<tr>
		<td style="width: 40px; height:20px;" align="center"><hr/></td>
		<td style="width: 330px; height:20px;" align="center"><hr/></td>
		<td style="width: 80px; height:20px;" align="center"><hr/></td>
		<td style="width: 120px; height:20px;" align="center"><hr/></td>
		<td style="width: 80px; height:20px;" align="center"><hr/></td>
	</tr>
	<tr>
			<%
		if(dto!=null){
			%>
		<td style="width: 330px; height:40px;" align="center"><%=dto.getEmail() %></td>
		<td style="width: 80px; height:40px;" align="center"><%=dto.getCreated() %></td>
		<td style="width: 120px; height:40px;" align="center"><%=dto.getAddress() %></td>
		<td style="width: 80px; height:40px;" align="center"><%=dto.getGender() %></td>
		<%}
		%>
	</tr>



		
	<tr><td colspan=5><hr/></td></tr>
</table>

</center>
<script>


</script>