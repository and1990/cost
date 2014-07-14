<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/third/easy-ui/themes/icon.css">

    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>


<script type="text/javascript">
    $(function () {
        //var elements = warpElements([10, 20, 30, 40, 50]);
        var elements = warp2([10, 20, 30, 40, 50]);
        var f = elements[1];
        f();
    });

    function warpElements(a) {
        var result = [];
        for (var i = 0, n = a.length; i < n; i++) {
            result[i] = function () {
                console.info(a[i]);
                return a[i];
            };
        }
        return result;
    }

    function warp2(a) {
        var result = [];
        for (var i = 0, n = a.length; i < n; i++) {
            (function () {
                var j = i;
                result[i] = function () {
                    console.info(a[j]);
                    return a[j];
                };
            })();
        }
        return result;
    }
</script>

</body>
</html>
