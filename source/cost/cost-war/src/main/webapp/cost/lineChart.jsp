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
<div>
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

    <div style="text-align: center;margin-top: 50px;">
        <div style="font-family: Microsoft YaHei;font-size: 16px;">
            消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.startTime" style="width: 150px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">
            &nbsp;
            到: <input class="Wdate" id="accountEndTime" name="accountVO.endTime" style="width: 150px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}'})">
            &nbsp;&nbsp;
            <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="pieChartShow();">查看</a>
        </div>
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
            title: {
                text: '线性图分析',
                x: -20
            },
            subtitle: {
                x: -20
            },
            xAxis: {
                categories: nameArr
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
            series: [
                {
                    name: 'Tokyo',
                    data: [7.0, 6.9, 9.5, 14.5, 18.2]
                },
                {
                    name: 'New York',
                    data: [-0.2, 0.8, 5.7, 11.3, 17.0]
                },
                {
                    name: 'Berlin',
                    data: [-0.9, 0.6, 3.5, 8.4, 13.5]
                },
                {
                    name: 'London',
                    data: [3.9, 4.2, 5.7, 8.5, 11.9]
                }
            ]
        });
    }

</script>
</body>
</html>