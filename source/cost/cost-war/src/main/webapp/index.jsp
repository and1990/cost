<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
	<title>file upload</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="js/coin-slider/coin-slider-styles.css"/>
	<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/uploadify/jquery.uploadify.min.js"></script>
	<script type="text/javascript" src="js/coin-slider/coin-slider.min.js"></script>
	<style type="text/css">
		.uploadify-button {

			text-decoration: underline;
			color: blue
		}

		.uploadify:hover .uploadify-button {

			text-decoration: underline;
			color: green
		}
	</style>
	<script type="text/javascript">
		$(document).ready(function ()
		{
			$('#coin-slider').coinslider({navigation: true, hoverPause: true });
		});
	</script>
</head>
<body>
<div id='coin-slider'>
	<a href="img01_url" target="_blank">
		<img src='image/login.jpg'>
		<span>
			Description for img01
		</span>
	</a>
	<a href="imgN_url">
		<img src='image/ico.jpg'>
		<span>
			Description for imgN
		</span>
	</a>
</div>
</body>
</html>
