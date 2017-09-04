<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script>
$(document).ready(function() {
	$('#addFile').click(function() {
		var fileIndex = $('#fileListTable tr').children().length;      
		$('#fileListTable').append('<tr><td>'+'<input type="file" name="multipartFile" required="required" />'+'</td></tr>');
    });     
});
</script>
<div id="content">
	<form action="upload" method="post" enctype="multipart/form-data">
	    <input id="addFile" type="button" value="파일추가" />&nbsp;&nbsp;<input type="submit" value="완료" /><input type="reset" value="취소"><br /><br />    
	    <table id="fileListTable">
        	<tr><td><input type="file" name="multipartFile" required="required" /></td></tr>        
    	</table>
	</form>
</div>