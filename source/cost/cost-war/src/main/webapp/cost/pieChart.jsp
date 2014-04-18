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
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/highcharts.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/Highcharts/modules/exporting.js"></script>
</head>
<body>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountByType.do',
            success: function (returnData) {
                var accountArr = getAccountData(returnData);
                if (accountArr != undefined && accountArr.length != 0) {
                    setChart(accountArr);
                }
            }
        });
    });

    //得到账单数据
    function getAccountData(returnData) {
        var accountArr = new Array();
        if (returnData == undefined) {
            return;
        }
        var rows = JSON.parse(returnData);
        for (var i = 0; i < rows.length; i++) {
            var arr = new Array();
            arr[0] = rows[i].name;
            arr[1] = rows[i].code;
            accountArr.push(arr);
        }
        return accountArr;
    }

    //初始化图表
    function setChart(accountArr) {
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '2014年4月1日-2014年4月30日 个人消费情况'
            },
            tooltip: {
                enabled: true,
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        color: '#000000',
                        connectorColor: '#000000',
                        format: '<b>{point.name}</b>:1 {point.percentage:.1f} %'
                    }
                }
            },
            series: [
                {
                    type: 'pie',
                    name: '比例',
                    data: accountArr
                }
            ]
        });
    }
</script>
</body>
</html>