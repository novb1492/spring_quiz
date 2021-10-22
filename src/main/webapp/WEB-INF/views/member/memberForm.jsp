<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/top.jsp" %>
<center>
<table>
	<tr>
		<td align='right' height=40>E-Mail</td>
		<td>
			<input type=text id='email' placeholder='E-Mail 입력'/> 
		</td>
		<td colspan="2"><input type="button" onclick="send()" value="인증번호 전송"></td>
		이름:<input type=text id='name' placeholder='이름을입력'/>
	</tr>
	<tr>
		<td align='right' height=40>패스워드</td>
		<td>
			<input type=text id='pwd' placeholder='pw 입력'/> 
		</td>
		<td align='right'>패스워드 확인</td>
		<td>
			<input type=text id='pwd2' placeholder='pw 입력'/> 
		</td>
	</tr>
	<tr>
		<td align='right'>인증번호</td>
		<td>
			<input type=text id='authNum' placeholder='인증번호 입력'/> 
		</td>
		<td colspan="2"><input type="button" onclick="confrimNum()" value="인증번호 확인"></td>
	</tr>
	<tr>
		<td align='right'>우편번호</td>
		<td>
			<input type=text id='zipcode' readonly="readonly"/> 
		</td>
		<td colspan="2"><input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 검색"></td>
	</tr>
	<tr>
		<td align='right'>주소</td>
		<td colspan="3">
			<input type=text id='addr1' readonly="readonly" style="width: 475px; "/> 
		</td>
	</tr>
	<tr>
		<td align='right'>상세주소</td>
		<td colspan="3">
			<input type=text id='addr2' style="width: 475px; "/> 
		</td>
	</tr>
	<tr>
		<td align='right' width=120>성 별</td>
		<td colspan="3">
			<input type=radio id='gender' value='n' checked="checked"/>선택 안함
			<input type=radio id='gender' value='m' />남자
			<input type=radio id='gender' value='w' />여자 
		</td>
	</tr>
	
	<tr>
		<td align='center' height=40 colspan=4>
			<input type=button value='회원 가입' onclick="insert()" style="width: 120px; "/>
			<input type=reset value='취소' style="width: 120px; "/>	 
		</td>
	</tr>
</table>
</center>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function confrimNum() {
	 var num=getIdValue('authNum');
		 let data=JSON.stringify({
				 "num":num,
		      	"kind":'email',
		      	"detail":'confrim'
		});
	var result=requestPutToServer('/demo4/sns/confrim',data);
}
function send() {
	 var email=getIdValue('email'); 
	sendSns(email,'email','confrim');
}
function insert() {
	 var email=getIdValue('email');
		var pwd=getIdValue('pwd');	
		var pwd2=getIdValue('pwd2');
			var postcode=getIdValue('zipcode');
				var address=getIdValue('addr1');
					var detailAddress=getIdValue('addr2');
						var gender='남자';
						var name=getIdValue('name');
		 let data=JSON.stringify({
				 "email":email,
		         "pwd":pwd,
		       	"pwd2":pwd2,
		       		"postcode":postcode,
		       			"address":address,
		       				"detailAddress":detailAddress,
		       					"gender":gender,
		       					"name":name
		        	 
		});
		 var reuslt=requestToServer('/demo4/user/crud/insert',data);
		 console.log(result);
		 alert(reuslt.message);
		 if(reuslt.flag){
			 location.href='/demo4/loginPage';
		 	return;
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