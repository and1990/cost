<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link rel="shortcut icon" href="<%=basePath%>/image/ico.jpg" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/third/uploadify/uploadify.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/third/lightbox/css/jquery.lightbox-0.5.css"
          media="screen"/>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/lightbox/js/jquery.lightbox-0.5.js"></script>
</head>
<body>
<div id="main_layout" class="easyui-layout" data-options="fit:true">
    <!--头信息-->
    <div id="headInfo" data-options="region:'north',split:true" style="height: 35px;background:#E7F0FF">
        <div style="font-family: Microsoft YaHei;font-size: 15px;">
            <span>欢迎你，${sessionScope.userName}</span>
            <span>|</span>
            <span><a href="<%=basePath%>/modifyPassword.do" style="text-decoration: none">修改密码</a></span>
            <span>|</span>
            <span><a href="<%=basePath%>/loginOut.do" style="text-decoration: none">退出系统</a></span>
        </div>
    </div>

    <!--系统菜单信息-->
    <div id="sysMenuInfo" data-options="region:'west',title:'系统功能',split:true,href:'<%=basePath %>/cost/menu.jsp'"
         style="width: 205px;">
    </div>

    <!--主页面信息-->
    <div id="mainInfo" data-options="region:'center',title:'业务信息'">
        <div class="easyui-tabs"
             data-options="border:false,plain:true,fit:true" id="main_tabs">
            <div title="账单信息" data-options="href:'<%=basePath %>/cost/account.do'" style="padding: 2px"></div>
            <div title="用户信息" data-options="href:'<%=basePath %>/cost/user.do'" style="padding: 2px"></div>
            <div title="组信息" data-options="href:'<%=basePath %>/cost/group.do'" style="padding: 2px"></div>
        </div>
    </div>

    <!--公告信息-->
    <div id="noticeInfo"
         data-options="region:'east',title:'公告信息',split:true,href:'<%=basePath %>/cost/notice.do'"
         style="width: 205px; padding: 2px">
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#main_layout').layout('collapse', 'east');
    });
</script>

</body>
</html>