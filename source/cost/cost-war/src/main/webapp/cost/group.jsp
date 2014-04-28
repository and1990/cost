<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<!--显示已创建的组信息-->
<div id="group_data_layout" class="easyui-layout" data-options="fit:true">
    <div id="group_data_north" data-options="region:'north',border:0,fit:true" style="height: 280px">
        <table id="group_data_table">
            <thead>
            <tr>
                <th data-options="field:'groupName',width:80,align:'center',editor:'text'">组名</th>
                <th data-options="field:'userIds',width:80,align:'center',editor:'text',hidden:true"/>
                <th data-options="field:'userNames',width:120,align:'center',
                    editor:{type:'combobox', options:{ editable:false, onShowPanel:openUserDialog}}">组员
                </th>
                <th data-options="field:'groupStatus',width:60,align:'center',hidden:true"/>
                <th data-options="field:'groupStatusName',width:60,align:'center'">状态</th>
                <th data-options="field:'groupRemark',width:120,align:'center',editor:'text'">备注</th>
                <th data-options="field:'createUser',width:120,align:'center'">创建人</th>
                <th data-options="field:'createTime',width:120,align:'center'">创建时间</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<!-- 工具栏 -->
<div id="group_filter_bar" style="padding: 5px; height: auto">
    <div style="margin-bottom: 5px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addGroup();">增加</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="modifyGroup();">修改</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteGroup();">删除</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveGroup();">保存</a>
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
    //初始化
    $(function () {
        $('#group_data_table').datagrid({
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
            selectOnCheck: true,
            checkOnSelect: true,
            pagination: true,
            toolbar: "#group_filter_bar"
        });

        //设置分页
        $('#group_data_table').datagrid('getPager').pagination({
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            beforePageText: '第',
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from}-{to} 条记录   共 {total} 条记录'
        });

        //获取用户数据
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "<%=basePath%>/getUserByFilter.do",
            dataType: 'json',
            success: function (resultData) {
                $('#user_data_dialog_left').datagrid('loadData', resultData.rows);
            }
        });
    });

    var rowIndex = undefined;
    var action = undefined;
    //增加组
    function addGroup() {
        $("#group_data_table").datagrid('appendRow', {"groupStatus": "1", "groupStatusName": "可用"});
        rowIndex = $("#group_data_table").datagrid('getRows').length - 1;
        $("#group_data_table").datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        action = 1;
    }
    // 修改组
    function modifyGroup() {
        $("#group_data_table").datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
        action = 2;
    }
    // 删除组
    function deleteGroup() {
        if (window.confirm("确定删除？")) {
            var jsonData = JSON.stringify(sendData);
            sendRequest(jsonData, url);
            $("#group_data_table").datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
            action = 3;
        }
    }
    //保存组
    function saveGroup() {
        if (action == undefined) {
            return;
        }
        $('#group_data_table').datagrid('acceptChanges');
        $('#group_data_table').datagrid("selectRow", rowIndex);
        var sendData = $('#group_data_table').datagrid('getSelected')
        sendData.userIds = getSelectUserIds();
        var jsonData = JSON.stringify(sendData);
        console.info(jsonData);
        var url = "<%=basePath%>/addGroup.do";
        if (action == 2) {
            url = "<%=basePath%>/modifyGroup.do";
        } else if (action == 3) {
            url = "<%=basePath%>/deleteGroup.do";
        }
        $.ajax({
            type: "POST",
            data: jsonData,
            url: url,
            success: function (data) {
                $('#group_data_table').datagrid('load');
            },
            error: function (data) {
                alert("系统错误，请联系管理员");
            }
        });
    }
    //弹出选择用户对话框
    function openUserDialog(index, field, value) {
        $('.combobox-f').combo('hidePanel');
        $('.combobox-f').attr('id', 'select-user_combo');
        $('#user_data_div').dialog('open');
        if (action == 2) {
            editGroup();
        }
    }
    //组修改操作
    function editGroup() {
        var leftUserRowArr = new Array();
        var rightUserRowArr = new Array();
        var selectRow = $('#group_data_table').datagrid('getSelected');
        var userIdArr = selectRow.userIds.split(",");
        var allUserRows = $("#user_data_dialog_left").datagrid("getRows");
        for (var i = 0; i < allUserRows.length; i++) {
            var isContain = false;
            for (var j = 0; j < userIdArr.length; j++) {
                if (allUserRows[i].userId == userIdArr[j]) {
                    isContain = true;
                    rightUserRowArr.push(allUserRows[i]);
                }
            }
            if (!isContain) {
                leftUserRowArr.push(allUserRows[i]);
            }
        }
        $('#user_data_dialog_left').datagrid('loadData', { total: 0, rows: [] });
        for (var i = 0; i < leftUserRowArr.length; i++) {
            var row = leftUserRowArr[i];
            $("#user_data_dialog_left").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
        }
        for (var i = 0; i < rightUserRowArr.length; i++) {
            var row = rightUserRowArr[i];
            $("#user_data_dialog_right").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
        }
    }

    //点击“>>”按钮操作
    function appendToGroup() {
        appendOrDelete("#user_data_dialog_left", "#user_data_dialog_right");
    }
    //点击“<<”按钮操作
    function deleteFromGroup() {
        appendOrDelete("#user_data_dialog_right", "#user_data_dialog_left");
    }
    //点击”确定“按钮操作
    function groupConfirm() {
        var allRows = $("#user_data_dialog_right").datagrid("getRows");
        if (allRows.length <= 0) {
            alert("请添加用户");
            return;
        }
        var selectUserNames = getSelectUserNames();
        $('#select-user_combo').combo('setText', selectUserNames);
        $('#user_data_div').dialog('close');
    }
    //点击“取消”按钮操作
    function groupCancel() {
        $('#user_data_div').dialog('close');
        actionType = undefined;
    }
    //组操作
    function appendOrDelete(from, to) {
        var selectRows = $(from).datagrid("getSelections");
        for (var i = 0; i < selectRows.length; i++) {
            var row = selectRows[i];
            $(to).datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
            var rowIndex = $(from).datagrid("getRowIndex", row);
            $(from).datagrid("deleteRow", rowIndex);
        }
    }
    //得到选择的用户ID
    function getSelectUserIds() {
        var userIds = undefined;
        var allRows = $("#user_data_dialog_right").datagrid("getRows");
        for (var index = 0; index < allRows.length; index++) {
            var row = allRows[index];
            if (userIds == undefined) {
                userIds = row.userId;
            }
            else {
                userIds += "," + row.userId;
            }
        }
        return userIds;
    }
    //得到选择的用户名称
    function getSelectUserNames() {
        var userNames = undefined;
        var allRows = $("#user_data_dialog_right").datagrid("getRows");
        for (var index = 0; index < allRows.length; index++) {
            var row = allRows[index];
            if (userNames == undefined) {
                userNames = row.userName;
            }
            else {
                userNames += "," + row.userName;
            }
        }
        return userNames;
    }
</script>