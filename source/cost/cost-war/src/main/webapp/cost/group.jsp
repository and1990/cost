<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<!--显示已创建的组信息-->
<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="group_data_north"
         data-options="region:'north',border:0,fit:true" style="height: 280px">
        <table id="group_data_table" class="easyui-datagrid"
               data-options="rownumbers:true,singleSelect:true,fit:true,pagination:true,fitColumns:true,
		       toolbar:'#group_filter_bar',onClickRow:onClickRow">
            <thead>
            <tr>
                <th data-options="field:'groupName',width:80,align:'center',editor:'text'">组名</th>
                <th data-options="field:'userIds',width:80,align:'center',editor:'text',hidden:true">组员ID</th>
                <th data-options="field:'userNames',width:120,align:'center',editor:{
                            type:'combobox',
                            options:{
							   editable:false,
                               onShowPanel:openUserDialog
                            }}"
                        >组员
                </th>
                <th data-options="field:'groupStatusName',width:60,align:'center',formatter:showStatusText,
						editor:{
                            type:'combobox',
                            options:{
                                data:statusData,
                                valueField:'value',
                                textField:'name',
                                editable:false
                            }}"
                        >状态
                </th>
                <th data-options="field:'groupRemark',width:120,align:'center',editor:'text'">备注</th>
                <th data-options="field:'createUser',width:120,align:'center'">创建人</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="group_filter_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addData('#group_data_table','<%=basePath%>/rest/group/addGroup');">增加</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editData('#group_data_table','<%=basePath%>/rest/group/updateGroup');">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="removeData('#group_data_table','<%=basePath%>/rest/group/deleteGroup');">删除</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
           onclick="undoData('#group_data_table');">撤销</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
           onclick="saveGroup();">保存</a>
    </div>
</div>


<!--点击增加、修改按钮时，显示界面-->
<div id="user_data_div" class="easyui-dialog" title="选择用户" style="width:600px;height:400px"
     data-options="closed: true,modal:true">
    <div id="user_data_dialog" class="easyui-layout" data-options="fit:true">
        <div id="user_data_dialog_north" style="height: 30px" data-options="region:'north',border:0"></div>
        <div id="user_data_dialog_west" style="width: 250px" data-options="region:'west',border:0">
            <div style="margin-left:100px">
                <table id="user_data_dialog_left" class="easyui-datagrid" style="width: 150px;height: 240px">
                    <thead>
                    <tr>
                        <th data-options="field:'userName',width:145,align:'center'">用户</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>

        <div id="user_data_dialog_center" style="width: 100px" data-options="region:'center',border:0">
            <div style="margin-top:90px;text-align:center">
                <input type="button" value=">>" onclick="appendToGroup();"><br>
                <input type="button" value="<<" onclick="deleteFromGroup();">
            </div>
        </div>

        <div id="user_data_dialog_east" style="width: 250px" data-options="region:'east',border:0">
            <div>
                <table id="user_data_dialog_right" class="easyui-datagrid" style="width:150px;height: 240px">
                    <thead>
                    <tr>
                        <th data-options="field:'userName',width:145,align:'center'">组员</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>


        <div id="user_data_dialog_south" style="height: 70px" data-options="region:'south',border:0">
            <div style="text-align:center">
                <hr color="powderblue">
                <input type="button" value="确定" onclick="groupConfirm();">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="取消" onclick="groupCancel();">
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        $('#account_data_table').datagrid({
            nowrap: false,
            striped: true,
            border: true,
            collapsible: false,
            loadMsg: '数据装载中......',
            url: '<%=basePath%>/getGroupByFilter.do',
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
            ]
        });

        $('#account_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });
    });

    $(function () {
        getGroupData('<%=basePath%>/rest/group/getGroupByFilter');
        getUserData("/cost/rest/user/getUserByFilter");
    });
    //点击行操作
    var rowIndex = undefined;
    function onClickRow(index) {
        if (actionType != undefined) {
            $('#group_data_table').datagrid('selectRow', rowIndex);
        } else {
            rowIndex = index;
        }
    }
</script>