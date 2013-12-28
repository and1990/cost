<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
	<!--显示已创建的组信息-->
	<div id="group_data_north"
	     data-options="region:'north',border:0" style="height: 280px">
		<table id="group_data_table" class="easyui-datagrid"
		       data-options="rownumbers:true,singleSelect:true,fit:true,pagination:true,fitColumns:true,
		       toolbar:'#group_filter_bar',onClickRow:onClickRow">
			<thead>
			<tr>
				<th data-options="field:'groupNames',width:80,align:'center',editor:'text'">用户</th>
				<th data-options="field:'groupStatus',width:80,align:'center',editor:'text'">状态</th>
				<th data-options="field:'groupRemark',width:120,align:'center',editor:'text'">备注</th>
				<th data-options="field:'createUser',width:120,align:'center'">创建人</th>
				<th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
			</tr>
			</thead>
		</table>
	</div>

	<!--点击增加、修改按钮时，显示界面-->
	<div id="group_data_west" style="width: 430px" data-options="region:'west',border:0">
		<div style="margin-left:230px">
			<table id="user_data_table_left" class="easyui-datagrid" style="width: 200px;height: 240px">
				<thead>
				<tr>
					<th data-options="field:'userName',width:180,align:'center'">用户</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>

	<div id="group_data_center" style="width: 140px" data-options="region:'center',border:0">
		<div style="margin-top:90px;text-align:center">
			<input type="button" value=">>" onclick="appendToGroup();">
			<br><br><br>
			<input type="button" value="<<" onclick="deleteFromGroup();">
		</div>
	</div>

	<div id="group_data_east" style="width: 430px" data-options="region:'east',border:0">
		<div id="">
			<table id="user_data_table_right" class="easyui-datagrid" style="width:200px;height: 240px">
				<thead>
				<tr>
					<th data-options="field:'userName',width:180,align:'center'">组员</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>


	<div id="group_data_south" style="height: 60px" data-options="region:'south',border:0">
		<div style="text-align:center">
			<hr color="lightBlue">
			<input type="button" value="确定">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="取消" onclick="groupCancel();">
		</div>
	</div>
</div>

<div id="group_filter_bar" style="padding: 5px; height: auto">
	<div style="margin-bottom: 5px">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		   onclick="addGroup();">增加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
		   onclick="editData('#group_data_table', '<%=basePath%>/rest/group/modifyGroup');">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		   onclick="removeData('#group_data_table', '<%=basePath%>/rest/group/deleteGroup');">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true"
		   onclick="undoData('#group_data_table');">撤销</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"
		   onclick="saveData('#group_data_table');">保存</a>
	</div>
</div>


<script>
	//页面加载完成触发
	$(function () {
		noDisplay();
		getUserData();
	});

	//点击行操作
	function onClickRow(index) {
		if (actionType != undefined) {
			$('#group_data_table').datagrid('selectRow', rowIndex);
		} else {
			rowIndex = index;
		}
	}

	//组增加操作
	function addGroup() {
		display();
	}

	//点击“>>”按钮操作
	function appendToGroup() {
		appendOrDelete("#user_data_table_left","#user_data_table_right");
	}

	//点击“<<”按钮操作
	function deleteFromGroup() {
		appendOrDelete("#user_data_table_right","#user_data_table_left");
	}

	//点击“取消”按钮操作
	function groupCancel() {
		noDisplay();
	}

	//得到用户数据
	function getUserData() {
		$.ajax({
			type: "POST",
			data: '{}',
			contentType: "application/json",
			url: '<%=basePath%>/rest/user/getUserByFilter',
			dataType: 'json',
			success: function (resultData) {
				$('#user_data_table_left').datagrid('loadData', resultData.data);
			}
		});
	}

	//组操作
	function appendOrDelete(from,to) {
		var selectRows = $(from).datagrid("getSelections");
		for (var i = 0; i < selectRows.length; i++) {
			var row = selectRows[i];
			$(to).datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
			var rowIndex = $(from).datagrid("getRowIndex", row);
			$(from).datagrid("deleteRow", rowIndex);
		}
	}

	//显示“组增加、修改”界面
	function display() {
		$("#group_data_west").fadeIn(500);
		$("#group_data_center").fadeIn(500);
		$("#group_data_east").fadeIn(500);
		$("#group_data_south").fadeIn(500);
	}

	//不显示“组增加、修改”界面
	function noDisplay() {
		$("#group_data_west").fadeOut(500);
		$("#group_data_center").fadeOut(500);
		$("#group_data_east").fadeOut(500);
		$("#group_data_south").fadeOut(500);
	}
</script>