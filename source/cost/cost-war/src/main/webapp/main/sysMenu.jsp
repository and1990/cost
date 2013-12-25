<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<div class="easyui-accordion" data-options="fit:true,border:false" id="main_left_accordion">
	<!-- 用户信息 -->
	<div title="用户信息" data-options="selected:true" style="padding: 10px;">
		<ul class="easyui-tree" data-options="animate:true" id="user_tree">
			<li><span>用户信息</span></li>
		</ul>
	</div>
	<!-- 费用报销 -->
	<div title="报销管理" style="padding: 10px;">
		<ul class="easyui-tree" data-options="animate:true" id="account_tree">
			<li><span>账单信息</span></li>
		</ul>
	</div>
</div>

<script type="text/javascript">
	$("#user_tree").tree({
		onClick: function(node){
			if("用户信息"===node.text){
				openTab(node.text,"user/userData.jsp");
			}
		}
	});
	
	
	$("#account_tree").tree({
		onClick: function(node){
			if("账单信息"===node.text){
				openTab(node.text,"account/accountData.jsp");
			}
		}
	}); 

	function openTab(text,url){
		if ($('#main_tabs').tabs('exists',text)){
			$('#main_tabs').tabs('select', text);
		} else {
			$('#main_tabs').tabs('add',{
				title:text,
				href:url,
				closable:true
			});
		}
	}
</script>