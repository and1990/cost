<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
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
    </ul>
</div>


<%--<div class="easyui-accordion" data-options="fit:true,border:false" id="main_left_accordion">
    <!-- 用户信息 -->
    <div title="用户信息" data-options="selected:true" style="padding: 10px;">
        <ul class="easyui-tree" data-options="animate:true" id="user_tree">
            <li>
                <a href="#" style="text-decoration:none;" onclick="openTab('userData','用户信息')">用户信息</a>
            </li>
            <li>
                <a href="#" style="text-decoration:none;" onclick="openTab('userChange','人员变更')">人员变更</a>
            </li>
        </ul>
    </div>
    <!-- 费用报销 -->
    <div title="我的消费" style="padding: 10px;">
        <ul class="easyui-tree" data-options="animate:true" id="account_tree">
            <li>
                <a style="text-decoration:none;" href="#" onclick="openTab('myAccountData','我的消费')">我的消费</a>
            </li>
            <li>
                <a style="text-decoration:none;" href="#" onclick="openTab('groupAccountData','组消费')">组消费</a>
            </li>
        </ul>
    </div>
</div>--%>

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


    //第二种方式
    /*function openTab(menuId, menuText) {
     var url = undefined;
     if ("userData" === menuId) {
     url = "user/user.jsp";
     } else if ("userChange" === menuId) {
     url = "group/group.jsp";
     } else if ("myAccountData" === menuId) {
     url = "account/account.jsp";
     } else if ("groupAccountData" === menuId) {
     //TODO
     }
     if ($('#main_tabs').tabs('exists', menuText)) {
     $('#main_tabs').tabs('select', menuText);
     } else {
     $('#main_tabs').tabs('add', {
     title: menuText,
     href: url,
     closable: true
     });
     }
     }*/
</script>