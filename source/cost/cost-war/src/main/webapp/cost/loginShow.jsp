<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8" %>

<!-- 基本信息界面 -->
<div id="main_base" class="easyui-layout" style="height: 500px;"
     data-options="fit:true">

    <!-- 用户信息 -->
    <div id="baseMsg_baseInfo" data-options="region:'north',border:0"
         style="height: 180px;" title="用户信息">
        <table id="baseMsg_userTable" style="height: 150px"
               data-options="url:'datagrid_data1.json',method:'post',border:false,singleSelect:true,fitColumns:true,fit:true">
            <tr>
                <th rowspan="3"><img src="imags/Background.jpeg"/></th>
                <td width="250px">姓名：刘腾飞</td>
                <th width="350px" style="font-weight:300; font-size:12px">上次登录时间：2013年11月16日12:45:30</th>
            </tr>
            <tr>
                <td>年龄：23</td>
                <th style="font-weight:300; font-size:12px">本次登录时间：2013年11月16日12:45:30</th>
            </tr>
            <tr>
                <td>联系电话：18612081242</td>
                <th style="font-weight:300; font-size:12px">上次登录时间：2013年11月16日12:45:30</th>
            </tr>
        </table>
    </div>

    <!-- 报销信息 -->
    <div id="baseMsg_costInfo" data-options="region:'center',border:0" title="报销信息">
        <table class="easyui-datagrid"
               data-options="rownumbers:true,url:'datagrid_data1.json',method:'post',border:false,singleSelect:true,fitColumns:true,fit:true">
            <thead>
            <tr>
                <th data-options="field:'userName',width:80,align:'center'">消费用户</th>
                <th data-options="field:'accountMoney',width:80,align:'center'">消费金额</th>
                <th data-options="field:'accountTypeName',width:80,align:'center'">消费类型</th>
                <th data-options="field:'accountPartner',width:80,align:'center'">消费人</th>
                <th data-options="field:'accountTime',width:80,align:'center'">消费时间</th>
                <th data-options="field:'approveTime',width:80,align:'center'">审批时间</th>
                <th data-options="field:'isApproveName',width:80,align:'center'">审批通过</th>
                <th data-options="field:'accountRemark',width:120,align:'center'">备注</th>
                <th data-options="field:'modifyUser',width:120,align:'center'">修改人</th>
                <th data-options="field:'modifyTime',width:120,align:'center'">修改时间</th>
                <th data-options="field:'createUser',width:120,align:'center'">创建人</th>
                <th data-options="field:'createTime',width:140,align:'center'">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>

    <!-- 系統信息 -->
    <div id="baseMsg_sysInfo"
         data-options="region:'south',border:0" style="height: 120px;"
         title="系统信息">
        <div id="baseMsg_baseInfo" data-options="region:'north'"
             title="用户信息">
            <table id="baseMsg_userTable" style="height: 90px"
                   data-options="url:'datagrid_data1.json',method:'post',border:false,singleSelect:true,fitColumns:true,fit:true">
                <tr>
                    <td>年龄：23</td>
                    <th style="font-weight:300; font-size:12px">本次登录时间：2013年11月16日12:45:30</th>
                </tr>
                <tr>
                    <td>联系电话：18612081242</td>
                    <th style="font-weight:300; font-size:12px">上次登录时间：2013年11月16日12:45:30</th>
                </tr>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $("#main_base").layout();
    $("#main_base").layout('collapse', 'south');
</script>
