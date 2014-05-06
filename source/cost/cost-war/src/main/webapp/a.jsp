<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:out value="${pageData.pageSize}"></c:out>
${page}
${rows}
${pageData}
</body>
</html>
