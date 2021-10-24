<%@page import="com.kim.demo4.board.getAllBoardDto"%>
<%@page import="com.kim.demo4.stringEnums"%>
<%@page import="com.kim.demo4.board.boardDto"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<center>
<%@ include file="../common/top.jsp" %>
<%
List<boardDto>dtos=(List<boardDto>)request.getAttribute(stringEnums.dtos.getValue());
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
		<th style="width: 330px; height:20px;" align="center">제 목</th>
		<th style="width: 80px; height:20px;" align="center">작성자</th>
		<th style="width: 120px; height:20px;" align="center">작성일</th>
		<th style="width: 80px; height:20px;" align="center">조회수</th>
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
		<td style="width: 330px; height:40px;" align="center">자주 이용해 주세요</td>
		<td style="width: 80px; height:40px;" align="center">관리자</td>
		<td style="width: 120px; height:40px;" align="center">20xx.xx.xx</td>
		<td style="width: 80px; height:40px;" align="center">1</td>
	</tr>
	<tr>
		<td style="width: 330px; height:40px;" align="center">게시판 확인</td>
		<td style="width: 80px; height:40px;" align="center">관리자</td>
		<td style="width: 120px; height:40px;" align="center">20xx.xx.xx</td>
		<td style="width: 80px; height:40px;" align="center">10</td>
	</tr>

	<%if(dtos!=null){
		for(boardDto b:dtos){
			%>
			<tr>
		<td style="width: 330px; height:40px;" align="center"><a href="/demo4/articlePage?bid=<%=b.getId()%>&inPage=<%=nowPage%>&keyword=<%=keyword%>"><%=b.getTitle() %></a></td>
		<td style="width: 80px; height:40px;" align="center"><%=b.getEmail() %></td>
		<td style="width: 120px; height:40px;" align="center"><%=b.getCreated() %></td>
		<td style="width: 80px; height:40px;" align="center"><%=b.getHit() %></td>
	</tr>
		<%}
	}else{
		%>
		<tr>
		<td style="width: 330px; height:40px;" align="center">검색결과없음</td>
		</tr>
	<% 	}
		%>
	

	
	
	<tr><td colspan=5><hr/></td></tr>
	<tr>

		<td colspan=3 align="right">
			<%if(flag){
			%>
			<input type="button" value='글쓰기' onclick="goWritePage()" style="width: 100px; "/>
			<%}%>
			
			
		</td>
	</tr>
	<tr><td colspan=5><hr/></td></tr>
</table>
<input type="button" id="next" value="next" onclick="movePage(1)">
<%=nowPage %>/<%=totalPage %>
<input type="button" id="before" value="before" onclick="movePage(-1)">
<table>
<tr>
<td>
	<select>
		<option>전체</option>
		<option>제목</option>
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
	location.href='/demo4/boardPage?page='+(page+num)+'&keyword='+keyword;
}
function search() {
	var title=getIdValue('search');
	location.href='/demo4/boardPage?page=1&keyword='+title;
}
function goWritePage() {
	location.href='/demo4/writePage';
}
</script>