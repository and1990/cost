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
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/modules/exporting.js"></script>
</head>
<body>


<div style="font-family: 'Microsoft YaHei';font-size:16px">
    <div id="invest_line_container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getInvestGroupByMonth.do?year=2014',
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
    function initChart(userArr, dataObjArr) {
        var monthArr = [1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12];
        $('#invest_line_container').highcharts({
            chart: {
                style: {fontFamily: 'Microsoft YaHei', fontSize: '16px'}
            },
            credits: {
                text: ''
            },
            title: {
                text: '线性图分析',
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

    //加载数据
    function showInvestLineChart() {
        if (startTime == undefined) {
            startTime = "";
        }
        if (endTime == undefined) {
            endTime = "";
        }
        var data = {"accountVO.accountStartTime": startTime, "accountVO.accountEndTime": endTime};
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountGroupByTypeAndUser.do',
            data: data,
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
                var chart = $('#invest_line_container').highcharts();
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
        Deposit(1, "存款"), Insurance(2, "保险"), Fund(3, "基金"), Shares(4, "股票");
        var investTypeData = {"1": "存款", "2": "保险", "3": "基金", "4": "股票"};
        var typeArr = $.parseJSON(investTypeData);
        var rows = $.parseJSON(returnData);
        for (var typeIndex = 0; typeIndex < typeArr.length; typeIndex++) {
            var valueArr = new Array();
            var code = typeArr[typeIndex].code;
            for (var key in rows) {
                var dataArr = rows[key];
                for (var dataIndex = 0; dataIndex < dataArr.length; dataIndex++) {
                    var data = dataArr[dataIndex];
                    if (code == data.accountType) {
                        valueArr.push(data.accountMoney);
                    }
                }

            }
            var dataObj = new Object();
            dataObj.name = typeArr[typeIndex].name;
            dataObj.data = valueArr;
            dataObjArr.push(dataObj);
        }

        return dataObjArr;
    }
</script>
</body>
</html>