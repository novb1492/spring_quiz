var result;
function requestToServer(url,data){
   $.ajax({
       type: 'POST',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function requestPutToServer(url,data){
   $.ajax({
       type: 'PUT',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
       async: false,
       xhrFields: {withCredentials: true},
       success: function(response) {
           result=response;
       },
   
   });
   console.log(result);
   console.log('통신직후');
   return result;
   
}
function getIdValue(id){
	return document.getElementById(id).value;
}
function sendSns(phoneOrEmail,kind,detail){
	console.log('sendSns');
	 var data=JSON.stringify({
			"phoneOrEmail":phoneOrEmail,
			"kind":kind,
			"detail":detail
	});
	requestToServer('/demo4/sns/send',data);
}
