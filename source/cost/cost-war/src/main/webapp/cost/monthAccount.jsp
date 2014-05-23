<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
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

    <script type="text/javascript" src="<%=basePath%>/third/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/modules/exporting.js"></script>
</head>
<body>


<div style="font-family: 'Microsoft YaHei';font-size:16px">
    <div id="month_line_container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
        <div>
            <span>年份：</span>
            <input id="monthChartYear" class="easyui-combobox" style="width:100px;" editable="false"/>
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showMonthLineChart();">查看</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    var userData = undefined;
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getUserByFilter.do?userVO.userStatus=2&userVo.isPage=false',
            success: function (returnData) {
                if (returnData == undefined) {
                    $('#null_data').html("未加载到数据...");
                    return;
                }
                userData = JSON.parse(returnData).rows;
                $.ajax({
                    type: 'post',
                    url: '<%=basePath%>/getAccountGroupByMonthAndUser.do',
                    success: function (returnData) {
                        if (returnData == undefined) {
                            return;
                        }
                        var dataObjArr = getData(returnData);
                        if (dataObjArr != undefined && dataObjArr.length != 0) {
                            initChart(dataObjArr);
                        }
                    }
                });
            }
        });

        //加载年份
        $('#monthChartYear').combobox({
            url: '<%=basePath%>/getYears.do',
            valueField: 'code',
            textField: 'name',
            onLoadSuccess: function (data) {
                $('#monthChartYear').combobox('setValue', data[0].code).combobox('setText', data[0].name);
                var year = new Date().getFullYear();
                $("#monthChartYear ").combobox("setValue", year);
            }
        });

    });

    //初始化图表
    function initChart(dataObjArr) {
        var monthArr = getMonthArr();
        $('#month_line_container').highcharts({
            chart: {
                style: {fontFamily: 'Microsoft YaHei', fontSize: '16px'}
            },
            credits: {
                text: ''
            },
            title: {
                text: '每月用户消费',
                x: -20
            },
            subtitle: {
                x: -20
            },
            xAxis: {
                categories: monthArr
            },
            yAxis: {
                title: {
                    text: '金额(￥)'
                },
                plotLines: [
                    {
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }
                ]
            },
            tooltip: {
                valueSuffix: '￥'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: dataObjArr
        });
    }
    //显示图表
    function showMonthLineChart() {
        var year = $('#monthChartYear').combobox('getValue');
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountGroupByMonthAndUser.do?year=' + year,
            success: function (returnData) {
                if (returnData == undefined) {
                    $('#null_data').html("未加载到数据...");
                    return;
                }
                var dataObjArr = getData(returnData);
                if (dataObjArr == undefined || dataObjArr.length == 0) {
                    $('#null_data').html("未加载到数据...");
                    return;
                }
                var chart = $('#month_line_container').highcharts();
                for (var index = 0; index < dataObjArr.length; index++) {
                    chart.series[index].setData(dataObjArr[index].data);
                }
                $('#null_data').html("");
            }
        });
    }

    //获取数据
    function getData(returnData) {
        var dataObjArr = new Array();
        var userArr = userData;
        var rows = $.parseJSON(returnData);
        for (var userIndex = 0; userIndex < userArr.length; userIndex++) {
            var valueArr = new Array();
            var userId = userArr[userIndex].userId;
            for (var key in rows) {
                var dataArr = rows[key];
                for (var dataIndex = 0; dataIndex < dataArr.length; dataIndex++) {
                    var data = dataArr[dataIndex];
                    if (userId == data.userId) {
                        valueArr.push(data.accountMoney);
                    }
                }

            }
            var dataObj = new Object();
            dataObj.name = userArr[userIndex].userName;
            dataObj.data = valueArr;
            dataObjArr.push(dataObj);
        }

        return dataObjArr;
    }

    //获取月份
    function getMonthArr() {
        var monthArr = new Array();
        for (var index = 1; index <= 12; index++) {
            monthArr.push(index + "月份");
        }
        return monthArr;
    }
</script>
</body>
</html>