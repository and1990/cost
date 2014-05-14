<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link rel="shortcut icon" href="<%=basePath%>/image/ico.jpg" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/icon.css">

    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div data-options="region:'west',split:true,title:'系统菜单',iconCls:'icon-help'"
     style="width:248px;padding:5px; text-align:left;">
    <ul id="help_tree">
        <li data-options="id:null">
            <span>用户信息</span>
            <ul>
                <li data-options="id:'showUser'">用户信息</li>
            </ul>
        </li>
        <li data-options="id:null">
            <span>收支信息</span>
            <ul>
                <li data-options="id:'showIncome'">收入信息</li>
            </ul>
            <ul>
                <li data-options="id:'showAccount'">支出信息</li>
            </ul>
        </li>
        <li data-options="id:null">
            <span>流水账</span>
            <ul>
                <li data-options="id:'showStream'">流水账统计明细</li>
            </ul>
        </li>
        <li data-options="id:null">
            <span>数据分析</span>
            <ul>
                <li data-options="id:'showPie'">饼状图</li>
                <li data-options="id:'showColumn'">柱状图</li>
                <li data-options="id:'showLine'">线性图</li>
                <%--<li data-options="id:'showInvestLine'">投资分析</li>--%>
                <li data-options="id:'showStreamColumn'">流水账分析</li>
            </ul>
        </li>
    </ul>
</div>

<script type="text/javascript">
    $(function () {
        $('#help_tree').tree({
            checkbox: false,
            animate: true,
            lines: true,
            onClick: function (node) {
                if (node.id) {
                    var menuText = node.text;
                    if ($('#main_tabs').tabs('exists', menuText)) {
                        $('#main_tabs').tabs('select', menuText);
                    } else {
                        var url = '<%=basePath%>/' + node.id + '.do';
                        $('#main_tabs').tabs('add', {
                            title: menuText,
                            href: url,
                            closable: true
                        });
                    }
                } else {
                    $('#help_tree').tree('expand', node.target);
                }
            }
        });
    });
</script>
</body>
</html>