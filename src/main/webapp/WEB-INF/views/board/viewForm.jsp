<%@page import="com.kim.demo4.board.boardDto"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
boardDto boardDto=(boardDto)request.getAttribute("dto");

%>
<center>
<%@ include file="../common/top.jsp" %>
<table style="width: 650px; ">
<%if(boardDto!=null){
%>
<tr>
		<td style="width: 300px; height:40px;" valign="middle"><h2><%=boardDto.getTitle() %></h2></td>
		<td style="width: 350px; height:40px;" align="right" valign="bottom"><%=boardDto.getCreated() %></td>
	</tr>
	<tr>
		<td colspan=2><hr/></td>
	</tr>
	<tr>
		<td  style="width: 300px; height:40px;" valign="top"><%=boardDto.getEmail() %></td>
	</tr>
	<tr>
		<td colspan=2 style="width: 650px; height: 300px" valign="top">
		<pre>
			<%=boardDto.getText()%>
		</pre>
		</td>
	</tr>
	<tr>
		<td colspan=2><hr/></td>
	</tr>
	<tr>
		<td colspan=2 align="right">
			<input type=button style="width: 60px; " value='글쓰기'/> 
			<input type=button style="width: 60px; " value='수정'/>
			<input type=button style="width: 60px; " value='삭제'/>
			<input type=button style="width: 60px; " onclick="back()"  value='목록'/>
		</td>
	</tr>
<%}else{
%>
존재하지 않는 게시물
<%}%>

	
</table>
</center>
<script>
function back() {
	var page=getParam('inPage');
	var keyword=getParam('keyword');
	page=page*1;
	location.href='/demo4/boardPage?page='+page+'&keyword='+keyword;
}
</script>