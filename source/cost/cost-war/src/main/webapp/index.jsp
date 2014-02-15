<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf8">
	<link rel="shortcut icon" href="<%=basePath%>/image/ico.jpg" type="image/x-icon"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>/easy-ui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/uploadify/uploadify.css">
	<link rel="stylesheet" type="text/css" href="js/lightbox/css/jquery.lightbox-0.5.css" media="screen"/>
	<script type="text/javascript" src="<%=basePath%>/js/uploadify/jquery.uploadify.min.js"></script>
	<%--<link rel="stylesheet" type="text/css" href="js/coin-slider/coin-slider-styles.css"/>--%>
	<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/easy-ui/jquery.easyui.min.js"></script>
	<%--<script type="text/javascript" src="js/coin-slider/coin-slider.min.js"></script>--%>
	<script type="text/javascript" src="js/lightbox/js/jquery.lightbox-0.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/cost.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/account.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/group.js"></script>
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
		/*$(document).ready(function ()
		 {
		 $('#coin-slider').coinslider({navigation: true, hoverPause: true });
		 });*/
		$(function ()
		{
			//$('a').lightBox({fixedNavigation: true, overlayOpacity: 0.2});
			$('#cc').combo({
				required: true,
				multiple: true,
				onShowPanel: editPanel
			});
		});
		function test()
		{
			$('.cc_class').combo('setText','123');
			alert(1);
			//$('#p').panel("open");
		}
		function editPanel()
		{
			$('.cc_class').combo("hidePanel");
		}
	</script>
</head>
<body>
<%--<div id='coin-slider'>
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
</div>--%>
<div>
	<%--<ul>
		<li><a href="image/login.jpg" class="lightbox">12</a></li>
		<li><a href="image/ico.jpg" class="lightbox">34</a></li>
	</ul>--%>
	<a href="image/login.jpg" class="lightbox">12</a>
	<a href="image/ico.jpg" class="lightbox">34</a>
</div>
<input id="cc" value="" class="cc_class">

<input type="button" onclick="test();"/>

<div id="p" class="easyui-panel" title="选择用户" style="width:600px;height:350px">
	<div id="user_data_dialog_north" style="height: 20px" data-options="region:'south',border:0"></div>
	<div id="user_data_dialog" class="easyui-layout" data-options="fit:true">
		<div id="user_data_dialog_west" style="width: 250px" data-options="region:'west',border:0">
			<div style="margin-left:100px">
				<table id="user_data_dialog_left" class="easyui-datagrid" style="width: 150px;height: 240px">
					<thead>
					<tr>
						<th data-options="field:'userName',width:150,align:'center'">用户</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>

		<div id="user_data_dialog_center" style="width: 100px" data-options="region:'center',border:0">
			<div style="margin-top:100px;text-align:center">
				<input type="button" value=">>" onclick="appendToGroup();"><br>
				<input type="button" value="<<" onclick="deleteFromGroup();">
			</div>
		</div>

		<div id="user_data_dialog_east" style="width: 250px" data-options="region:'east',border:0">
			<div id="">
				<table id="user_data_dialog_right" class="easyui-datagrid" style="width:150px;height: 240px">
					<thead>
					<tr>
						<th data-options="field:'userName',width:150,align:'center'">组员</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>


		<div id="user_data_dialog_south" style="height: 70px" data-options="region:'south',border:0">
			<div style="text-align:center">
				<hr color="lightBlue">
				<input type="button" value="确定" onclick="groupSave();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="取消" onclick="groupCancel();">
			</div>
		</div>
	</div>
</div>
</body>
</html>
