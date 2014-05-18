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
<div id="stream_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="stream_data_north"
         data-options="region:'north',border:0,fit:true">
        <!-- 统计数据 -->
        <table id="stream_data_table">
            <thead>
            <tr>
                <th data-options="field:'monthName',width:80,align:'center'">月份</th>
                <th data-options="field:'incomeMoney',width:80,align:'center' ">收入金额</th>
                <th data-options="field:'accountMoney',width:80,align:'center' ">支出金额</th>
                <th data-options="field:'leftMoney',width:80,align:'center' ">剩余金额</th>
                <th data-options="field:'createTime',width:60,align:'center'">同步时间</th>
            </tr>
            </thead>
        </table>
        <!-- 明细数据 -->
        <div style="padding:2px" id="stream_detail_div">
            <table id="stream_detail_table" bgcolor="#ff7f50"></table>
        </div>
    </div>
</div>

<!-- 工具栏 -->
<div id="stream_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <span>年份：</span>
        <input id="year" class="easyui-combobox" name="streamVO.year"
               style="width:100px;" editable="false"/>
        <a href="#" id="approve_button" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
           onclick="synStreamData();">同步数据</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportStreamToExcel();">导出Excel</a>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $('#stream_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg: '数据装载中......',
            url: '<%=basePath%>/getStreamByYear.do',
            idField: 'streamId',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            showFooter: true,
            toolbar: "#stream_tool_bar",
            view: detailview,
            detailFormatter: function (index, row) {
                return $("#stream_detail_div").html();
            },
            onExpandRow: function (index, row) {
                showStreamDetail(index, row);
            }
        });

        //加载年份
        $('#year').combobox({
            url: '<%=basePath%>/getYears.do',
            valueField: 'code',
            textField: 'name',
            onLoadSuccess: function (data) {
                $('#year').combobox('setValue', data[0].code).combobox('setText', data[0].name);
            },
            onChange: function (newValue, oldValue) {
                var data = {"year": newValue};
                $("#stream_data_table").datagrid(
                        {
                            queryParams: data,
                            pageNumber: 1
                        }, 'load'
                );
            }
        });
    });

    //显示流水明细
    function showStreamDetail(index, row) {
        var year = $('#year').combobox("getValue");
        if (row.month == undefined) {
            return;
        }
        var detailTable = $('#stream_data_table').datagrid('getRowDetail', index).find('#stream_detail_table');
        detailTable.datagrid({
            url: '<%=basePath%>/getStreamDetail.do?year=' + year + "&month=" + row.month,
            fitColumns: true,
            singleSelect: true,
            rownumbers: true,
            loadMsg: '',
            height: 'auto',
            columns: [
                [
                    {field: 'date', title: '日期', width: 150, align: 'center'},
                    {field: 'typeName', title: '收支类型', width: 100, align: 'center'},
                    {field: 'money', title: '金额', width: 100, align: 'center'},
                    {field: 'remark', title: '备注', width: 100, align: 'center'}
                ]
            ],
            onResize: function () {
                $('#stream_data_table').datagrid('fixDetailRowHeight', index);
            },
            onLoadSuccess: function () {
                setTimeout(function () {
                    $('#stream_data_table').datagrid('fixDetailRowHeight', index);
                }, 0);
            },
            rowStyler: function (index, row) {
                if (row.type == 1) {
                    return 'background-color:lightgreen;';
                }
            }
        });
        $('#stream_data_table').datagrid('fixDetailRowHeight', index);
    }

    //同步流水数据
    function synStreamData() {
        var year = $('#year').combobox("getValue");
        $.ajax({
            method: "post",
            url: "<%=basePath%>/synStreamData.do?year=" + year,
            success: function (returnData) {
                $("#stream_data_table").datagrid("reload");
            }
        });
    }

    //导出excel
    function exportStreamToExcel() {
        window.location.href = "<%=basePath%>/exportStreamToExcel.do";
    }
</script>
</body>
</html>