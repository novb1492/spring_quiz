function requestToServer(url,data,protocol){
    console.log('requestToServer');
	var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open(prorocol, url, true);
    xmlHttpRequest.setRequestHeader("Content-Type", "application/json");
    // 혹은 application/x-json
    xmlHttpRequest.send(data);
}
function getIdValue(id){
	return document.getElementById(id).value;
}