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
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/datagrid-detailview.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div id="clear_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="clear_data_north"
         data-options="region:'north',border:0,fit:true">
        <!-- 统计数据 -->
        <table id="clear_data_table">
            <thead>
            <tr>
                <th data-options="field:'accountMoney',width:80,align:'center' ">总金额</th>
                <th data-options="field:'startDate',width:60,align:'center'">结算开始时间</th>
                <th data-options="field:'endDate',width:60,align:'center'">结算结束时间</th>
                <th data-options="field:'createUser',width:60,align:'center'">创建人</th>
                <th data-options="field:'createTime',width:60,align:'center'">创建时间</th>
                <th data-options="field:'clearAccountRemark',width:60,align:'center'">备注</th>
            </tr>
            </thead>
        </table>
        <!-- 明细数据 -->
        <div style="padding:2px" id="clear_detail_div">
            <table id="clear_detail_table" bgcolor="#ff7f50"></table>
        </div>
    </div>
</div>

<!-- 工具栏 -->
<div id="clear_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" id="approve_button" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
           onclick="clearData();">结算</a>
    </div>
</div>

<!-- 选择结算日期 -->
<div id="clear_date_dialog" title="选择结算日期" class="easyui-dialog"
     style="width:400px;height:200px;text-align:center"
     data-options="closed:true,modal: true">
    <div style="margin-top: 50px">
        结算日期从: <input class="Wdate" id="clearStartDate" name="accountVO.accountStartTime" style="width: 100px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'clearEndDate\');}'})">
        到: <input class="Wdate" id="clearEndDate" name="accountVO.accountEndTime" style="width: 100px"
                  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'clearStartDate\');}',maxDate:'%y-%M-%d'})">
    </div>

    <div style="text-align:center;padding:5px;margin-top: 20px">
        <a href="#" class="easyui-linkbutton" onclick="clearConfirm();">确定</a>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#clear_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg: '数据装载中......',
            url: '<%=basePath%>/getClearData.do',
            idField: 'clearId',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            toolbar: "#clear_tool_bar",
            onLoadSuccess: function (data) {
                if (data.rows.length == 0) {
                    $.messager.alert("提示", "没有加载到数据！", "info");
                }
            },
            view: detailview,
            detailFormatter: function (index, row) {
                return $("#clear_detail_div").html();
            },
            onExpandRow: function (index, row) {
                showClearDetail(index, row);
            }
        });

        //设置分页
        $('#clear_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });

    });

    //显示流水明细
    function showClearDetail(index, row) {
        var detailTable = $('#clear_data_table').datagrid('getRowDetail', index).find('#clear_detail_table');
        detailTable.datagrid({
            url: "<%=basePath%>/getClearDetailData.do?clearAccountId=" + row.clearAccountId,
            fitColumns: true,
            singleSelect: true,
            rownumbers: true,
            loadMsg: '',
            height: 'auto',
            columns: [
                [
                    {field: 'userName', title: '用户名', width: 150, align: 'center'},
                    {field: 'accountMoney', title: '已支付金额', width: 100, align: 'center'},
                    {field: 'clearMoney', title: '需支付金额', width: 100, align: 'center'},
                    {field: 'clearTypeName', title: '结算类型', width: 100, align: 'center'},
                    {field: 'overStatus', title: '结算状态', width: 100, align: 'center'},
                    {field: 'detailRemark', title: '备注', width: 100, align: 'center'}
                ]
            ],
            onResize: function () {
                $('#clear_data_table').datagrid('fixDetailRowHeight', index);
            },
            onLoadSuccess: function () {
                setTimeout(function () {
                    $('#clear_data_table').datagrid('fixDetailRowHeight', index);
                }, 0);
            },
            rowStyler: function (index, row) {
                if (row.type == 1) {
                    return 'background-color:lightgreen;';
                }
            }
        });
        $('#clear_data_table').datagrid('fixDetailRowHeight', index);
    }

    //结算
    function clearData() {
        $("#clear_date_dialog").dialog("open");
    }

    //确定结算
    function clearConfirm() {
        var clearEndDate = $("#clearEndDate").val();
        $.ajax({
            method: "post",
            url: "<%=basePath%>/clearData.do",
            success: function (returnData) {
                $('#clear_data_table').datagrid('reload');
            }
        });
    }

</script>
</body>
</html>