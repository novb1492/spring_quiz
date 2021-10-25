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
		<td style="width: 120px; height:40px;" align="center">
			<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호찾기">
			우편번호:<input type=text id='zipcode' value="<%=dto.getAddress().split(",")[0] %>" readonly="readonly"/> 
			주소:<input type=text id='addr1' readonly="readonly" value="<%=dto.getAddress().split(",")[1] %>"/> 
			상세주소:<input type=text id='addr2' value="<%=dto.getAddress().split(",")[2] %>"/> 
			<input type="button" onclick="changeAddress()" value="주소변경">
		</td>
		<td style="width: 80px; height:40px;" align="center"><%=dto.getGender() %></td>
		<%}
		%>
	</tr>



		
	<tr><td colspan=5><hr/></td></tr>
</table>

</center>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function changeAddress() {
	var postcode=getIdValue('zipcode');
	var address=getIdValue('addr1');
		var detailAddress=getIdValue('addr2');
		let data=JSON.stringify({
	       		"postcode":postcode,
	       			"address":address,
	       				"detailAddress":detailAddress,
	       				"detail":"address",
	       				<%if(dto.getRole().equals("admin")) 
		       				{%>
		       				"email":'<%=dto.getEmail() %>'
		       			<%	}
	       				
	       				%>
	});
	 var reuslt=requestPutToServer('/demo4/user/crud/change?detail=address',data);
	 console.log(result);
	 alert(result.message);
	 if(!result.flag){
		 location.reload();
	 }
}
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
               // document.getElementById("sample6_extraAddress").value = extraAddr;
            
            } else {
                //document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("addr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("addr2").focus();
        }
    }).open();
}

</script>