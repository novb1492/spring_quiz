<%@page import="com.kim.demo4.member.memberDto"%>

<%@page import="com.kim.demo4.stringEnums"%>

<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<center>
<%@ include file="../common/top.jsp" %>
<%
List<memberDto>dtos=(List<memberDto>)request.getAttribute(stringEnums.dtos.getValue());

int nowPage=(int)request.getAttribute("page");
int totalPage=(int)request.getAttribute("totalPage");
String keyword=request.getParameter("keyword");
if(keyword==null){
	keyword="";
}
%>

<table style="width: 650px;">
	<thead>
	<tr>
		<th style="width: 330px; height:20px;" align="center">이메일</th>
		<th style="width: 120px; height:20px;" align="center">가입일</th>
		<th style="width: 120px; height:20px;" align="center">성별</th>
	</tr>
	</thead>



			<%if(dtos!=null){
		for(memberDto m:dtos){
			%>
			<tr>
			<%
				if((flag&&email.equals(m.getEmail()))||m.getRole().equals("admin")){
					%>
					<td style="width: 330px; height:40px;" align="center"><a href="/demo4/showMemberPage?detail=one&email=<%=m.getEmail()%>&inPage=<%=nowPage%>&keyword=<%=keyword%>"><%=m.getEmail()%></a></td>
				<%}else{	
				%>	
					<td style="width: 330px; height:40px;" align="center"><%=m.getEmail()%></td>
				<%}
			
			%>
		<td style="width: 120px; height:40px;" align="center"><%=m.getCreated() %></td>
		<td style="width: 80px; height:40px;" align="center"><%=m.getGender() %></td>
	</tr>
		<%}
	}else{
		%>
		<tr>
		<td style="width: 330px; height:40px;" align="center">검색결과없음</td>
		</tr>
	<% 	}
		%>
	
	
	
	

</table>
<input type="button" id="next" value="next" onclick="movePage(1)">
<%=nowPage %>/<%=totalPage %>
<input type="button" id="before" value="before" onclick="movePage(-1)">
<table>
<tr>
<td>
	<select>
		<option>전체</option>
		<option>이메일</option>
	</select>
	<input type=text id='search'/>
	<input type=button name='searchBtn' onclick="search()" value='검색' style="width: 80px; "/>
</td>
</tr>
</table>
</center>
<script>
if(<%=nowPage %>==1){
	disabledById('before',true);
}
if(<%=nowPage %>==<%=totalPage %>){
	disabledById('next',true);
}
function movePage(num) {
	var page=getParam('page');
	var keyword=getParam('keyword');
	page=page*1;
	location.href='/demo4/membersPage?detail=all&page='+(page+num)+'&keyword='+keyword;
}
function search() {
	var email=getIdValue('search');
	location.href='/demo4/membersPage?detail=all&page=1&keyword='+email;
}

</script>