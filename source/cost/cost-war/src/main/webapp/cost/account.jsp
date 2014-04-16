<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/third/uploadify/uploadify.css">
<script type="text/javascript" src="<%=basePath%>/third/uploadify/jquery.uploadify.min.js"></script>

<div id="account_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="account_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="account_data_table">
            <thead>
            <tr>
                <th data-options="field:'userName',width:80,align:'center'">用户名</th>
                <th data-options="field:'accountMoney',width:80,align:'center' ">金额</th>
                <th data-options="field:'accountTypeName',width:60,align:'center'">消费类型</th>
                <th data-options="field:'accountPartner',width:80,align:'center',editor:'text'">组员</th>
                <th data-options="field:'accountTime',width:80,align:'center', editor:'datebox'">消费时间</th>
                <th data-options="field:'isApproveName',width:60,align:'center'">是否审批</th>
                <th data-options="field:'accountAccessory',width:80,align:'center'">附件</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
                <th data-options="field:'accountRemark',width:120,align:'center'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="account_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData();">增加</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyData();">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteData();">删除</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
           onclick="undoData('#account_data_table');">审批</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-tag-red" plain="true"
           onclick="undoData('#account_data_table');">结算</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="undoData('#account_data_table');">导出Excel</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="saveData('#account_data_table');">查看本周</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="saveData('#account_data_table');">查看本月</a>
    </div>
    <div>
        <form id="account_filter_form" method="post">
            <span>用户：</span>
            <input class="text" name="accountVO.accountName" style="width:100px;"/>
            <span>消费类型：</span>
            <select class="easyui-combobox" name="accountVO.isAdmin" style="width:100px;">
                <option value="0">全部</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>

            <span>状态：</span>
            <select class="easyui-combobox" name="accountVO.accountStatus" style="width:100px;">
                <option value="0">全部</option>
                <option value="1">未审批</option>
                <option value="2">已审批</option>
                <option value="3">已结算</option>
            </select>
            消费时间从: <input class="Wdate" id="accountStartTime" name="accountVO.startTime" style="width: 100px"
                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'accountEndTime\');}'})">

            到: <input class="Wdate" id="accountEndTime" name="accountVO.endTime" style="width: 100px"
                      onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'accountStartTime\');}'})">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getAccountByFilter();">查询</a>
        </form>
    </div>
</div>

<div id="account_dialog" class="easyui-dialog"
     style="width:500px;height:430px;padding:2px;" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="account_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="accountId" name="accountVO.accountId"/>
                        <input type="hidden" id="accountStatus" name="accountVO.accountStatus"/>
                        <input type="hidden" id="isAdmin" name="accountVO.isAdmin"/>
                    </tr>
                    <tr>
                        <td>金额：</td>
                        <td>
                            <input class="text" id="accountMoney" name="accountVO.accountMoney"/>
                        </td>
                    </tr>
                    <tr>
                        <td>消费类型:</td>
                        <td>
                            <input class="text" id="accountType" name="accountVO.accountType"/>
                        </td>
                    </tr>
                    <tr id="tr_password">
                        <td>消费时间:</td>
                        <td>
                            <input class="text" id="accountTime" name="accountVO.accountTime"/>
                        </td>
                    </tr>
                    <tr id="tr_repassword">
                        <td>备注:</td>
                        <td>
                            <input class="password" id="accountRemark" name="accountVO.accountRemark"/>
                        </td>
                    </tr>
                    <tr>
                        <td>上传附件:</td>
                        <td>
                            <input class="text" id="accountAge" name="accountVO.accountAge"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="$('#account_form').form('clear');">取消</a>
            </div>
        </div>
    </div>
</div>

//文件上传对话框
<div id="fileUploadDialog">
    <input type="file" name="file_upload" id="file_upload"/>
</div>

//图片浏览对话框
<div id="lightbox"></div>

<script type="text/javascript">
    //页面加载完成，加载数据
    $(function () {
        $('#account_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg: '数据装载中......',
            url: '<%=basePath%>/getAccountByFilter.do',
            idField: 'accountId',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            toolbar: "#account_tool_bar"
        });

        $('#account_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });
    });

    //获取查询参数
    function getUserByFilter() {
        var queryData = $("#account_filter_form").serializeJson();
        $("#account_data_table").datagrid(
                {
                    queryParams: queryData,
                    pageNumber: 1
                }, 'load'
        );
    }
    (function ($) {
        $.fn.serializeJson = function () {
            var serializeObj = {};
            $(this.serializeArray()).each(function () {
                serializeObj[this.name] = this.value;
            });
            return serializeObj;
        };
    })(jQuery);

    //增加账单
    function addData() {
        //设置标题
        $('#account_dialog').dialog({ title: '增加用户信息'});
        //打开弹出框
        $("#account_dialog").dialog("open");
        //设置action、url值，1代表增加
        $("#action").val(1);
        $("#url").val('<%=basePath%>/addAccount.do');

        $("#accountName").validatebox({
            required: true,
            missingMessage: "用户名不能为空"
        });
        $("#loginName").validatebox({
            required: true,
            missingMessage: "登录名不能为空"
        });
        $("#password").validatebox({
            required: true,
            missingMessage: "密码不能为空"
        });
        $("#repassword").validatebox({
            required: true,
            missingMessage: "确认密码不能为空"
        });
    }

    //修改账单
    function modifyData() {
        var rowData = $("#account_data_table").datagrid("getSelected");
        if (rowData == undefined) {
            alert("请选择数据");
            return;
        }
        //隐藏密码输入框
        $("#tr_password").hide();
        $("#tr_repassword").hide();

        //设置标题
        $('#account_dialog').dialog({ title: '修改用户信息'});
        //打开弹出框
        $("#account_dialog").dialog("open");
        //设置action值，2代表修改
        $("#action").val(2);
        $("#url").val('<%=basePath%>/modifyAccount.do');

        //填充数据
        $("#userId").val(rowData.userId);
        $("#userStatus").val(rowData.userStatus);
        $("#isAdmin").val(rowData.isAdmin);
        $("#userName").val(rowData.userName);
        $("#loginName").val(rowData.loginName);
        $("#userAge").val(rowData.userAge);
        $("#userAddress").val(rowData.userAddress);
        $("#userEmail").val(rowData.userEmail);
        $("#userRemark").val(rowData.userRemark);
    }

    //删除用户
    function deleteData() {
        if (window.confirm("确定删除？")) {
            var rowData = $("#account_data_table").datagrid("getSelected");
            var url = "<%=basePath%>/deleteAccount.do?accountVO.accountId=" + rowData.accountId;
            $.ajax({
                        type: "post",
                        url: url,
                        success: function (returnData) {
                            $('#account_data_table').datagrid('reload');
                        }
                    }
            );
        }
    }

    //提交表单
    function submitForm() {
        //打开进度条
        $.messager.progress();

        var action = $("#action").val();
        var url = $("#url").val();
        $('#account_form').form('submit', {
                    url: url,
                    onSubmit: function () {
                        if (action == 1) {
                            var isValid = $(this).form('validate');
                            if (!isValid) {
                                $.messager.progress('close');
                                return false;
                            }
                            var password = $("#password").val();
                            var rePassword = $("#rePassword").val();
                            var passwordSame = password === rePassword;
                            if (!passwordSame) {
                                $.messager.progress('close');
                                return false;
                            }
                        }
                        return true;
                    },
                    success: function () {
                        $.messager.progress('close');
                        $('#account_form').form('clear');
                        $("#account_dialog").dialog("close");
                        if (action == 2) {
                            $("#password").show();
                            $("#repassword").show();
                            $('#account_data_table').datagrid('reload');
                        }
                    }
                }
        );
    }
</script>