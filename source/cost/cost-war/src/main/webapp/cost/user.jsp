<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
</head>
<body>
<div id="user_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="user_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="user_data_table">
            <thead>
            <tr>
                <th data-options="field:'userName',width:80,align:'center',editor:'text'">用户名</th>
                <th data-options="field:'loginName',width:80,align:'center',editor:'text'">登录名</th>
                <th data-options="field:'password',width:80,align:'center',editor:'text'">密码</th>
                <th data-options="field:'userAge',width:80,align:'center',editor:'numberbox'">年龄</th>
                <th data-options="field:'userAddress',width:120,align:'center',editor:'text'">地址</th>
                <th data-options="field:'userEmail',width:120,align:'center',editor:'text'">邮箱</th>
                <th data-options="field:'isAdminName',width:120,align:'center',formatter:showIsAdminText,
                          editor:{
                              type:'combobox',
                              options:{
                                  data:isAdminData,
                                  valueField:'value',
                                  textField:'name'
                              }}"
                        >是否管理员
                </th>
                <th data-options="field:'userStatusName',width:120,align:'center',formatter:showStatusText,
                          editor:{
                              type:'combobox',
                              options:{
                                  data:statusData,
                                  valueField:'value',
                                  textField:'name'
                              }}"
                        >用户状态
                </th>
                <th data-options="field:'userRemark',width:120,align:'center',editor:'text'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="user_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <%--<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData('#user_data_table', '<%=basePath%>/addUser.do');">增加</a>--%>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addUser();">增加</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editData('#user_data_table', '<%=basePath%>/modifyUser.do');">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="removeData('#user_data_table', '<%=basePath%>/deleteUser.do');">删除</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
           onclick="undoData('#user_data_table');">撤销</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
           onclick="saveData('#user_data_table');">保存</a>
    </div>
    <div>
        <table style="font-size: 12">
            <tr>
                <td>
                    <span>用户名：</span><input class="textbox" style="width:100px;">
                </td>
                <td>
                    <span>管理员：</span>
                    <select class="easyui-combobox" name="status" style="width:100px;">
                        <option value="0">全部</option>
                        <option value="1">是</option>
                        <option value="2">否</option>
                    </select>
                </td>
                <td>
                    <span>状态：</span>
                    <select class="easyui-combobox" name="status" style="width:100px;">
                        <option value="0">全部</option>
                        <option value="1">可用</option>
                        <option value="2">禁用</option>
                    </select>
                </td>
                <td>
                    创建时间从: <input class="easyui-datebox" style="width: 80px">
                    到: <input class="easyui-datebox" style="width: 80px">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="user_panel" class="easyui-dialog" title="增加用户"
     style="width:500px;height:430px;padding:2px;" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="user_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <td>用户名：</td>
                        <td>
                            <input class="text" id="userName" name="userVO.userName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>登录名:</td>
                        <td>
                            <input class="text" id="loginName" name="userVO.loginName"/>
                        </td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td>
                            <input class="password" id="password" name="userVO.password"/>
                        </td>
                    </tr>
                    <tr>
                        <td>确认密码:</td>
                        <td>
                            <input class="password" id="rePassword" name="userVO.password"/>
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
            singleSelect: true,
            pagination: true,
            toolbar: '#user_tool_bar',

            onBeforeLoad: function () {
                $("#user_data_table").datagrid("clearSelections");
            },
            onLoadSuccess: function (data) {
                $("#user_data_table").datagrid("clearSelections");
            }

        });

        $('#user_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });
    });

    //更新选中行
    function updateRow() {
        var rowCount = $('#dg').datagrid('getRows').length;
        for (var i = 0; i < rowCount; i++) {
            $('#user_data_table').datagrid('updateRow', {
                index: i,
                row: {action: ''}
            });
        }
    }

    function onClickRow(index) {
        if (actionType != undefined) {
            $('#user_data_table').datagrid('selectRow', rowIndex);
        } else {
            rowIndex = index;
        }
    }

    //获取查询参数
    function query() {
        var queryData = {
            "userName": $("#userName").val(),
            "status": $("#userStatus").combobox('getValue'),
            "level": $("#userLevel").combobox('getValue')
        };
        $("#user_data_table").datagrid({
                    queryParams: queryData,
                    pageNumber: 1
                }, 'load'
        );
    }

    //增加用户
    function addUser() {
        $("#user_panel").dialog("open");

        $("#userName").validatebox({
            required: true,
            missingMessage: "用户名不能为空"
        });
        $("#loginName").validatebox({
            required: true,
            missingMessage: "登录名不能为空"
        });
        $("#password").validatebox({
            required: true,
            missingMessage: "密码不能为空"
        });
        $("#rePassword").validatebox({
            required: true,
            missingMessage: "确认密码不能为空"
        });
    }

    //提交表单
    function submitForm() {
        $.messager.progress();
        $('#user_form').form('submit', {
                    url: '<%=basePath%>/addUser.do',
                    onSubmit: function () {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                            return false;
                        }
                        var password = $("#password").val();
                        var rePassword = $("#rePassword").val();
                        var passwordSame = password === rePassword;
                        if (!passwordSame) {
                            $.messager.progress('close');
                            return false;
                        }
                        return true;
                    },
                    success: function () {
                        $.messager.progress('close');
                    }
                }
        );
    }

</script>
</body>
</html>