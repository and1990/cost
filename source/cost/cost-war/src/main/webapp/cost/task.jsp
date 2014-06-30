<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="<%=basePath%>/js/drag-drop-custom.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
</head>
<body>
<div id="task_table_div">
    <table id="task_table" border="1" style="width: 750px;height: 250px;text-align:center">
        <thead>
        <tr>
            <td>2014-6-30 周一</td>
            <td>2014-7-01 周二</td>
            <td>2014-7-02 周三</td>
            <td>2014-7-03 周四</td>
            <td>2014-7-04 周五</td>
            <td>2014-7-05 周六</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td> hello</td>
            <td> hello</td>
            <td> hello</td>
            <td> hello</td>
            <td> hello</td>
            <td> hello</td>
        </tr>
        </tbody>
    </table>
</div>

<br><br><br>

<div id="user_data">
    <table id="user_table" border="1" style="width: 600px;height: 50px;text-align: center">
        <tr>
            <td> world</td>
            <td> world</td>
            <td> world</td>
            <td> world</td>
            <td> world</td>
            <td> world</td>
            <td> world</td>
            <td> world</td>
        </tr>
    </table>
</div>

<script type="text/javascript">

    $(function () {
        var root = document.getElementById("task_table");
        for (var index = 0; index < 6; index++) {
            var tr = $("tbody tr:first");
            var trClone = tr.clone(true);
            trClone.appendTo(root);
        }

        //目标对象
        var dragDropObj = new DHTMLgoodies_dragDrop();
        var taskRows = $("#task_table tr");
        for (var rowIndex = 0; rowIndex < taskRows.length; rowIndex++) {
            var columns = $("#task_table tr:eq(" + rowIndex + ")>td");
            for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
                var id = "row" + rowIndex + "_column" + columnIndex;
                columns[columnIndex].id = id;
                dragDropObj.addTarget(id, 'dropItems');
            }
        }

        //源对象
        var columns = $("#user_table tr:eq(0)>td");
        for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
            var id = "user_column" + columnIndex;
            columns[columnIndex].id = id;
            dragDropObj.addSource(id, true);
        }

        dragDropObj.init();
    });

    function dropItems(idOfDraggedItem, targetId, x, y) {
        var obj = document.getElementById(idOfDraggedItem);
        document.getElementById(targetId).appendChild(obj);
    }

</script>

<br><br>
</body>
</html>
