<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
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
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
        <div>
            消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.accountStartTime" style="width: 150px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">
            &nbsp;
            到: <input class="Wdate" id="accountEndTime" name="accountVO.accountEndTime" style="width: 150px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}',maxDate:'%y-%M-%d'})">
            &nbsp;&nbsp;
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showChart();">查看</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountGroupByTypeAndUser.do',
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

    //初始化图表
    function initChart(dataObjArr) {
        $('#container').highcharts({
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
                categories: ["刘腾飞", "史庆杰", "贾文龙", "吴建涛", "石亚飞", "段飒莎"]
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
    function showChart() {
        var startTime = $("#accountStartTime").val();
        var endTime = $("#accountEndTime").val();
        loadData(startTime, endTime);
        setChartTitle(startTime, endTime);
    }

    //设置图表格式
    function setChartTitle(startTime, endTime) {
        var title = undefined;
        var startNotNull = startTime != undefined && startTime != '';
        var endIsNull = endTime == undefined || endTime == '';
        if (startNotNull && endIsNull) {
            title = startTime + "以后消费线性分析";
        }

        var startIsNull = startTime == undefined || startTime == '';
        var endNotNull = endTime != undefined && endTime != '';
        if (startIsNull && endNotNull) {
            title = endTime + "之前消费线性分析";
        }

        if (startNotNull && endNotNull) {
            title = startTime + "至" + endTime + "消费线性分析";
        }

        if (startIsNull && endIsNull) {
            title = "消费线性分析";
        }
        var chart = $('#container').highcharts();
        chart.setTitle({text: title});
    }

    //加载数据
    function loadData(startTime, endTime) {
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
                var chart = $('#container').highcharts();
                chart.series[0].setData(dataObjArr);
                if (dataObjArr == undefined || dataObjArr.length == 0) {
                    $('#null_data').html("未加载到数据...");
                } else {
                    $('#null_data').html("");
                }
            }
        });
    }

    //获取数据
    function getData(returnData) {
        var dataObjArr = new Array();
        var rows = $.parseJSON(returnData);
        for (var key in rows) {
            var valueArr = new Array();
            var objArr = rows[key];
            for (var index = 0; index < objArr.length; index++) {
                valueArr.push(objArr[index].accountMoney);
            }
            var dataObj = new Object();
            dataObj.name = key;
            dataObj.data = valueArr;
            dataObjArr.push(dataObj);
        }
        return dataObjArr;
    }
</script>
</body>
</html>