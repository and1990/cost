<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
%>
<html>
	<head>
		<title>file upload</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/uploadify/uploadify.css">
		<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/uploadify/jquery.uploadify.min.js"></script>
		<style type="text/css">
			.uploadify-button {

				text-decoration:underline;
				color:blue
			}
			.uploadify:hover .uploadify-button {

				text-decoration:underline;
				color:green
			}
		</style>
		<script type="text/javascript">
			$(function()
			{
				$('#file_upload').uploadify({
					'swf'      : '<%=basePath%>/js/uploadify/uploadify.swf',
					'uploader' : '<%=basePath%>/account/fileUpload',
					'cancelImg' : '<%=basePath%>/js/uploadify/uploadify-cancel.png',
					'buttonText' : '上传',
					'auto':true,
					'removeCompleted': false,
					'fileTypeDesc': '选择文件',
					'fileTypeExts': '*.gif; *.jpg; *.png',
					'method' : 'post',
					'formData':{'key':'value'},
					'onUploadSuccess' : function(file,data,response){
						alert("save data : " + data);
					}
				});
			});
		</script>
	</head>
	<body>
		<input type="file" name="file_upload" id="file_upload" />
		<a href="javascript:$('#file_upload').uploadify('upload','*')">Upload Files</a>
	</body>
</html>
