<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<% 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8">
		<link rel="shortcut icon" href="<%=basePath%>/image/ico.jpg" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/uploadify/uploadify.css">
		<script type="text/javascript" src="<%=basePath%>/js/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/cost.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/group.js"></script>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true">
			<div id="sysMenuInfo"
				data-options="region:'west',title:'系统功能',split:true,href:'<%=basePath %>/main/sysMenu.jsp'"
				style="width: 205px;"></div>
			<div id="mainInfo" data-options="region:'center',title:'业务信息'">
				<div class="easyui-tabs"
					data-options="border:false,plain:true,fit:true" id="main_tabs">
					<div title="账单信息" data-options="href:'<%=basePath %>/account/accountData.jsp'" style="padding: 2px"></div>
					<div title="组信息" data-options="href:'<%=basePath %>/group/group.jsp'" style="padding: 2px"></div>
					<div title="用户信息" data-options="href:'<%=basePath %>/user/userData.jsp'" style="padding: 2px"></div>
				</div>
			</div>
			<div id="noticeInfo"
				data-options="region:'east',title:'公告信息',split:true,href:'<%=basePath %>/main/notice.jsp'"
				style="width: 205px; padding: 2px"></div>
		</div>
	</body>
</html>