<%@page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>
<div class="easyui-accordion" data-options="fit:true,border:false" id="main_left_accordion">
	<!-- 用户信息 -->
	<div title="用户信息" data-options="selected:true" style="padding: 10px;">
		<ul class="easyui-tree" data-options="animate:true" id="user_tree">
			<li>
				<a href="#" style="text-decoration:none;" onclick="openTab('userData','用户信息')">用户信息</a>
			</li>
			<li>
				<a href="#" style="text-decoration:none;" onclick="openTab('userChange','人员变更')">人员变更</a>
			</li>
		</ul>
	</div>
	<!-- 费用报销 -->
	<div title="我的消费" style="padding: 10px;">
		<ul class="easyui-tree" data-options="animate:true" id="account_tree">
			<li>
				<a style="text-decoration:none;" href="#" onclick="openTab('myAccountData','我的消费')">我的消费</a>
			</li>
			<li>
				<a style="text-decoration:none;" href="#" onclick="openTab('groupAccountData','组消费')">组消费</a>
			</li>
		</ul>
	</div>
</div>

<script type="text/javascript">
	//第一种方式
	/*$("#user_tree").tree({
	 onClick: function (node)
	 {
	 if ("用户信息" === node.text)
	 {
	 openTab(node.text, "user/userData.jsp");
	 } else if ("" === node.text)
	 {
	 }
	 }
	 });
	 $("#account_tree").tree({
	 onClick: function (node)
	 {
	 if ("账单信息" === node.text)
	 {
	 openTab(node.text, "account/accountData.jsp");
	 }
	 }
	 });*/
	//第二种方式
	function openTab(menuId, menuText)
	{
		var url = undefined;
		if ("userData" === menuId)
		{
			url = "user/userData.jsp";
		} else if ("userChange" === menuId)
		{
			url = "group/group.jsp";
		} else if ("myAccountData" === menuId)
		{
			url = "account/accountData.jsp";
		} else if ("groupAccountData" === menuId)
		{
			//TODO
		}
		if ($('#main_tabs').tabs('exists', menuText))
		{
			$('#main_tabs').tabs('select', menuText);
		} else
		{
			$('#main_tabs').tabs('add', {
				title: menuText,
				href: url,
				closable: true
			});
		}
	}
</script>