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
    <div id="stream_column_container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
        <div>
            <span>年份：</span>
            <input id="streamChartYear" class="easyui-combobox" style="width:100px;" editable="false"/>
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showStreamColumnChart();">查看</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getStreamGroupByMonth.do',
            success: function (returnData) {
                if (returnData != undefined) {
                    initChart(getData(returnData));
                }
            }
        });

        //加载年份
        $('#streamChartYear').combobox({
            url: '<%=basePath%>/getYears.do',
            valueField: 'code',
            textField: 'name',
            onLoadSuccess: function (data) {
                $('#streamChartYear').combobox('setValue', data[0].code).combobox('setText', data[0].name);
                var year = new Date().getFullYear();
                $("#streamChartYear ").combobox("setValue", year);
            }
        });
    });

    Highcharts.setOptions({
        lang: {
            printChart: '打印图表',
            downloadPNG: '保存为PNG',
            downloadJPEG: '保存为JPEG',
            downloadPDF: '保存为PDF',
            downloadSVG: '',
            loading: '正在加载...'
        }
    });

    //初始化图表
    function initChart(valueObjArr) {
        var monthArr = getMonth();
        $('#stream_column_container').highcharts({
            chart: {
                type: 'column',
                style: {fontFamily: 'Microsoft YaHei', fontSize: '16px'}
            },
            credits: {
                text: ''
            },
            title: {
                text: '每月收支'
            },
            xAxis: {
                categories: monthArr
            },
            yAxis: {
                min: 0,
                title: {
                    text: '金额 (￥)'
                }
            },
            tooltip: {
                headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} ￥</b></td></tr>',
                footerFormat: '</table>',
                shared: true,
                useHTML: true
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: valueObjArr
        });
    }

    //显示图表
    function showStreamColumnChart() {
        var year = $('#streamChartYear').combobox('getValue');
        $.ajax({
            type: 'post',
            url: "<%=basePath%>/getStreamGroupByMonth.do?year=" + year,
            success: function (returnData) {
                if (returnData == undefined) {
                    $('#null_data').html("未加载到数据...");
                } else {
                    initChart(getData(returnData));
                    $('#null_data').html("");
                }
            }
        });
    }


    //获取数据
    function getData(returnData) {
        var dataObjArr = new Array();
        var rows = $.parseJSON(returnData);
        var valueArr = new Array();
        for (var key in rows) {
            var row = rows[key];
            for (var i = 0; i < row.length; i++) {
                valueArr.push(parseFloat(row[i]));
            }
            var dataObj = new Object();
            dataObj.name = "支出金额";
            dataObj.data = valueArr;
            dataObjArr.push(dataObj);
        }
        return dataObjArr;
    }

    //获取月份
    function getMonth() {
        var monthArr = new Array();
        for (var i = 1; i <= 12; i++) {
            monthArr.push(i + "月份");
        }
        return monthArr;
    }
</script>
</body>
</html>