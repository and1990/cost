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
<div id="income_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="income_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="income_data_table">
            <thead>
            <tr>
                <th field="ck" checkbox="true"></th>
                <th data-options="field:'userName',width:80,align:'center'">用户名</th>
                <th data-options="field:'incomeMoney',width:80,align:'center' ">金额</th>
                <th data-options="field:'incomeTypeName',width:60,align:'center'">收入方式</th>
                <th data-options="field:'incomeTime',width:80,align:'center'">收入时间</th>
                <th data-options="field:'createUser',width:120,align:'center'">创建人</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
                <th data-options="field:'incomeRemark',width:120,align:'center'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<!-- 工具栏 -->
<div id="income_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" id="add_button" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addIncome();">增加</a>
        <a href="#" id="modify_button_income" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyIncome();">修改</a>
        <a href="#" id="delete_button_income" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteIncome();">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportincomeToExcel();">导出Excel</a>
    </div>
    <div>
        <form id="income_filter_form" method="post">
            <span>用户名：</span>
            <input class="text" name="incomeVO.userName" style="width:100px;"/>

            <span>收入类型：</span>
            <input class="incomeType easyui-combobox" name="incomeVO.incomeType"
                   style="width:100px;" editable="false"/>

            收入时间从: <input class="Wdate" id="incomeStartTime" name="incomeVO.incomeStartTime" style="width: 100px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'incomeEndTime\');}'})">
            到: <input class="Wdate" id="incomeEndTime" name="incomeVO.incomeEndTime" style="width: 100px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'incomeStartTime\');}',maxDate:'%y-%M-%d'})">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getIncomeByFilter();">查询</a>
        </form>
    </div>
</div>

<!-- 添加、修改收入对话框 -->
<div id="income_dialog" class="easyui-dialog"
     style="width:500px;height:250px;padding:2px;border:1px" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="income_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="incomeId" name="incomeVO.incomeId"/>
                    </tr>
                    <tr>
                        <td>金额：</td>
                        <td>
                            <input class="easyui-numberbox easyui-validatebox" id="incomeMoney"
                                   name="incomeVO.incomeMoney"
                                   data-options="min:0,precision:2,required:true,prefix:'￥'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>收入类型:</td>
                        <td>
                            <select class="incomeType easyui-combobox" id="incomeType"
                                    name="incomeVO.incomeType" style="width:150px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>收入时间:</td>
                        <td>
                            <input class="Wdate easyui-validatebox" id="incomeTime" name="incomeVO.incomeTime"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="text" id="incomeRemark" name="incomeVO.incomeRemark"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="#" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="#" class="easyui-linkbutton" onclick="$('#income_form').form('clear');">取消</a>
            </div>
        </div>
    </div>
</div>

<!-- 隐藏字段 -->
<div>
    <input type="hidden" id="userId" name="userId" value="${userId}"/>
</div>

<script type="text/javascript">
$(function () {
    $('#income_data_table').datagrid({
        nowrap: false,
        striped: true,
        border: true,
        collapsible: false,
        loadMsg: '数据装载中......',
        url: '<%=basePath%>/getIncomeByYear.do',
        idField: 'incomeId',
        fit: true,
        fitColumns: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        pagination: true,
        toolbar: "#income_tool_bar",
        onCheck: function (rowIndex, rowData) {
            var userId = $("#userId").val();
            if (userId != rowData.userId) {
                $('#modify_button_income').linkbutton('disable');
                $('#delete_button_income').linkbutton('disable');
            }
        },
        onUncheck: function (rowIndex, rowData) {
            var rows = $("#income_data_table").datagrid("getChecked");
            if (rows == undefined || rows.length == 0) {
                $('#modify_button_income').linkbutton('enable');
                $('#delete_button_income').linkbutton('enable');
            } else {
                if (onlyOwnData(rows)) {
                    $('#modify_button_income').linkbutton('enable');
                    $('#delete_button_income').linkbutton('enable');
                }
            }
        },
        onCheckAll: function (rows) {
            if (!onlyOwnData(rows)) {
                $('#modify_button_income').linkbutton('disable');
                $('#delete_button_income').linkbutton('disable');
            }
        },
        onUncheckAll: function (rows) {
            $('#modify_button_income').linkbutton('enable');
            $('#delete_button_income').linkbutton('enable');
        }
    });

    //设置分页
    $('#income_data_table').datagrid('getPager').pagination({
        pageSize: 10,
        pageList: [10, 20, 30, 40, 50],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
    });

    //加载收入类型
    $('.incomeType').combobox({
        url: '<%=basePath%>/getIncomeType.do',
        valueField: 'code',
        textField: 'name',
        onLoadSuccess: function (data) {
            $('.incomeType').combobox('setValue', data[0].code).combobox('setText', data[0].name);
        }
    });
});

//获取查询参数
function getIncomeByFilter() {
    var queryData = $("#income_filter_form").serializeJson();
    $("#income_data_table").datagrid(
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

//增加收入
function addIncome() {
    //设置标题
    $('#income_dialog').dialog({ title: '增加收入信息'});
    //打开弹出框
    $("#income_dialog").dialog("open");
    //设置action、url值，1代表增加
    $("#action").val(1);
    $("#url").val('<%=basePath%>/addIncome.do');
}

//修改收入
function modifyIncome() {
    var rowDataArr = $("#income_data_table").datagrid("getChecked");
    if (rowDataArr == undefined || rowDataArr.length == 0) {
        alert("请选择数据");
        return;
    }
    if (rowDataArr.length > 1) {
        alert("请选择一条数据");
        return;
    }

    //设置标题
    $('#income_dialog').dialog({ title: '修改收入信息'});
    //打开弹出框
    $("#income_dialog").dialog("open");
    //设置action值，2代表修改
    $("#action").val(2);
    $("#url").val('<%=basePath%>/modifyIncome.do');

    //填充数据
    var rowData = rowDataArr[0];
    $("#incomeId").val(rowData.incomeId);
    $("#incomeMoney").numberbox({value: rowData.incomeMoney});
    $('#incomeType').combobox('setValue', rowData.incomeType);
    $("#incomeTime").val(rowData.incomeTime);
    $("#incomeRemark").val(rowData.incomeRemark);
    $("#incomeId").val(rowData.incomeId);
}

//删除收入
function deleteIncome() {
    var rowDataArr = $("#income_data_table").datagrid("getChecked");
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
        var url = "<%=basePath%>/deleteIncome.do?incomeVO.incomeId=" + rowData.incomeId;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#income_data_table').datagrid('reload');
                    }
                }
        );
    }
}

//导出Excel
function exportincomeToExcel() {
    window.location.href = "<%=basePath%>/exportIncomeToExcel.do";
}

//提交表单
function submitForm() {
    $.messager.progress();
    var action = $("#action").val();
    var url = $("#url").val();
    $('#income_form').form('submit', {
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
                    $('#income_form').form('clear');
                    $("#income_dialog").dialog("close");
                    $('#income_data_table').datagrid('reload');
                }
            }
    );
}

//获取选择的收入ID
function getCheckedIncome() {
    var incomeIds = undefined;
    var rowData = $("#income_data_table").datagrid("getChecked");
    for (var i = 0; i < rowData.length; i++) {
        var incomeId = rowData[i].incomeId;
        incomeIds = incomeIds == undefined ? incomeId : incomeIds + "," + incomeId;
    }
    return incomeIds;
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