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
            <form id="pie_form">
                按消费类型查看：<input type="radio" name="type" value="1" checked="checked"/>
                &nbsp;
                按用户查看：<input type="radio" name="type" value="2"/>
                &nbsp;
                消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.accountStartTime" style="width: 150px"
                              onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">
                &nbsp;
                到: <input class="Wdate" id="accountEndTime" name="accountVO.accountEndTime" style="width: 150px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}',maxDate:'%y-%M-%d'})">
                &nbsp;&nbsp;
                <a href="#" style="text-decoration: none" iconCls="icon-search" onclick="showChart();">查看</a>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $.ajax({
            type: 'post',
            url: '<%=basePath%>/getAccountGroupByUser.do',
            success: function (returnData) {
                var accountArr = new Array();
                if (returnData == undefined) {
                    return;
                }
                var rows = JSON.parse(returnData);
                for (var i = 0; i < rows.length; i++) {
                    var arr = new Array();
                    arr[0] = rows[i].userName;
                    arr[1] = rows[i].accountMoney;
                    accountArr.push(arr);
                }
                if (accountArr != undefined && accountArr.length != 0) {
                    initChart(accountArr);
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
    function initChart(accountArr) {
        $('#container').highcharts({
                    chart: {
                        style: {
                            fontFamily: 'Microsoft YaHei',
                            fontSize: '16px'
                        }
                    },
                    credits: {
                        text: ''
                    },
                    title: {
                        text: "个人消费占比[消费类型]"
                    },
                    tooltip: {
                        enabled: true,
                        pointFormat: '{series.name}: {point.percentage:.2f}%',
                        style: {
                            fontFamily: 'Microsoft YaHei', fontSize: '12px'
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: true,
                                color: '#000000',
                                connectorColor: '#000000',
                                format: '{point.name} : {point.percentage:.2f} %',
                                style: {fontFamily: 'Microsoft YaHei', fontSize: '12px'}
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
                }
        )
        ;
    }

    //显示图表
    function showChart() {
        var startTime = $("#accountStartTime").val();
        var endTime = $("#accountEndTime").val();
        var showType = $(":radio").val();
        loadData(startTime, endTime, showType);
        setChartTitle(startTime, endTime, showType);
    }

    //设置图表格式
    function setChartTitle(startTime, endTime, showType) {
        var typeText = "消费类型";
        if (showType == 2) {
            typeText = "用户";
        }

        var title = undefined;
        var startNotNull = startTime != undefined && startTime != '';
        var endIsNull = endTime == undefined || endTime == '';
        if (startNotNull && endIsNull) {
            title = startTime + "以后消费占比[" + typeText + "]";
        }

        var startIsNull = startTime == undefined || startTime == '';
        var endNotNull = endTime != undefined && endTime != '';
        if (startIsNull && endNotNull) {
            title = endTime + "之前消费占比[" + typeText + "]";
        }

        if (startNotNull && endNotNull) {
            title = startTime + "至" + endTime + "个人消费占比[" + typeText + "]";
        }

        if (startIsNull && endIsNull) {
            title = "消费占比[" + typeText + "]";
        }
        var chart = $('#container').highcharts();
        chart.setTitle({text: title});
    }

    //加载数据
    function loadData(startTime, endTime, showType) {
        var url = "<%=basePath%>/getAccountGroupByAccountType.do";
        if (showType == 2) {
            url = "<%=basePath%>/getAccountGroupByUser.do";
        } else if (showType == 3) {
            url = "<%=basePath%>/getAccountGroupByMonth.do";
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
                var accountArr = new Array();
                var rows = JSON.parse(returnData);
                for (var i = 0; i < rows.length; i++) {
                    var arr = new Array();
                    arr[0] = rows[i].accountTypeName;
                    arr[1] = rows[i].accountMoney;
                    accountArr.push(arr);
                }
                var chart = $('#container').highcharts();
                chart.series[0].setData(accountArr);
                if (accountArr == undefined || accountArr.length == 0) {
                    $('#null_data').html("未加载到数据...");
                } else {
                    $('#null_data').html("");
                }
            }
        });
    }

    //得到账单数据
    function getAccountData(returnData) {
        var accountArr = new Array();
        if (returnData == undefined) {
            return;
        }
        var rows = JSON.parse(returnData);
        for (var i = 0; i < rows.length; i++) {
            var arr = new Array();
            arr[0] = rows[i].userName;
            arr[1] = rows[i].accountMoney;
            accountArr.push(arr);
        }
        return accountArr;
    }
</script>
</body>
</html>