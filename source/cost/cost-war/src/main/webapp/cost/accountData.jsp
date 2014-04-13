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
                <th data-options="field:'accountMoney',width:80,align:'center',
						editor:{
								type:'numberbox',
								options:{min:0,precision:2}
						}">金额
                </th>
                <th data-options="field:'accountTypeName',width:60,align:'center',formatter:showAccountTypeText,
						editor:{
	                            type:'combobox',
	                            options:{data:accountTypeData,valueField:'value',textField:'name'}
                         }"
                        >消费类型
                </th>
                <th data-options="field:'accountPartner',width:80,align:'center',editor:'text'">组员</th>
                <th data-options="field:'accountTime',width:80,align:'center',
					editor:'datebox'">消费时间
                </th>
                <th data-options="field:'isApproveName',width:60,align:'center',formatter:showIsApproveText,
						editor:{
                            	type:'combobox',
                            	options:{data:isApproveData,valueField:'value',textField:'name'}
                        }"
                        >是否审批
                </th>
                <%--<th data-options="field:'isApproveName',width:60,align:'center',editor:'combobox',loader:getApproveText,mode:'remote'">是否审批</th>--%>
                <th data-options="field:'accountAccessory',width:80,align:'center',formatter:showAccessoryText,
					    editor:{
							options:{data:isApproveData,valueField:'value',textField:'name'}
						}"
                        >附件
                </th>
                <th data-options="field:'approveTime',width:120,align:'center'">审批时间</th>
                <th data-options="field:'accountRemark',width:120,align:'center',editor:'text'">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="account_filter_bar" style="padding: 5px; height: auto">
    <%--<div style="margin-bottom: 5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData('#account_data_table','<%=basePath%>/rest/account/addAccount');">增加</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editData('#account_data_table','<%=basePath%>/rest/account/modifyAccount');">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="removeData('#account_data_table','<%=basePath%>/rest/account/deleteAccount');">删除</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
           onclick="undoData('#account_data_table');">撤销</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
           onclick="saveData('#account_data_table');">保存</a>
    </div>--%>
    <div>
        创建时间从: <input class="easyui-datebox" style="width: 80px">
        到: <input class="easyui-datebox" style="width: 80px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
</div>

<form>
    <input type="hidden" id="page" name="page" value="${pageData.page}">
    <input type="hidden" id="pageSize" name="pageSize" value="${pageData.pageSize}">
</form>

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
            toolbar: [
                {
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        addData('#account_data_table', '<%=basePath%>/addUser.do');
                    }
                },
                '-',
                {
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        editData('#account_data_table', '<%=basePath%>/modifyUser.do');
                    }
                },
                '-',
                {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        removeData('#account_data_table', '<%=basePath%>/deleteUser.do');
                    }
                },
                '-',
                {
                    text: '撤销',
                    iconCls: 'icon-undo',
                    handler: function () {
                        undoData('#account_data_table');
                    }
                },
                '-',
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        saveData('#account_data_table');
                    }
                }
            ]/*,
            queryParams: {
                'pageData.page': $("#page").val(),
                'pageData.pageSize': $("#pageSize").val()
            }*/

        });

        $('#account_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'/*,
            onSelectPage: function (page, pageSize) {
                $("#page").val(page);
                $("#pageSize").val(pageSize);
                $('#account_data_table').datagrid('reload')
            }*/
        });
    });

    //点击“行”操作
    function onClickRow(index) {
        if (actionType != undefined) {
            $('#account_data_table').datagrid('selectRow', rowIndex);
        } else {
            rowIndex = index;
        }
    }
    var accountTypeData = [
        {'value': '1', 'name': '公共'},
        {'value': '2', 'name': '组'},
        {'value': '3', 'name': '个人'}
    ];
    var isApproveData = [
        {'value': '1', 'name': '未审批'},
        {'value': '2', 'name': '已审批'}
    ];
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