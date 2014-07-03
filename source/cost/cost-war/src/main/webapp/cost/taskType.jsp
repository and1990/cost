<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<div id="task_type_data_layout" class="easyui-layout" data-options="width:400">
    <div id="task_type_data_layout" style="width:400px;height:500px">
        <table id="task_type_data_table">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'taskTypeName',width:80,align:'center',editor:'text'">名称</th>
                <th data-options="field:'statusName',width:120,align:'center'">状态</th>
                <th data-options="field:'taskTypeRemark',width:120,align:'center',editor:'text'">备注</th>
                <th data-options="field:'createTime',width:120,align:'center',editor:'text'">创建时间</th>
                <th data-options="field:'modifyTime',width:120,align:'center',editor:'text'">修改时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<%--<div id="task_type_data_layout" style="width:400px;height:500px">
    <table id="task_type_data_table">
        <thead>
        <tr>
            <th field="ck" checkbox="true"></th>
            <th data-options="field:'taskTypeName',width:80,align:'center',editor:'text'">名称</th>
            <th data-options="field:'statusName',width:120,align:'center'">状态</th>
            <th data-options="field:'taskTypeRemark',width:120,align:'center',editor:'text'">备注</th>
            <th data-options="field:'createTime',width:120,align:'center',editor:'text'">创建时间</th>
            <th data-options="field:'modifyTime',width:120,align:'center',editor:'text'">修改时间</th>
        </tr>
        </thead>
    </table>
</div>--%>

<div id="task_type_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <shiro:hasRole name="admin">
            <a href="#" id="task_type_add_button" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="addTaskType();">增加</a>
        </shiro:hasRole>
        <a href="#" id="task_type_modify_button" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyTaskType();">修改</a>
        <shiro:hasRole name="admin">
            <a href="#" id="task_type_delete_button" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="deleteTaskType();">删除</a>
            <a href="#" id="task_type_enable_button" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
               onclick="modifyTaskTypeStatus(2);">启用</a>
            <a href="#" id="task_type_unable_button" class="easyui-linkbutton" iconCls="icon-tag-red" plain="true"
               onclick="modifyTaskTypeStatus(1);">禁用</a>
        </shiro:hasRole>
    </div>
</div>

<div id="task_type_dialog" class="easyui-dialog"
     style="width:500px;height:430px;padding:2px;" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="task_type_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="taskTypeId" name="taskTypeVO.taskTypeId"/>
                        <input type="hidden" id="taskTypeStatus" name="taskTypeVO.taskTypeStatus"/>
                        <input type="hidden" id="taskTypeType" name="taskTypeVO.taskTypeType"/>
                    </tr>
                    <tr>
                        <td>名称：</td>
                        <td>
                            <input class="text easyui-validatebox" id="taskTypeName"
                                   name="taskTypeVO.taskTypeName" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="text " id="taskTypeRemark"
                                   name="taskTypeVO.taskTypeRemark" data-options="required:true"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="$('#taskType_form').form('clear');">取消</a>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#task_type_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg: '数据装载中......',
            url: '',
            idField: 'taskTypeId',
            fit: true,
            fitColumns: true,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            pagination: true,
            toolbar: '#task_type_tool_bar'
        });

        $('#task_type_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });
    });

    //增加任务类型
    function addtaskType() {
        //设置标题
        $('#taskType_dialog').dialog({ title: '增加任务类型信息'});
        //初始化界面
        $("#taskTypeId").val("");
        $("#taskTypeStatus").val("");
        $("#taskTypeType").val("");
        $("#taskTypeName").val("");
        $("#loginName").val("");
        $("#password").val("");
        $("#repassword").val("");
        $("#taskTypeAge").val("");
        $("#taskTypeAddress").val("");
        $("#taskTypeEmail").val("");
        $("#taskTypeRemark").val("");
        //显示密码输入框
        $("#tr_password").show();
        $("#tr_repassword").show();
        //打开弹出框
        $("#taskType_dialog").dialog("open");
        //设置action、url值，1代表增加
        $("#action").val(1);
        $("#url").val('<%=basePath%>/addtaskType.do');
    }

    //修改任务类型
    function modifytaskType() {
        var rowDataArr = $("#task_type_data_table").datagrid("getChecked");
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
        $('#taskType_dialog').dialog({ title: '修改任务类型信息'});
        //打开弹出框
        $("#taskType_dialog").dialog("open");
        //设置action值，2代表修改
        $("#action").val(2);
        $("#url").val('<%=basePath%>/modifytaskType.do');

        //填充数据
        var rowData = rowDataArr[0];
        $("#taskTypeId").val(rowData.taskTypeId);
        $("#taskTypeStatus").val(rowData.taskTypeStatus);
        $("#taskTypeType").val(rowData.taskTypeType);
        $("#taskTypeName").val(rowData.taskTypeName);
        $("#loginName").val(rowData.loginName);
        $("#taskTypeAge").val(rowData.taskTypeAge);
        $("#taskTypeAddress").val(rowData.taskTypeAddress);
        $("#taskTypeEmail").val(rowData.taskTypeEmail);
        $("#taskTypeRemark").val(rowData.taskTypeRemark);
    }

    //删除任务类型
    function deletetaskType() {
        var rowDataArr = $("#task_type_data_table").datagrid("getChecked");
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
            var url = "<%=basePath%>/deletetaskType.do?taskTypeVO.taskTypeId=" + rowData.taskTypeId;
            $.ajax({
                        type: "post",
                        url: url,
                        success: function (returnData) {
                            $('#task_type_data_table').datagrid('reload').datagrid('uncheckAll');
                        }
                    }
            );
        }
    }

    //更新任务类型状态
    function modifyTaskTypeStatus(taskTypeStatus) {
        var rowDataArr = $("#task_type_data_table").datagrid("getChecked");
        if (rowDataArr == undefined || rowDataArr.length == 0) {
            alert("请勾选数据");
            return;
        }
        var promotion = taskTypeStatus == 1 ? "确定禁用选择的任务类型？" : "确定启用选择的任务类型？";
        if (window.confirm(promotion)) {
            var taskTypeIds = getCheckedtaskTypeIds();
            var url = "<%=basePath%>/modifyTaskTypeStatus.do?taskTypeIds=" + taskTypeIds + "&taskTypeVO.taskTypeStatus=" + taskTypeStatus;
            $.ajax({
                        type: "post",
                        url: url,
                        success: function (returnData) {
                            $('#task_type_data_table').datagrid('reload').datagrid('uncheckAll');
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
        $('#taskType_form').form('submit', {
                    url: url,
                    onSubmit: function () {
                        if (action == 1) {
                            var isValid = $(this).form('validate');
                            if (!isValid) {
                                $.messager.progress('close');
                                return false;
                            }
                        }
                        return true;
                    },
                    success: function () {
                        $.messager.progress('close');
                        $('#task_type_form').form('clear');
                        $("#task_type_dialog").dialog("close");
                        $('#task_type_data_table').datagrid('reload').datagrid('uncheckAll');
                    }
                }
        );
    }
</script>
</body>
</html>