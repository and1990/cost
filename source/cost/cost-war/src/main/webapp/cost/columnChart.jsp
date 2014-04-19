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

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<div style="text-align: center;margin-top: 50px;">
    <div style="font-family: Microsoft YaHei;font-size: 15px;">
        消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.startTime" style="width: 150px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">
        &nbsp;
        到: <input class="Wdate" id="accountEndTime" name="accountVO.endTime" style="width: 150px"
                  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}'})">
        &nbsp;&nbsp;
        <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="getAccountByFilter();">查看</a>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountByType.do',
            success: function (returnData) {
                if (returnData == undefined) {
                    return;
                }
                var nameArr = new Array();
                var valueArr = new Array();
                var rows = JSON.parse(returnData);
                for (var i = 0; i < rows.length; i++) {
                    nameArr.push(rows[i].name);
                    valueArr.push(rows[i].code)
                }
                if (nameArr.length != 0 && valueArr.length != 0) {
                    setChart(nameArr, valueArr);
                }
            }
        });
    });

    //初始化图表
    function setChart(nameArr, valueArr) {
        $('#container').highcharts({
            chart: {
                type: 'column',
                margin: [ 50, 50, 100, 80]
            },
            title: {
                text: 'World\'s largest cities per 2008'
            },
            xAxis: {
                categories: nameArr,
                labels: {
                    rotation: -45,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Population (millions)'
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'Population in 2008: <b>{point.y:.1f} millions</b>',
            },
            series: [
                {
                    name: 'Population',
                    data: valueArr,
                    dataLabels: {
                        enabled: true,
                        rotation: -90,
                        color: '#FFFFFF',
                        align: 'right',
                        x: 4,
                        y: 10,
                        style: {
                            fontSize: '13px',
                            fontFamily: 'Verdana, sans-serif',
                            textShadow: '0 0 3px black'
                        }
                    }
                }
            ]
        });
    }
</script>
</body>
</html>