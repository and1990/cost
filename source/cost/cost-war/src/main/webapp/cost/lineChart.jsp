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
            按用户查看：<input type="radio" name="accountType" value="1" checked="true"/>
            &nbsp;
            按消费类型查看：<input type="radio" name="accountType" value="2"/>
            &nbsp;
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
                var rows = JSON.parse(returnData);
                var dataObjArr = new Array();
                for (var i = 0; i < rows.length; i++) {
                    var valueArr = new Array();
                    var typeName = row.accountTypeName;
                    for (var j = 0; j < rows[i].length; j++) {
                        valueArr.push(row[j].accountMoney);
                    }
                    var dataObj = new (typeName, valueArr);
                    dataObjArr.push(dataObj);
                }
                if (nameArr.length != 0 && valueArr.length != 0) {
                    initChart(dataObjArr);
                }
            }
        });
    });

    function dataObj(name, dataArr) {
        this.name = name;
        this.dataArr = dataArr;
    }

    //初始化图表
    function initChart(dataObjArr) {
        $('#container').highcharts({
            title: {
                text: '线性图分析',
                x: -20
            },
            subtitle: {
                x: -20
            },
            xAxis: {
                categories: [11, 22, 33, 44, 55]
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

</script>
</body>
</html>