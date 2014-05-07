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
<div id="stream_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="stream_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="stream_data_table">
            <thead>
            <tr>
                <th data-options="field:'month',width:80,align:'center'">月份</th>
                <th data-options="field:'streamMoney',width:80,align:'center' ">收入金额</th>
                <th data-options="field:'accountMoney',width:80,align:'center' ">支出金额</th>
                <th data-options="field:'leftMoney',width:80,align:'center' ">剩余金额</th>
                <th data-options="field:'createTime',width:60,align:'center'">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<!-- 工具栏 -->
<div id="stream_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportstreamToExcel();">导出Excel</a>
    </div>
    <div>
        <span>年份：</span>
        <input id="year" class="easyui-combobox" name="streamVO.year"
               style="width:100px;" editable="false"/>
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
            singleSelect: false,
            toolbar: "#stream_tool_bar"
        });

        //加载年份
        $('#year').combobox({
            url: '<%=basePath%>/getYear.do',
            valueField: 'code',
            textField: 'name',
            onLoadSuccess: function (data) {
                $('#year').combobox('setValue', data[0].code).combobox('setText', data[0].name);
            }
        });
    });

    //获取查询参数
    function getStreamByFilter() {
        var queryData = $("#stream_filter_form").serializeJson();
        $("#stream_data_table").datagrid(
                {
                    queryParams: queryData,
                    pageNumber: 1
                }, 'load'
        );
    }

</script>
</body>
</html>