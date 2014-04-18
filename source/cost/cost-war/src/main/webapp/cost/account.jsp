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
                <th data-options="field:'accountTime',width:80,align:'center'">消费时间</th>
                <th data-options="field:'accountStatusName',width:60,align:'center'">状态</th>
                <th data-options="field:'accountFile',width:80,align:'center'">附件</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
                <th data-options="field:'accountRemark',width:120,align:'center'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<!-- 工具栏 -->
<div id="account_tool_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData();">增加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="modifyData();">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteData();">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-tag-blue" plain="true"
           onclick="approveData();">审批</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-tag-red" plain="true"
           onclick="clearData();">结算</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="exportExcel();">导出Excel</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="queryThisWeek();">查看本周</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="queryThisMonth();">查看本月</a>
    </div>
    <div>
        <form id="account_filter_form" method="post">
            <span>账单：</span>
            <input class="text" name="accountVO.accountName" style="width:100px;"/>

            <span>消费类型：</span>
            <input id="accountType" class="accountType easyui-combobox" name="accountVO.accountType"
                   style="width:100px;" editable="false"/>

            <span>状态：</span>
            <select class="easyui-combobox" name="accountVO.accountStatus" style="width:100px;" editable="false">
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

<!-- 添加、修改账单对话框 -->
<div id="account_dialog" class="easyui-dialog"
     style="width:500px;height:300px;padding:2px;border:1px" data-options="closed:true,modal: true">
    <div class="easyui-panel" style="width:480px;text-align:center">
        <div style="padding:10px 10px 10px 100px">
            <form id="account_form" method="post">
                <table cellpadding="5">
                    <tr>
                        <input type="hidden" id="accountId" name="accountVO.accountId"/>
                        <input type="hidden" id="accountStatus" name="accountVO.accountStatus"/>
                    </tr>
                    <tr>
                        <td>金额：</td>
                        <td>
                            <input class="easyui-numberbox easyui-validatebox" id="accountMoney"
                                   name="accountVO.accountMoney"
                                   data-options="min:0,precision:2,required:true,prefix:'￥'"/>
                        </td>
                    </tr>
                    <tr>
                        <td>消费类型:</td>
                        <td>
                            <select class="accountType easyui-combobox" name="accountVO.accountType"
                                    style="width:150px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>消费时间:</td>
                        <td>
                            <input class="Wdate easyui-validatebox" id="accountTime" name="accountVO.accountTime"
                                   onfocus="WdatePicker({minDate:'%y-%M-{%d-7}',maxDate:'%y-%M-%d'})"/>
                        </td>
                    </tr>
                    <tr>
                        <td>备注:</td>
                        <td>
                            <input class="text" id="accountRemark" name="accountVO.accountRemark"/>
                        </td>
                    </tr>
                    <tr>
                        <td>上传附件:</td>
                        <td>
                            <input class="text" id="accountFile" name="accountVO.accountFile"/>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <input type="hidden" id="action" name="action">
                <input type="hidden" id="url" name="url">
                <a href="#" class="easyui-linkbutton" onclick="submitForm()">确定</a>
                <a href="#" class="easyui-linkbutton" onclick="$('#account_form').form('clear');">取消</a>
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

        //设置分页
        $('#account_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });

        //加载账单类型
        $('.accountType').combobox({
            url: '<%=request.getContextPath()%>/getAccountType.do',
            valueField: 'code',
            textField: 'name',
            onLoadSuccess: function (data) {
                $('.accountType').combobox('setValue',
                                data[0].code).combobox('setText',
                                data[0].name);
            }
        });
    });

    //获取查询参数
    function getAccountByFilter() {
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
        $('#account_dialog').dialog({ title: '增加账单信息'});
        //打开弹出框
        $("#account_dialog").dialog("open");
        //设置action、url值，1代表增加
        $("#action").val(1);
        $("#url").val('<%=basePath%>/addAccount.do');
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
        $('#account_dialog').dialog({ title: '修改账单信息'});
        //打开弹出框
        $("#account_dialog").dialog("open");
        //设置action值，2代表修改
        $("#action").val(2);
        $("#url").val('<%=basePath%>/modifyAccount.do');

        //填充数据
        $("#accountId").val(rowData.accountId);
        $("#accountMoney").numberbox({value: rowData.accountMoney});
        $('#accountType').combobox('setValue', rowData.accountType);
        $("#accountTime").val(rowData.accountTime);
        $("#accountRemark").val(rowData.accountRemark);
        $("#accountId").val(rowData.accountId);
        $("#accountStatus").val(rowData.accountStatus);
    }

    //删除账单
    function deleteData() {
        var rowData = $("#account_data_table").datagrid("getSelected");
        if (rowData == undefined) {
            alert("请选择数据");
            return;
        }
        if (window.confirm("确定删除？")) {
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

    //审批
    function approveData() {
        var rowData = $("#account_data_table").datagrid("getSelected");
        if (rowData == undefined) {
            alert("请选择数据");
            return;
        }
        var url = "<%=basePath%>/approveAccount.do?accountVO.accountId=" + rowData.accountId;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#account_data_table').datagrid('reload');
                    }
                }
        );
    }

    //结算
    function clearData() {
        var rowData = $("#account_data_table").datagrid("getSelected");
        if (rowData == undefined) {
            alert("请选择数据");
            return;
        }
        var url = "<%=basePath%>/clearAccount.do?accountVO.accountId=" + rowData.accountId;
        $.ajax({
                    type: "post",
                    url: url,
                    success: function (returnData) {
                        $('#account_data_table').datagrid('reload');
                    }
                }
        );
    }

    //导出Excel
    function exportExcel() {

    }

    //查询本周
    function queryThisWeek() {

    }

    //查询本月
    function queryThisMonth() {

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
                            }
                        }
                        return isValid;
                    },
                    success: function () {
                        $.messager.progress('close');
                        $('#account_form').form('clear');
                        $("#account_dialog").dialog("close");
                        $('#account_data_table').datagrid('reload');
                    }
                }
        );
    }
</script>