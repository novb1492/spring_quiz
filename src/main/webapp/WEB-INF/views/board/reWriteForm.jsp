<%@page import="com.kim.demo4.board.boardDto"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%boardDto boardDto=(boardDto)request.getAttribute("dto"); %>
<center>
<%@ include file="../common/top.jsp" %>
<table style="width: 650px; ">
	<tr>
		<td style="width: 80px; height:40px;" align="right">작성자</td>
		<td style="width: 570px; height:40px;">
		<%=email %>
		</td>
	</tr>
	<tr>
		<td  style="width: 80px; height:40px;" align="right">제 목</td>
		<td style="width: 570px; height:40px;">
			<input type=text id='title' placeholder="촤대 30자입니다" style="width: 500px; " value="<%= boardDto.getTitle()%>"/> 
		</td>
	</tr>
	<tr>
		<td colspan=2 align="right"><textarea style="width: 650px; height: 300px" id="editor"></textarea></td>
	</tr>
	<tr>
		<td align='center' height=40 colspan=2>
			<input type="button" value='수정' onclick="update()" style="width: 120px; "/>
			<input type=reset value='취소' onclick="cancel()" style="width: 120px; "/>	 
		</td>
	</tr>
</table>
<input type="button" value="sds" onclick="cancleArticle()">
</center>
<script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>
<script>
let editor;
var flag=true;
function cancel() {
	location.href='/demo4/boardPage?page=1'
}
function update() {
	 let data=JSON.stringify({
		 "text":editor.getData(),
		 "title":getIdValue('title'),
		 "id":<%= boardDto.getId()%>
	});
	var result=requestPutToServer('/demo4/board/crud/update',data);
	alert(result.message);
	if(result.flag){
		location.href='/demo4/articlePage?bid=<%=boardDto.getId()%>&inPage=1&keyword=';
	}
}
class MyUploadAdapter {
    constructor(props) {
        // CKEditor 5's FileLoader instance.
      this.loader = props;
      // URL where to send files.
      this.url = '/demo4/img';
    }

    // Starts the upload process.
    upload() {
        return new Promise((resolve, reject) => {
            this._initRequest();
            this._initListeners(resolve, reject);
            this._sendRequest();
        } );
    }

    // Aborts the upload process.
    abort() {
        if ( this.xhr ) {
            this.xhr.abort();
        }
    }

    // Example implementation using XMLHttpRequest.
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();

        xhr.open('POST', this.url, true);
        xhr.responseType = 'json';

    }

    // Initializes XMLHttpRequest listeners.
    _initListeners( resolve, reject ) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = 'Couldn\'t upload file:' + ` ${ loader.file.name }.`;

        xhr.addEventListener( 'error', () => reject( genericErrorText ) );
        xhr.addEventListener( 'abort', () => reject() );
        xhr.addEventListener( 'load', () => {
            const response = xhr.response;
            if ( !response || response.error ) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            // If the upload is successful, resolve the upload promise with an object containing
            // at least the "default" URL, pointing to the image on the server.
            resolve({
                default: response.url
            });
        } );

        if ( xhr.upload ) {
            xhr.upload.addEventListener( 'progress', evt => {
                if ( evt.lengthComputable ) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            } );
        }
    }

    // Prepares the data and sends the request.
    _sendRequest() {
        const data = new FormData();

        this.loader.file.then(result => {
          data.append('upload', result);
          //this.xhr.setRequestHeader('Content-type', 'multipart/form-data');
          this.xhr.send(data);
          }
        )
    }

}
function MyCustomUploadAdapterPlugin( editor ) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
    // Configure the URL to the upload script in your back-end here!
    return new MyUploadAdapter( loader );
    };
}

window.onload = function () {
	ClassicEditor
	.create( document.querySelector('#editor'), {
	        extraPlugins: [ MyCustomUploadAdapterPlugin ],

	        // ...
	    } )
		.then( newEditor  => {
	        console.log( 'Editor was initialized', newEditor  );
	        editor = newEditor ;
	        editor.setData('<%=boardDto.getText()%>');
	    } )
		.catch( error => {
		   
	} );
};
window.onbeforeunload = function(e) {
	if(flag){
		cancleArticle();
	}
};
function cancleArticle() {
console.log('a');
 let data=JSON.stringify({
	 "originText":'<%=boardDto.getText()%>',
	 "text":editor.getData(),
	 "detail":'update'
});
 console.log(data);
 requestToServer2('/demo4/deleteimg',data);

}
</script>