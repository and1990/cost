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
    <div id="line_container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
        <div>
            消费时间从: <input class="Wdate" id="line_start_time" name="accountVO.accountStartTime" style="width: 150px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'line_end_time\');}'})">
            &nbsp;
            到: <input class="Wdate" id="line_end_time" name="accountVO.accountEndTime" style="width: 150px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'line_start_time\');}',maxDate:'%y-%M-%d'})">
            &nbsp;&nbsp;
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showLineChart();">查看</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    var typeData = undefined;
    $(function () {
        $("#line_start_time").val("");
        $("#line_end_time").val("");
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountType.do?accountVO.accountClass=1',
            success: function (returnData) {
                if (returnData == undefined) {
                    $('#null_data').html("未加载到数据...");
                    return;
                }
                typeData = returnData;
                $.ajax({
                    type: 'post',
                    url: '<%=basePath%>/getAccountGroupByTypeAndUser.do',
                    success: function (returnData) {
                        if (returnData == undefined) {
                            return;
                        }
                        var dataObjArr = getData(returnData);
                        if (dataObjArr != undefined && dataObjArr.length != 0) {
                            var userArr = getUserData(returnData);
                            initChart(userArr, dataObjArr);
                        }
                    }
                });
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
        $('#line_container').highcharts({
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
                categories: userArr
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
    function showLineChart() {
        var startTime = $("#line_start_time").val();
        var endTime = $("#line_end_time").val();
        loadLineData(startTime, endTime);
        setLineTitle(startTime, endTime);
    }

    //设置图表格式
    function setLineTitle(startTime, endTime) {
        var title = undefined;
        var startNotNull = startTime != undefined && startTime != '';
        var endIsNull = endTime == undefined || endTime == '';
        if (startNotNull && endIsNull) {
            title = startTime + " 以后消费线性分析";
        }

        var startIsNull = startTime == undefined || startTime == '';
        var endNotNull = endTime != undefined && endTime != '';
        if (startIsNull && endNotNull) {
            title = endTime + " 之前消费线性分析";
        }

        if (startNotNull && endNotNull) {
            title = startTime + " 至 " + endTime + " 消费线性分析";
        }

        if (startIsNull && endIsNull) {
            title = "消费线性分析";
        }
        var chart = $('#line_container').highcharts();
        chart.setTitle({text: title});
    }

    //加载数据
    function loadLineData(startTime, endTime) {
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
                var chart = $('#line_container').highcharts();
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
        var typeArr = $.parseJSON(typeData);
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

    //获取用户名称
    function getUserData(returnData) {
        var userArr = new Array();
        var rows = $.parseJSON(returnData);
        for (var key in rows) {
            userArr.push(key);
        }
        return userArr;
    }
</script>
</body>
</html>