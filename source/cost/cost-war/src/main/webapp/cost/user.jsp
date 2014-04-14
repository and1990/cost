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
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData('#user_data_table', '<%=basePath%>/addUser.do');">增加</a>
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
                    <span>状态：</span>
                    <select class="easyui-combobox" name="status" style="width:100px;">
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
            onBeforeEdit: function (index, row) {
                row.editing = true;
                updateSelectRow();
            },
            onAfterEdit: function (index, row) {
                row.editing = false;
                updateSelectRow();
            },
            onCancelEdit: function (index, row) {
                row.editing = false;
                updateSelectRow();
            },
            onBeforeLoad: function () {
                $("#dg").datagrid("clearSelections");
            },
            onLoadSuccess: function (data) {
                $("#dg").datagrid("clearSelections");
            }

        });

        $('#user_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });
    })
    ;

    //更新选中行
    function updateSelectRow() {
        var rowCount = $('#dg').datagrid('getRows').length;
        for (var i = 0; i < rowCount; i++) {
            $('#dg').datagrid('updateRow', {
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
        $("#dg").datagrid({
                    queryParams: queryData,
                    pageNumber: 1
                }, 'load'
        );
    }
</script>
</body>
</html>