<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/top.jsp" %>
<center>
<table>
	<tr>
		<td>아이디</td>
		<td><input type="email" id='email' placeholder='email 입력'/></td>
	</tr>
	<tr>
		<td>패스워드</td>
		<td><input type="password" id='pwd' placeholder='PW 입력'/></td>
	</tr>
	<tr>
		<td colspan=2 align='center'>
			<input type=button onclick="login()" value='로그인' style="width: 86px; "/>
			<input type=reset value='취소' style="width: 86px; "/> 
		</td>
	</tr>
</table>
<input type="button" value="카카오 로그인" onclick="callKakaoLogin()">
</center>
<script>
function login() {
	 var email=getIdValue('email');
	var pwd=getIdValue('pwd');	
	 let data=JSON.stringify({
		 "email":email,
         "pwd":pwd,
	});
	 var reuslt=requestToServer('/demo4/login',data);
	 if(reuslt.flag){
		 location.href='/demo4/board';
	 }else{
		 alert(result.message);
	 }
	
}
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