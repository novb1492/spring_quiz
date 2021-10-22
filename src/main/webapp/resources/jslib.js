function test(){
	alert('test');
	var xmlHttpRequest = new XMLHttpRequest();
    let data=JSON.stringify({
		 "email":"test",
         "pwd":"pwd"
	}); 
    xmlHttpRequest.open("POST", '/demo4/user/crud/insert', true);
    xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
    // 혹은 application/x-json

    xmlHttpRequest.send(data);
}