<%@ page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8"%>
<% 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath(); 
%>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/registDialog.css">
<div id="user_regist_div" class="user_regist_div">
 	<form>
 		<div class="1">
			<div class="left"><span>用户名:</span></div>	 		
	 		<div class="center"><input type="text" name="userName" id="userName" class="input_text"/></div>
	 		<div class="right"><span id="user_name_message"></span></div>
 		</div>
 		<div class="1">
			<div class="left"><span>登录名:</span></div>	 		
	 		<div class="center"><input type="text" name="loginName" id="loginName" class="input_text"/></div>
	 		<div class="right"><span id="login_name_message"></span></div>
 		</div>
 		<div> 
 			<div class="left"><span>密码:</span></div>
 			<div class="center"><input type="password" name="password" id="password" class="input_text"/></div>
 			<div class="right"><span id="password_message"></span></div>
 		</div>
 		<div>
 			<div class="left"><span>确认密码:</span></div>				 		
	 		<div class="center"><input type="password" name="password" id="confirmPassword" class="input_text"/></div>
	 		<div class="right"><span id="confirm_password_message"></span></div>
 		</div>
 		<div>
 			<div class="left"><span>年龄:</span></div>				 		
	 		<div class="center"><input type="text" name="userAge" id="userAge" class="input_text"/></div>
	 		<div class="right"><span id="user_age_message"></span></div>
 		</div>
 		<div>
 			<div class="left"><span>地址:</span></div>				 		
	 		<div class="center"><input type="text" name="userAddress" id="userAddress" class="input_text"/></div>
	 		<div class="right"><span id="user_address_message"></span></div>
 		</div>
 		<div>
 			<div class="left"><span>邮箱:</span></div>				 		
	 		<div class="center"><input type="text" name="userEmail" id="userEmail" class="input_text"/></div>
	 		<div class="right"><span id="user_email_message"></span></div>
 		</div>
 		<div>
 			<div class="left"><span>备注:</span></div>				 		
	 		<div class="center"><input type="text" name="userRemark" id="userRemark" class="input_text"/></div>
	 		<div class="right"><span id="user_remark_message"></span></div>
 		</div>
 		<div class="button_div">
 			<input type="submit" value="重&nbsp;&nbsp;&nbsp;&nbsp;置"class="input_button"/>
 			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 			<input type="submit" value="注&nbsp;&nbsp;&nbsp;&nbsp;册"class="input_button"/>
 		</div>
 	</form>
 </div> 