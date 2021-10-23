<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/top.jsp" %>
<center>
<table>
	<tr>
		<td>아이디</td>
		<td><input type=text name='id' placeholder='ID 입력'/></td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td><input type=password name='pw' placeholder='PW 입력'/></td>
	</tr>
	<tr>
		<td colspan=2 align='center'>
			<input type=submit value='로그인' style="width: 86px; "/>
			<input type=reset value='취소' style="width: 86px; "/> 
		</td>
	</tr>
</table>
<input type="button" value="카카오 로그인" onclick="callKakaoLogin()">
</center>
<script>
function  callKakaoLogin(){
    var url='/demo4/kakao/showPage?scope=login';
    var result=requestGetToServer(url);
    	if(result.flag){ 
            this.child=window.open(result.message,'width=500','height=500');
        }else{
            alert(result.message);
        }
  
}
</script>