<%@ page contentType="text/html; charset=UTF-8"%>

<center>

<table style="width: 650px; ">
	<tr>
		<td style="width: 80px; height:40px;" align="right">작성자</td>
		<td style="width: 570px; height:40px;">
		
		</td>
	</tr>
	<tr>
		<td  style="width: 80px; height:40px;" align="right">제 목</td>
		<td style="width: 570px; height:40px;">
			<input type=text name='title' placeholder="촤대 30자입니다" style="width: 500px; "/> 
		</td>
	</tr>
	<tr>
		<td colspan=2 align="right"><textarea style="width: 650px; height: 300px" id="editor"></textarea></td>
	</tr>
	<tr>
		<td align='center' height=40 colspan=2>
			<input type=submit value='글쓰기' style="width: 120px; "/>
			<input type=reset value='취소' style="width: 120px; "/>	 
		</td>
	</tr>
</table>
</center>
 <form action="/demo4/img" method="POST" enctype="multipart/form-data">
        <input type="file" name="report" /><br/>
        <input type="submit" value="sss" />
    </form>
<script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>
<script>


</script>