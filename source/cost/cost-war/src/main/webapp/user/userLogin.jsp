<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<% 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath(); 
%>
<base href='<%=basePath %>'>
<html>
	<head>
		<title>登录——COST信息系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/login.css">
		<script type="text/javascript" src="<%=basePath %>/easy-ui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>/easy-ui/jquery.easyui.min.js"></script>
	</head>
	<body>
		  <div id="user_login_header" class="user_login_header">
		  	<h1>欢迎登录</h1>
		  </div>
		  <div id="user_login_div" class="user_login_div">
		  	 <div id="user_login_image" class="user_login_image">
			 	<img src="<%=basePath %>/image/login.jpg">
		  	 </div> 
			 <div id="user_login_input" class="user_login_input">
			 	<form method="post" id="user_login_form">
			 		<br>
			 		<div><span>用户名/邮箱/手机号</span></div>
			 		<div><input type="text" name="loginName" id="loginName" class="input_text"/></div><br>
			 		<div><span>密码</span></div>
			 		<div><input type="password" name="password" id="password" class="input_text"/></div><br>
			 		<div>
			 			<input type="checkbox"/><span>记住密码</span> &nbsp;&nbsp;&nbsp;&nbsp;
			 			<a>忘记密码？</a> &nbsp;&nbsp;&nbsp;&nbsp;
			 			<a>快速注册</a>
			 		</div>
			 	</form>
		 		<div>
		 			<input type="button" value="登 &nbsp;&nbsp;&nbsp;&nbsp;录" class="input_button" onclick="userLogin();"/>
		 		</div>
		  	 </div> 
		  </div> 
	</body>
	<script type="text/javascript">
		function userLogin(){
			var loginUrl = "<%=basePath%>/rest/auth/userLogin?"+$('#user_login_form').serialize();
			 $.ajax({
		        type: "POST",
		        url:loginUrl,
		        success: function(resultData){
		        	window.location.href="<%=basePath%>/main.jsp";
		        },
		        error:function(msg){
		        	alert(msg);
		        }
		    });
		}
	</script>
</html>