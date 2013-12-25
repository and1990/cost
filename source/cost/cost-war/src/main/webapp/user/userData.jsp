<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<% 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath(); 
%>
<base href='<%=basePath %>'> 
<div id="user_data_layout" class="easyui-layout" data-options="fit:true">
	<div id="user_data_north"
		data-options="region:'north',border:0,fit:true">
		<table id="user_data_table" class="easyui-datagrid"
			data-options="rownumbers:true,singleSelect:true,pagination:true,fit:true,fitColumns:true,toolbar:'#user_filter_bar',onClickRow:onClickRow">
			<thead>
				<tr>
					<th data-options="field:'userName',width:80,align:'center',editor:'text'">用户名</th>
					<th data-options="field:'loginName',width:80,align:'center',editor:'text'">登录名</th>
					<th data-options="field:'password',width:80,align:'center',editor:'text'">密码</th>
					<th data-options="field:'userAge',width:80,align:'center',editor:'numberbox'">年龄</th>
					<th data-options="field:'userAddress',width:120,align:'center',editor:'text'">地址</th>
					<th data-options="field:'userEmail',width:120,align:'center',editor:'text'">邮箱</th>
					<th data-options="field:'isAdminName',width:120,align:'center',formatter:showIsAdminText,
						editor:{
                            type:'combobox',
                            options:{
                                data:isAdminData,
                                valueField:'value',
                                textField:'name'
                            }}"
                     >是否管理员</th>
					<th data-options="field:'userStatusName',width:120,align:'center',formatter:showUserStatusText,
						editor:{
                            type:'combobox',
                            options:{
                                data:userStatusData,
                                valueField:'value',
                                textField:'name'
                            }}"
                    >用户状态</th>
					<th data-options="field:'userRemark',width:120,align:'center',editor:'text'">备注</th>
					<!-- <th data-options="field:'loginTime',width:140,align:'center'">登录时间</th>
					<th data-options="field:'modifyUser',width:120,align:'center'">修改人</th>
					<th data-options="field:'modifyTime',width:120,align:'center'">修改时间</th>
					<th data-options="field:'createUser',width:120,align:'center'">创建人</th>
					<th data-options="field:'createTime',width:140,align:'center'">创建时间</th> -->
				</tr>
			</thead>
		</table>
	</div>
</div>
<div id="user_filter_bar" style="padding: 5px; height: auto">
	<div style="margin-bottom: 5px">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addData('#user_data_table');">增加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editData('#user_data_table');">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeData('#user_data_table');">删除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="undoData('#user_data_table');">撤销</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveData('#user_data_table');">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges('#user_data_table')">GetChanges</a>
	</div>
	<div>
		创建时间从: <input class="easyui-datebox" style="width: 80px"> 
		到: <input class="easyui-datebox" style="width: 80px">
		<a href="#"	class="easyui-linkbutton" iconCls="icon-search">查询</a>
	</div>
</div>
<script type="text/javascript">
		//界面加载完成，加载数据
		$(function(){
			$.ajax({
		        type: "POST",
		        data:'{}',
		        contentType: "application/json",
		        url:'<%=basePath%>/rest/user/getUserByFilter',
		        dataType:'json',
		        success: function(resultData) {
		        	$('#user_data_table').datagrid('loadData', resultData.data);
		        }
		    }); 
		});
		
		function onClickRow(index){
			if(actionType != undefined){
				$('#user_data_table').datagrid('selectRow', rowIndex);
			}else{
				rowIndex = index;
			}
		}
		
		//增加用户保存时，显示下拉框的文本
		var isAdminData = [{'value': '1', 'name': '否'},{'value': '2', 'name': '是'}];
		var userStatusData = [{'value': '1', 'name': '不可用'},{'value': '2', 'name': '可用'}];
		
		function showIsAdminText(value){
			for (var i = 0; i < isAdminData.length; i++) {
				if (isAdminData[i].value == value)
				return isAdminData[i].name;
			}
			return value;
		}
		
		function showUserStatusText(value){
			for (var i = 0; i < userStatusData.length; i++) {
				if (userStatusData[i].value == value)
				return userStatusData[i].name;
			}
			return value;
		}
		
</script>