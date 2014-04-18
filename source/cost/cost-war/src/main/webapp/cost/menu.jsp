<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head></head>
<body>
<div data-options="region:'west',split:true,title:'系统菜单',iconCls:'icon-help'"
     style="width:248px;padding:5px; text-align:left;">
    <ul id="help_tree">
        <li data-options="id:null">
            <span>用户信息</span>
            <ul>
                <li data-options="id:'user'">用户信息</li>
                <li data-options="id:'group'">组员信息</li>
            </ul>
        </li>
        <li data-options="id:null">
            <span>消费信息</span>
            <ul>
                <li data-options="id:'account'">消费信息</li>
                <li data-options="id:'groupAccount'">组消费</li>
            </ul>
        </li>
        <li data-options="id:null">
            <span>数据分析</span>
            <ul>
                <li data-options="id:'column '">柱状图</li>
                <li data-options="id:'pie'">饼状图</li>
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
                        $('#main_tabs').tabs('add', {
                            title: menuText,
                            href: '<%=basePath%>/' + node.id + '.do',
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