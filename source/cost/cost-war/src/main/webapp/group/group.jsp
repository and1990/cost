<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<!--显示已创建的组信息-->
<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
	<div id="group_data_north"
	     data-options="region:'north',border:0" style="height: 280px">
		<table id="group_data_table" class="easyui-datagrid"
		       data-options="rownumbers:true,singleSelect:true,fit:true,pagination:true,fitColumns:true,
		       toolbar:'#group_filter_bar',onClickRow:onClickRow">
			<thead>
			<tr>
				<th data-options="field:'groupName',width:80,align:'center',editor:'text'">组名</th>
				<th data-options="field:'userNames',width:80,align:'center',editor:{
                            type:'combobox',
                            options:{
							  editable:false,
                               onShowPanel:openUserDialog
                            }}"
						>组员
				</th>
				<th data-options="field:'userStatusName',width:120,align:'center',formatter:showStatusText,
						editor:{
                            type:'combobox',
                            options:{
                                data:statusData,
                                valueField:'value',
                                textField:'name'
                            }}"
						>用户状态
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
	<%--<div style="margin-bottom: 5px">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		   onclick="addGroup('<%=basePath%>/rest/group/addGroup');">增加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
		   onclick="editGroup('<%=basePath%>/rest/group/updateGroup');">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		   onclick="deleteGroup('<%=basePath%>/rest/group/deleteGroup');">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
		   onclick="undoData('#group_data_table');">撤销</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
		   onclick="saveData('#group_data_table');">保存</a>
	</div>--%>
	<div style="margin-bottom: 5px">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		   onclick="addData('#group_data_table','<%=basePath%>/rest/account/addAccount');">增加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
		   onclick="editData('#group_data_table','<%=basePath%>/rest/account/modifyAccount');">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		   onclick="removeData('#group_data_table','<%=basePath%>/rest/account/deleteAccount');">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
		   onclick="undoData('#group_data_table');">撤销</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
		   onclick="saveData('#group_data_table');">保存</a>
	</div>
</div>


<!--点击增加、修改按钮时，显示界面-->
<div id="user_data_div" class="easyui-dialog" title="选择用户" style="width:600px;height:400px"
     data-options="closed: true">
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
				<input type="button" value="确定" onclick="groupSave();">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="取消" onclick="groupCancel();">
			</div>
		</div>
	</div>
</div>

<script>
	//页面加载完成触发
	$(function ()
	{
		getGroupData('<%=basePath%>/rest/group/getGroupByFilter');
		<%--getUserData('<%=basePath%>/rest/user/getUserByFilter');--%>
	});
</script>