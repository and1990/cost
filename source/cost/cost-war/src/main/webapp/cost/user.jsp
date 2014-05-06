<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
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
<div id="user_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="user_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="user_data_table">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'userName',width:80,align:'center',editor:'text'">用户名</th>
                <th data-options="field:'loginName',width:80,align:'center',editor:'text'">登录名</th>
                <th data-options="field:'password',width:80,align:'center',editor:'text',hidden:true">密码</th>
                <th data-options="field:'userAge',width:80,align:'center',editor:'numberbox'">年龄</th>
                <th data-options="field:'userAddress',width:120,align:'center',editor:'text'">地址</th>
                <th data-options="field:'userEmail',width:120,align:'center',editor:'text'">邮箱</th>
                <th data-options="field:'userTypeName',width:120,align:'center'">用户类型</th>
                <th data-options="field:'userStatusName',width:120,align:'center'">用户状态</th>
                <th data-options="field:'userRemark',width:120,align:'center',editor:'text'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="user_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addUser();">增加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyUser();">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteUser();">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
           onclick="modifyUserStatus(2);">启用</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-tag-red" plain="true"
           onclick="modifyUserStatus(1);">禁用</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportUserToExcel();">导出Excel</a>
    </div>
    <div>
        <form id="user_filter_form" method="post">
            <table style="font-size: 12">
                <tr>
                    <td>
                        <span>用户名：</span>
                        <input class="text" name="userVO.userName" style="width:100px;"/>
                    </td>
                    <td>
                        <span>用户类型：</span>
                        <select class="easyui-combobox" name="userVO.userType" style="width:100px;">
                            <option value="0">全部</option>
                            <option value="1">普通用户</option>
                            <option value="2">管理员</option>
                        </select>
                    </td>
                    <td>
                        <span>状态：</span>
                        <select class="easyui-combobox" name="userVO.userStatus" style="width:100px;">
                            <option value="0">全部</option>
                            <option value="1">可用</option>
                            <option value="2">禁用</option>
                        </select>
                    </td>
                    <td>
                        创建时间从: <input class="Wdate" id="startTime" name="userVO.startTime" style="width: 100px"
                                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\');}'})">
                        到: <input class="Wdate" id="endTime" name="userVO.endTime" style="width: 100px"
                                  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\');}'})">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getUserByFilter();">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div id="user_dialog" class="easyui-dialog"
     style="width:500px;height:430px;padding:2px;" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="user_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="userId" name="userVO.userId"/>
                        <input type="hidden" id="userStatus" name="userVO.userStatus"/>
                        <input type="hidden" id="userType" name="userVO.userType"/>
                    </tr>
                    <tr>
                        <td>用户名：</td>
                        <td>
                            <input class="text easyui-validatebox" id="userName"
                                   name="userVO.userName" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>登录名:</td>
                        <td>
                            <input class="text easyui-validatebox" id="loginName"
                                   name="userVO.loginName" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr id="tr_password">
                        <td>密码:</td>
                        <td>
                            <input type="password" class="easyui-validatebox" id="password"
                                   name="userVO.password" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr id="tr_repassword">
                        <td>确认密码:</td>
                        <td>
                            <input type="password" class="easyui-validatebox" id="repassword"
                                   data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>年龄:</td>
                        <td>
                            <input class="text" id="userAge" name="userVO.userAge"/>
                        </td>
                    </tr>
                    <tr>
                        <td>地址:</td>
                        <td>
                            <input class="text" id="userAddress" name="userVO.userAddress"/>
                        </td>
                    </tr>
                    <tr>
                        <td>邮箱:</td>
                        <td>
                            <input class="text" id="userEmail" name="userVO.userEmail"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="text" id="userRemark" name="userVO.userRemark"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#user_form').form('clear');">取消</a>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$(function () {
    $('#user_data_table').datagrid({
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,
        loadMsg: '数据装载中......',
        url: '<%=basePath%>/getUserByFilter.do',
        idField: 'userId',
        fit: true,
        fitColumns: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        pagination: true,
        toolbar: '#user_tool_bar'
    });

    $('#user_data_table').datagrid('getPager').pagination({
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
    });
});

//获取查询参数
function getUserByFilter() {
    var queryData = $("#user_filter_form").serializeJson();
    console.info(queryData);
    $("#user_data_table").datagrid(
            {
                queryParams: queryData,
                pageNumber: 1
            }, 'load'
    );
}
(function ($) {
    $.fn.serializeJson = function () {
        var serializeObj = {};
        $(this.serializeArray()).each(function () {
            serializeObj[this.name] = this.value;
        });
        return serializeObj;
    };
})(jQuery);

//增加用户
function addUser() {
    //设置标题
    $('#user_dialog').dialog({ title: '增加用户信息'});
    //初始化界面
    $("#userId").val("");
    $("#userStatus").val("");
    $("#userType").val("");
    $("#userName").val("");
    $("#loginName").val("");
    $("#password").val("");
    $("#repassword").val("");
    $("#userAge").val("");
    $("#userAddress").val("");
    $("#userEmail").val("");
    $("#userRemark").val("");
    //打开弹出框
    $("#user_dialog").dialog("open");
    //设置action、url值，1代表增加
    $("#action").val(1);
    $("#url").val('<%=basePath%>/addUser.do');
}

//修改用户
function modifyUser() {
    var rowDataArr = $("#user_data_table").datagrid("getChecked");
    if (rowDataArr == undefined || rowDataArr.length == 0) {
        alert("请选择数据");
        return;
    }
    if (rowDataArr.length > 1) {
        alert("请选择一条数据");
        return;
    }
    //隐藏密码输入框
    $("#tr_password").hide();
    $("#tr_repassword").hide();

    //设置标题
    $('#user_dialog').dialog({ title: '修改用户信息'});
    //打开弹出框
    $("#user_dialog").dialog("open");
    //设置action值，2代表修改
    $("#action").val(2);
    $("#url").val('<%=basePath%>/modifyUser.do');

    //填充数据
    var rowData = rowDataArr[0];
    $("#userId").val(rowData.userId);
    $("#userStatus").val(rowData.userStatus);
    $("#userType").val(rowData.userType);
    $("#userName").val(rowData.userName);
    $("#loginName").val(rowData.loginName);
    $("#userAge").val(rowData.userAge);
    $("#userAddress").val(rowData.userAddress);
    $("#userEmail").val(rowData.userEmail);
    $("#userRemark").val(rowData.userRemark);
}

//删除用户
function deleteUser() {
    var rowDataArr = $("#user_data_table").datagrid("getChecked");
    if (rowDataArr == undefined || rowDataArr.length == 0) {
        alert("请选择数据");
        return;
    }
    if (rowDataArr.length > 1) {
        alert("请选择一条数据");
        return;
    }
    if (window.confirm("确定删除？")) {
        var rowData = rowDataArr[0];
        var url = "<%=basePath%>/deleteUser.do?userVO.userId=" + rowData.userId;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#user_data_table').datagrid('reload');
                    }
                }
        );
    }
}

//更新用户状态
function modifyUserStatus(userStatus) {
    var rowDataArr = $("#user_data_table").datagrid("getChecked");
    if (rowDataArr == undefined || rowDataArr.length == 0) {
        alert("请勾选数据");
        return;
    }
    var promotion = userStatus == 1 ? "确定禁用选择的用户？" : "确定启用选择的用户？";
    if (window.confirm(promotion)) {
        var userIds = getCheckedUserIds();
        var url = "<%=basePath%>/modifyUserStatus.do?userIds=" + userIds + "&userVO.userStatus=" + userStatus;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#user_data_table').datagrid('reload');
                    }
                }
        );
    }
}

//提交表单
function submitForm() {
    //打开进度条
    $.messager.progress();
    var action = $("#action").val();
    var url = $("#url").val();
    $('#user_form').form('submit', {
                url: url,
                onSubmit: function () {
                    if (action == 1) {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                            return false;
                        }
                        var password = $("#password").val();
                        var repassword = $("#repassword").val();
                        var passwordSame = password === repassword;
                        if (!passwordSame) {
                            $.messager.progress('close');
                            return false;
                        }
                    }
                    return true;
                },
                success: function () {
                    $.messager.progress('close');
                    $('#user_form').form('clear');
                    $("#user_dialog").dialog("close");
                    $('#user_data_table').datagrid('reload');
                    if (action == 2) {
                        $("#password").show();
                        $("#repassword").show();
                    }
                }
            }
    );
}

//获取选择的用户ID
function getCheckedUserIds() {
    var userIds = undefined;
    var rowData = $("#user_data_table").datagrid("getChecked");
    for (var i = 0; i < rowData.length; i++) {
        var userId = rowData[i].userId;
        userIds = userIds == undefined ? userId : userIds + "," + userId;
    }
    return userIds;
}

//导出excel
function exportUserToExcel() {
    window.location.href = "<%=basePath%>/exportUserToExcel.do";
}
</script>
</body>
</html>