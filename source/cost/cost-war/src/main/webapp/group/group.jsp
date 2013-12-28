<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
	<div id="group_data_north"
	     data-options="region:'north',border:0,fit:true">
		<table id="group_data_table" class="easyui-datagrid"
		       data-options="rownumbers:true,singleSelect:true,pagination:true,fit:true,fitColumns:true,toolbar:'#group_filter_bar',onClickRow:onClickRow">
			<thead>
			<tr>
				<th data-options="field:'groupName',width:80,align:'center',editor:'text'">用户名</th>
				<th data-options="field:'loginName',width:80,align:'center',editor:'text'">登录名</th>
				<th data-options="field:'password',width:80,align:'center',editor:'text'">密码</th>
				<th data-options="field:'groupAge',width:80,align:'center',editor:'numberbox'">年龄</th>
				<th data-options="field:'groupAddress',width:120,align:'center',editor:'text'">地址</th>
				<th data-options="field:'groupEmail',width:120,align:'center',editor:'text'">邮箱</th>
				<th data-options="field:'isAdminName',width:120,align:'center',formatter:showIsAdminText,
						editor:{
                            type:'combobox',
                            options:{
                                data:isAdminData,
                                valueField:'value',
                                textField:'name'
                            }}"
						>是否管理员</th>
				<th data-options="field:'groupStatusName',width:120,align:'center',formatter:showgroupStatusText,
						editor:{
                            type:'combobox',
                            options:{
                                data:groupStatusData,
                                valueField:'value',
                                textField:'name'
                            }}"
						>用户状态</th>
				<th data-options="field:'groupRemark',width:120,align:'center',editor:'text'">备注</th>
			</tr>
			</thead>
		</table>
	</div>
</div>