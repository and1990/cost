<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/uploadify/uploadify.css">
<script type="text/javascript" src="<%=basePath%>/js/uploadify/jquery.uploadify.min.js"></script>

<div id="account_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="account_data_north"
         data-options="region:'north',border:0,fit:true">
        <table id="account_data_table"
        <%--data-options="onClickCell:accessoryDo"--%>>
            <thead>
            <tr>
                <th data-options="field:'userName',width:80,align:'center'">用户名</th>
                <th data-options="field:'accountMoney',width:80,align:'center' ">金额
                </th>
                <th data-options="field:'accountTypeName',width:60,align:'center'">消费类型
                </th>
                <th data-options="field:'accountPartner',width:80,align:'center',editor:'text'">组员</th>
                <th data-options="field:'accountTime',width:80,align:'center', editor:'datebox'">消费时间
                </th>
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
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-do" plain="true"
           onclick="undoData('#account_data_table');">审批</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-do" plain="true"
           onclick="undoData('#account_data_table');">结算</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"
           onclick="undoData('#account_data_table');">导出Excel</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="saveData('#account_data_table');">查看本周</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
           onclick="saveData('#account_data_table');">查看本月</a>
    </div>
    <div>
        <form id="filter_form" method="post">
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
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="getaccountByFilter();">查询</a>
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
    //显示“审批类型”文本
    function showAccountTypeText(value) {
        for (var i = 0; i < accountTypeData.length; i++) {
            if (accountTypeData[i].value == value)
                return accountTypeData[i].name;
        }
        return value;
    }
    //显示“是否审批”文本
    function showIsApproveText(value) {
        for (var i = 0; i < isApproveData.length; i++) {
            if (isApproveData[i].value == value)
                return isApproveData[i].name;
        }
        return value;
    }
    //显示“附件”文本
    function showAccessoryText(value) {
        if (value == undefined) {
            return '<a href="javascript:void();">上传</a>';
        } else {
            return "<a href='javascript:void();'>浏览</a>";
        }
    }
    //日期格式
    $.fn.datebox.defaults.formatter = function (date) {
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + '-' + month + '-' + day;
    }

</script>