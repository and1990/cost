<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<html>
<head>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/third/easy-ui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
<div style="margin-top:200px;margin-left:450px">
    <form>
        &nbsp;&nbsp;&nbsp;
        原密码：<input type="password" id="oldPassword">
        <span id="oldPasswordText" style="color: red"></span><br><br>
        &nbsp;&nbsp;&nbsp;
        新密码：<input type="password" id="newPassword">
        <span id="newPasswordText" style="color: red"></span><br><br>
        确认新密码：<input type="password" id="confirmNewPassword">
        <span id="confirmNewPasswordText" style="color: red"></span><br><br>
        <input type="button" value="确认" onclick="modifyPassword();">
        &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="取消">
    </form>
</div>

<script type="text/javascript">
    //修改密码
    function modifyPassword() {
        initPromotionText();
        var oldPassword = $("#oldPassword").val();
        var newPassword = $("#newPassword").val();
        var confirmNewPassword = $("#confirmNewPassword").val();
        var validate = validateInput(oldPassword, newPassword, confirmNewPassword);
        if (!validate) {
            return;
        }
        $.ajax({
                    type: "post",
                    url: "<%=basePath%>/validatePassword.do?userVO.password=" + oldPassword,
                    success: function (returnData) {
                        var dataObj = JSON.parse(returnData);
                        if (dataObj.data != true) {
                            $("#oldPasswordText").text("原密码不正确");
                            return;
                        }
                        $.ajax({
                                    type: "post",
                                    url: "<%=basePath%>/modifyPassword.do?userVO.password=" + newPassword,
                                    success: function (data) {
                                        alert("修改成功，请重新登录");
                                        window.location.href = "<%=basePath%>/loginOut.do";
                                    }
                                }
                        );
                    }
                }
        );
    }

    //初始化提示信息
    function initPromotionText() {
        $("#oldPasswordText").text("");
        $("#newPasswordText").text("");
        $("#confirmNewPasswordText").text("");
    }

    /**
     *验证输入是否合法
     */
    function validateInput(oldPassword, newPassword, confirmNewPassword) {
        var validate = true;
        if (oldPassword == undefined || oldPassword.length == 0) {
            $("#oldPasswordText").text("请输入原密码");
            validate = false;
        }
        if (newPassword == undefined || newPassword.length == 0) {
            $("#newPasswordText").text("请输入新密码");
            validate = false;
        }
        if (confirmNewPassword == undefined || confirmNewPassword.length == 0) {
            $("#confirmNewPasswordText").text("请输入确认密码");
            validate = false;
        }
        if (newPassword != confirmNewPassword) {
            $("#newPasswordText").text("新密码两次输入不一致");
            validate = false;
        }
        return validate;
    }
</script>
</body>
</html>
