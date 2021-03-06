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
    <div id="column_container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-bottom: 50px">
        <span id="null_data" style="color: #FF2F2F"></span>
    </div>

    <div style="text-align: center;margin-top: 50px;">
        <div>
            按用户查看：<input type="radio" name="moneyAccountType" value="1" checked="true"/>
            &nbsp;
            按消费类型查看：<input type="radio" name="moneyAccountType" value="2"/>
            &nbsp;
            消费时间从: <input class="Wdate" id="column_start_time" name="accountVO.accountStartTime" style="width: 150px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'column_end_time\');}'})">
            &nbsp;
            到: <input class="Wdate" id="column_end_time" name="accountVO.accountEndTime" style="width: 150px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'column_start_time\');}',maxDate:'%y-%M-%d'})">
            &nbsp;&nbsp;
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showAccountMoneyChart();">查看</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#column_start_time").val("");
        $("#column_end_time").val("");
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountGroupByUser.do',
            success: function (returnData) {
                if (returnData == undefined) {
                    return;
                }
                var nameArr = new Array();
                var valueArr = new Array();
                setAccountDataByUser(nameArr, valueArr, returnData);
                if (nameArr.length != 0 && valueArr.length != 0) {
                    initAccountMoneyChart(nameArr, valueArr);
                }
            }
        });
    });

    //初始化图表
    function initAccountMoneyChart(nameArr, valueArr) {
        $('#column_container').highcharts({
            chart: {
                type: 'column',
                margin: [ 50, 50, 100, 80],
                style: {fontFamily: 'Microsoft YaHei', fontSize: '16px'}
            },
            plotOptions: {
                series: {
                    pointWidth: 40
                }
            },
            credits: {
                text: ''
            },
            title: {
                text: '消费金额[用户]'
            },
            xAxis: {
                categories: nameArr,
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontFamily: 'Microsoft YaHei', fontSize: '16px'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '金额'
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '金额 : {point.y:.2f} ￥',
                style: {
                    fontFamily: 'Microsoft YaHei', fontSize: '12px'
                }
            },
            series: [
                {
                    data: valueArr,
                    dataLabels: {
                        enabled: true,
                        rotation: -90,
                        color: '#FFFFFF',
                        align: 'right',
                        x: 4,
                        y: 10,
                        style: {
                            fontFamily: 'Microsoft YaHei', fontSize: '16px', textShadow: '0 0 3px black'
                        }
                    }
                }
            ]
        });
    }

    //显示图表
    function showAccountMoneyChart() {
        var startTime = $("#column_start_time").val();
        var endTime = $("#column_end_time").val();
        var showType = $("input[name='moneyAccountType']:checked").val()
        loadAccountMoneyData(startTime, endTime, showType);
        setAccountMoneyChartTitle(startTime, endTime, showType);
    }

    //设置图表格式
    function setAccountMoneyChartTitle(startTime, endTime, showType) {
        var typeText = "用户";
        if (showType == 2) {
            typeText = "消费类型";
        }

        var title = undefined;
        var startNotNull = startTime != undefined && startTime != '';
        var endIsNull = endTime == undefined || endTime == '';
        if (startNotNull && endIsNull) {
            title = startTime + "以后消费金额[" + typeText + "]";
        }

        var startIsNull = startTime == undefined || startTime == '';
        var endNotNull = endTime != undefined && endTime != '';
        if (startIsNull && endNotNull) {
            title = endTime + "之前消费金额[" + typeText + "]";
        }

        if (startNotNull && endNotNull) {
            title = startTime + "至" + endTime + "消费金额[" + typeText + "]";
        }

        if (startIsNull && endIsNull) {
            title = "消费金额[" + typeText + "]";
        }
        var chart = $('#column_container').highcharts();
        chart.setTitle({text: title});
    }

    //加载数据
    function loadAccountMoneyData(startTime, endTime, showType) {
        var url = "<%=basePath%>/getAccountGroupByUser.do";
        if (showType == 2) {
            url = "<%=basePath%>/getAccountGroupByAccountType.do";
        }

        if (startTime == undefined) {
            startTime = "";
        }
        if (endTime == undefined) {
            endTime = "";
        }
        var data = {"accountVO.accountStartTime": startTime, "accountVO.accountEndTime": endTime};
        $.ajax({
            type: 'post',
            url: url,
            data: data,
            success: function (returnData) {
                if (returnData == undefined) {
                    $('#null_data').html("未加载到数据...");
                    return;
                }
                var nameArr = new Array();
                var valueArr = new Array();
                if (showType == 1) {
                    setAccountDataByUser(nameArr, valueArr, returnData);
                } else {
                    setAccountDataByAccountType(nameArr, valueArr, returnData);
                }
                var chart = $('#column_container').highcharts();
                chart.xAxis[0].setCategories(nameArr);
                chart.series[0].setData(valueArr);
                if (valueArr == undefined || valueArr.length == 0) {
                    $('#null_data').html("未加载到数据...");
                } else {
                    $('#null_data').html("");
                }
            }
        });
    }


    //设置用户对应的账单数据
    function setAccountDataByUser(nameArr, valueArr, returnData) {
        var rows = JSON.parse(returnData);
        for (var i = 0; i < rows.length; i++) {
            nameArr.push(rows[i].userName);
            valueArr.push(rows[i].accountMoney)
        }
    }

    /**
     *设置消费类型对应的账单数据
     */
    function setAccountDataByAccountType(nameArr, valueArr, returnData) {
        var rows = JSON.parse(returnData);
        for (var i = 0; i < rows.length; i++) {
            nameArr.push(rows[i].accountTypeName);
            valueArr.push(rows[i].accountMoney)
        }
    }
</script>
</body>
</html>