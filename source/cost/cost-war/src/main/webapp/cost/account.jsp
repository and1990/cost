<%@page language="java" contentType="text/html; charset=utf8" pageEncoding="utf8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/icon.css">

    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div id="account_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="account_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="account_data_table">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'userName',width:80,align:'center'">用户名</th>
                <th data-options="field:'accountMoney',width:80,align:'center' ">金额</th>
                <th data-options="field:'accountClassName',width:60,align:'center'">消费种类</th>
                <th data-options="field:'accountTypeName',width:60,align:'center'">消费类型</th>
                <th data-options="field:'accountTime',width:80,align:'center'">消费时间</th>
                <th data-options="field:'accountStatusName',width:60,align:'center'">状态</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
                <th data-options="field:'accountRemark',width:120,align:'center'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<!-- 工具栏 -->
<div id="account_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" id="add_button" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addAccount();">增加</a>
        <a href="#" id="modify_button" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyAccount();">修改</a>
        <a href="#" id="delete_button" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteAccount();">删除</a>
        <shiro:hasRole name="admin">
            <a href="#" id="approve_button" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
               onclick="approveAccount();">审批</a>
        </shiro:hasRole>
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportAccountToExcel();">导出Excel</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="queryAccountByThisWeek();">查看本周</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="queryAccountByThisMonth();">查看本月</a>
    </div>
    <div>
        <form id="account_filter_form" method="post">
            <span>用户名：</span>
            <input class="text" name="accountVO.userName" style="width:100px;"/>

            <span>消费种类：</span>
            <select class="accountClass easyui-combobox" name="accountVO.accountClass"
                    style="width:100px;" editable="false">
            </select>

            <span>消费类型：</span>
            <select class="accountType easyui-combobox" name="accountVO.accountType"
                    style="width:100px;" editable="false">
            </select>

            <span>状态：</span>
            <select class="accountStatus easyui-combobox" name="accountVO.accountStatus"
                    style="width:100px;" editable="false">
            </select>

            消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.accountStartTime" style="width: 100px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">

            到: <input class="Wdate" id="accountEndTime" name="accountVO.accountEndTime" style="width: 100px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}',maxDate:'%y-%M-%d'})">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getAccountByFilter();">查询</a>
        </form>
    </div>
</div>

<!-- 添加、修改账单对话框 -->
<div id="account_dialog" class="easyui-dialog"
     style="width:500px;height:300px;padding:2px;border:1px" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="account_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="accountId" name="accountVO.accountId"/>
                        <input type="hidden" id="accountStatus" name="accountVO.accountStatus"/>
                    </tr>
                    <tr>
                        <td>金额：</td>
                        <td>
                            <input class="easyui-numberbox easyui-validatebox" id="accountMoney"
                                   name="accountVO.accountMoney"
                                   data-options="min:0,precision:2,required:true,prefix:'￥'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>消费种类:</td>
                        <td>
                            <select class="accountClass easyui-combobox" id="accountClass"
                                    name="accountVO.accountClass" style="width:150px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>消费类型:</td>
                        <td>
                            <select class="accountType easyui-combobox" id="accountType"
                                    name="accountVO.accountType" style="width:150px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>消费时间:</td>
                        <td>
                            <input class="Wdate easyui-validatebox" id="accountTime" name="accountVO.accountTime"
                                   onfocus="WdatePicker({maxDate:'%y-%M-%d'})"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="text" id="accountRemark" name="accountVO.accountRemark"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="#" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="#" class="easyui-linkbutton" onclick="$('#account_form').form('clear');">取消</a>
            </div>
        </div>
    </div>
</div>

//审批对话框
<div id="approve_dialog" title="审批" class="easyui-dialog"
     style="width:300px;height:200px;text-align:center"
     data-options="closed:true,modal: true">
    <div style="margin-top: 50px">
        <select id="approve_dialog_select" class="easyui-combobox" style="width:150px;" editable="false">
            <option value="2">同意</option>
            <option value="4">拒绝</option>
        </select>
    </div>

    <div style="text-align:center;padding:5px;margin-top: 20px">
        <a href="#" class="easyui-linkbutton" onclick="approveConfirm();">确定</a>
    </div>
</div>

<!-- 隐藏字段 -->
<div>
    <input type="hidden" id="userId" name="userId" value="${userId}"/>
    <input type="hidden" id="userType" name="userType" value="${userType}"/>
</div>

<script type="text/javascript">
$(function () {
    $('#account_data_table').datagrid({
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,
        loadMsg: '数据装载中......',
        url: '<%=basePath%>/getAccountByFilter.do',
        idField: 'accountId',
        fit: true,
        fitColumns: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        pagination: true,
        toolbar: "#account_tool_bar",
        onCheck: function (rowIndex, rowData) {
            var userId = $("#userId").val();
            var userType = $("#userType").val();
            if (userId != rowData.userId && userType == 1) {
                $('#modify_button').linkbutton('disable');
                $('#delete_button').linkbutton('disable');
            }
        },
        onUncheck: function (rowIndex, rowData) {
            var rows = $("#account_data_table").datagrid("getChecked");
            if (rows == undefined || rows.length == 0) {
                $('#modify_button').linkbutton('enable');
                $('#delete_button').linkbutton('enable');
            } else {
                if (onlyOwnData(rows)) {
                    $('#modify_button').linkbutton('enable');
                    $('#delete_button').linkbutton('enable');
                }
            }
        },
        onCheckAll: function (rows) {
            var userType = $("#userType").val();
            if (!onlyOwnData(rows) && userType == 1) {
                $('#modify_button').linkbutton('disable');
                $('#delete_button').linkbutton('disable');
            }
        },
        onUncheckAll: function (rows) {
            $('#modify_button').linkbutton('enable');
            $('#delete_button').linkbutton('enable');
        }
    });

    //设置分页
    $('#account_data_table').datagrid('getPager').pagination({
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
    });

    //加载账单种类
    $('.accountClass').combobox({
        url: '<%=basePath%>/getAccountClass.do',
        valueField: 'code',
        textField: 'name',
        onLoadSuccess: function (classData) {
            if (classData != undefined) {
                $('.accountClass').combobox('setValue', classData[0].code).combobox('setText', classData[0].name);
            }
        },
        onChange: function (newValue, oldValue) {
            //加载账单类型
            if (newValue == 0) {
                $('.accountType').combobox("disable");
                $('.accountType').combobox('setValue', 0);
                return;
            }
            $('.accountType').combobox({
                url: '<%=basePath%>/getAccountType.do?accountVO.accountClass=' + newValue,
                valueField: 'code',
                textField: 'name',
                onLoadSuccess: function (typeData) {
                    if (typeData != undefined) {
                        $('.accountType').combobox('setValue', typeData[0].code).combobox('setText', typeData[0].name);
                    }
                }
            });
            $('.accountType').combobox("enable");
        }
    });

    //加载账单状态
    $('.accountStatus').combobox({
        url: '<%=basePath%>/getAccountStatus.do',
        valueField: 'code',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('.accountStatus').combobox('setValue', data[0].code).combobox('setText', data[0].name);
        }
    });
});

//获取查询参数
function getAccountByFilter() {
    var queryData = $("#account_filter_form").serializeJson();
    $("#account_data_table").datagrid(
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

//增加账单
function addAccount() {
    //设置标题
    $('#account_dialog').dialog({ title: '增加账单信息'});
    //打开弹出框
    $("#account_dialog").dialog("open");
    //设置action、url值，1代表增加
    $("#action").val(1);
    $("#url").val('<%=basePath%>/addAccount.do');
}

//修改账单
function modifyAccount() {
    var rowDataArr = $("#account_data_table").datagrid("getChecked");
    if (rowDataArr == undefined || rowDataArr.length == 0) {
        alert("请选择数据");
        return;
    }
    if (rowDataArr.length > 1) {
        alert("请选择一条数据");
        return;
    }

    //设置标题
    $('#account_dialog').dialog({ title: '修改账单信息'});
    //打开弹出框
    $("#account_dialog").dialog("open");
    //设置action值，2代表修改
    $("#action").val(2);
    $("#url").val('<%=basePath%>/modifyAccount.do');

    //填充数据
    var rowData = rowDataArr[0];
    $("#accountId").val(rowData.accountId);
    $("#accountMoney").numberbox({value: rowData.accountMoney});
    $('#accountType').combobox('setValue', rowData.accountType);
    $("#accountTime").val(rowData.accountTime);
    $("#accountRemark").val(rowData.accountRemark);
    $("#accountId").val(rowData.accountId);
    $("#accountStatus").val(rowData.accountStatus);
}

//删除账单
function deleteAccount() {
    var rowDataArr = $("#account_data_table").datagrid("getChecked");
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
        var url = "<%=basePath%>/deleteAccount.do?accountVO.accountId=" + rowData.accountId;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#account_data_table').datagrid('reload');
                    }
                }
        );
    }
}

//审批
function approveAccount() {
    var rowData = $("#account_data_table").datagrid("getChecked");
    if (rowData == undefined) {
        alert("请选择数据");
        return;
    }
    $("#approve_dialog").dialog("open");
}
//确认审批
function approveConfirm() {
    var accountIds = getCheckedAccount();
    var status = $('#approve_dialog_select').combobox('getValue');
    $.ajax({
                type: "post",
                url: "<%=basePath%>/approveAccount.do?accountIds=" + accountIds + "&accountVO.accountStatus=" + status,
                success: function (returnData) {
                    $("#approve_dialog").dialog("close");
                    $('#account_data_table').datagrid('reload').datagrid('uncheckAll');
                }
            }
    );
}

//导出Excel
function exportAccountToExcel() {
    window.location.href = "<%=basePath%>/exportAccountToExcel.do";
}

//查询本周
function queryAccountByThisWeek() {
    $.ajax({
                type: "post",
                url: "<%=basePath%>/getAccountByThisWeek.do",
                success: function (returnData) {
                    var dataObj = JSON.parse(returnData);
                    $('#account_data_table').datagrid('loadData', dataObj.rows);
                }
            }
    );
}

//查询本月
function queryAccountByThisMonth() {
    $.ajax({
                type: "post",
                url: "<%=basePath%>/getAccountByThisMonth.do",
                success: function (returnData) {
                    var dataObj = JSON.parse(returnData);
                    $('#account_data_table').datagrid('loadData', dataObj.rows);
                }
            }
    );
}

//提交表单
function submitForm() {
    $.messager.progress();
    var action = $("#action").val();
    var url = $("#url").val();
    $('#account_form').form('submit', {
                url: url,
                onSubmit: function () {
                    if (action == 1) {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                        }
                    }
                    return isValid;
                },
                success: function () {
                    $.messager.progress('close');
                    $('#account_form').form('clear');
                    $("#account_dialog").dialog("close");
                    $('#account_data_table').datagrid('reload');
                }
            }
    );
}

//获取选择的账单ID
function getCheckedAccount() {
    var accountIds = undefined;
    var rowData = $("#account_data_table").datagrid("getChecked");
    for (var i = 0; i < rowData.length; i++) {
        var accountId = rowData[i].accountId;
        accountIds = accountIds == undefined ? accountId : accountIds + "," + accountId;
    }
    return accountIds;
}

//是否是自己添加的数据
function onlyOwnData(rows) {
    var userId = $("#userId").val();
    var onlyOwnData = true;
    for (var index = 0; index < rows.length; index++) {
        if (userId != rows[index].userId) {
            onlyOwnData = false;
        }
    }
    return onlyOwnData;
}
</script>
</body>
</html>