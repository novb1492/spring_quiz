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
function requestToServer2(url,data){
   $.ajax({
       type: 'POST',
       url: url,
       dataType : "json",
       data: data,
       contentType: "application/json; charset:UTF-8",
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
function requestDelteToServer(url){
   $.ajax({
       type: 'DELETE',
       url: url,
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
function requestGetToServer(url){
   $.ajax({
       type: 'GET',
       url: url,
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
function disabledById(id,flag){
	document.getElementById(id).disabled=flag;	
}
function sendSns(phoneOrEmail,kind,detail){
	console.log('sendSns');
	 var data=JSON.stringify({
			"phoneOrEmail":phoneOrEmail,
			"kind":kind,
			"detail":detail
	});
	var result=requestToServer('/demo4/sns/send',data);
	alert(result.message);
	if(result.flag){
		disabledById('sendNum',false);
	}
}
 function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
       var temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}
function getRadioValue(name){
	 var obj_length = document.getElementsByName(name).length;
        for (var i=0; i<obj_length; i++) {
            if (document.getElementsByName(name)[i].checked == true) {
               return document.getElementsByName(name)[i].value;
            }
        }
}

