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
            按消费类型查看：<input type="radio" name="type" value="1"/>
            &nbsp;
            按用户查看：<input type="radio" name="type" value="2"/>
            &nbsp;&nbsp;
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
    function setChart(accountArr) {
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            credits: {
                text: ''
            },
            title: {
                text: '个人消费占比',
                style: {fontFamily: 'Microsoft YaHei', fontSize: 16}
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

    //显示图表
    function pieChartShow() {
        var startTime = $("#accountStartTime").val();
        var endTime = $("#accountEndTime").val();

        var title = undefined;

        var startNotNull = startTime != undefined && startTime != '';
        var endIsNull = endTime == undefined || endTime == '';
        if (startNotNull && endIsNull) {
            title = startTime + "以后个人消费占比";
        }

        var startIsNull = startTime == undefined || startTime == '';
        var endNotNull = endTime != undefined && endTime != '';
        if (startIsNull && endNotNull) {
            title = endTime + "之前个人消费占比";
        }

        if (startNotNull && endNotNull) {
            title = startTime + "至" + endTime + "个人消费占比";
        }
        var chart = $('#container').highcharts();
        chart.setTitle({text: title, style: {fontFamily: 'Microsoft YaHei', fontSize: 16}});
    }
</script>
</body>
</html>