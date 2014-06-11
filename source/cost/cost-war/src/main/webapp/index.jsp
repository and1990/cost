<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
</head>
<body>
hello world<br>
<a href="<%=basePath%>/getUserByFilter.do">A</a>

<script type="text/javascript">
    $(function () {
        var a = {};
        console.info(Object.getPrototypeOf(a));
        console.info(a.__proto__);
        console.info(a.constructor.prototype);
    });
</script>
</body>
</html>
