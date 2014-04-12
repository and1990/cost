<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<div id="user_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="user_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="user_data_table" class="easyui-datagrid"
               data-options="onClickRow:onClickRow">
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

<div id="user_filter_bar" style="padding: 5px; height: auto">
    <%--<div style="margin-bottom: 5px">
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
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
           onclick="getChanges('#user_data_table')">GetChanges</a>
    </div>--%>
    <div>
        创建时间从: <input class="easyui-datebox" style="width: 80px">
        到: <input class="easyui-datebox" style="width: 80px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
</div>

<form>
    <input type="hidden" name="pageSize" value="${pageData.pageSize}">
    <input type="hidden" name="pageTotal" value="${pageData.pageTotal}">
    <input type="hidden" name="pageIndex" value="${pageData.pageIndex}">
    <input type="hidden" name="dataTotal" value="${pageData.dataTotal}">
</form>

<script type="text/javascript">
    $(function () {
        $('#user_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg:'数据装载中......',
            url: '<%=basePath%>/getUserByFilter.do',
            idField: 'userId',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            pageSize:5,
            pageList:[5,10,15,20],
            frozenColumns: [
                [
                    {field: 'userName', checkbox: true},
                    {field: 'loginName', checkbox: true}
                ]
            ],
            toolbar: [
                {
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        addData('#user_data_table', '<%=basePath%>/addUser.do');
                    }
                },
                '-',
                {
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        editData('#user_data_table', '<%=basePath%>/modifyUser.do');
                    }
                },
                '-',
                {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        removeData('#user_data_table', '<%=basePath%>/deleteUser.do');
                    }
                },
                '-',
                {
                    text: '撤销',
                    iconCls: 'icon-undo',
                    handler: function () {
                        undoData('#user_data_table');
                    }
                },
                '-',
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        saveData('#user_data_table');
                    }
                }
            ]
        });

        $('#user_data_table').datagrid('getPager').pagination({
            displayMsg:'当前显示从{from}到{to}共{total}记录',
            onBeforeRefresh:function(pageNumber, pageSize){
                $(this).pagination('loading');
                alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
                $(this).pagination('loaded');
            }
        });

    });


    /*  $(function () {
     $.ajax({
     type: "POST",
     url: '<%=basePath%>/getUserByFilter.do',
     success: function (pageData) {
     var pageObj = JSON.parse(pageData);
     $('#user_data_table').datagrid('loadData', pageObj.dataList);
     }
     });
     });*/
    function onClickRow(index) {
        if (actionType != undefined) {
            $('#user_data_table').datagrid('selectRow', rowIndex);
        } else {
            rowIndex = index;
        }
    }
</script>