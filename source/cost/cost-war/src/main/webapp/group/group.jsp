<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
	<!--显示已创建的组信息-->
	<div id="group_data_north"
	     data-options="region:'north'" style="height: 280px">
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
	<div id="group_data_west" style="width: 450px" data-options="region:'west',border:1">
		<div style="margin-left:248px">
			<table class="easyui-datagrid" style="width: 200px">
				<thead>
				<tr>
					<th data-options="field:'userName',width:200,align:'center'">用户</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>

	<div id="group_data_center" style="width: 100px" data-options="region:'center',border:1">
		<div style="margin-top:90px;text-align:center">
			<input type="button" value=">>"><br><br>
			<input type="button" value="<<">
		</div>
	</div>

	<div id="group_data_east" style="width: 450px" data-options="region:'east',border:1">
		<div id="">
			<table class="easyui-datagrid" style="width:200px">
				<thead>
				<tr>
					<th data-options="field:'userName',width:200,align:'center'">组员</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>

	<div id="group_data_south" style="height: 30px" data-options="region:'south',border:1">
		<div style="text-align:center">
			<input type="button" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
		$.ajax({
			type: "POST",
			data: '{}',
			contentType: "application/json",
			url: '<%=basePath%>/rest/group/addGroup',
			dataType: 'json',
			success: function (resultData) {
				$('#user_data_table').datagrid('loadData', resultData.data);
			}
		});
	}

	//点击“取消”按钮操作
	function groupCancel() {
		noDisplay();
	}

	//显示“组增加、修改”界面
	function display() {
		$("#group_data_west").show();
		$("#group_data_center").show();
		$("#group_data_east").show();
		$("#group_data_south").show();
	}

	//不显示“组增加、修改”界面
	function noDisplay() {
		$("#group_data_west").fadeOut(500);
		$("#group_data_center").fadeOut(500);
		$("#group_data_east").fadeOut(500);
		$("#group_data_south").fadeOut(500);
	}
</script>