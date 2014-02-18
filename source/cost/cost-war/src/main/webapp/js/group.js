//得到组数据
function getGroupData(httpUrl)
{
    $.ajax({
        type: "POST",
        data: '{}',
        contentType: "application/json",
        url: httpUrl,
        dataType: 'json',
        success: function (resultData)
        {
            $('#group_data_table').datagrid('loadData', resultData.data);
        }
    });
}
//弹出选择用户对话框
function openUserDialog(index, field, value)
{
    $('.combobox-f').combo('hidePanel');
    $('.combobox-f').attr('id', 'select-user_combo');
    $('#user_data_div').dialog('open');
    if (actionType == 1)
    {
        editGroup();
    }
}
//组修改操作
function editGroup()
{
    var leftUserRowArr = new Array();
    var rightUserRowArr = new Array();
    var selectRow = $('#group_data_table').datagrid('getSelected');
    var userIdArr = selectRow.userIds.split(",");
    var allUserRows = $("#user_data_dialog_left").datagrid("getRows");
    for (var i = 0; i < allUserRows.length; i++)
    {
        var isContain = false;
        for (var j = 0; j < userIdArr.length; j++)
        {
            if (allUserRows[i].userId == userIdArr[j])
            {
                isContain = true;
                rightUserRowArr.push(allUserRows[i]);
            }
        }
        if (!isContain)
        {
            leftUserRowArr.push(allUserRows[i]);
        }
    }
    $('#user_data_dialog_left').datagrid('loadData', { total: 0, rows: [] });
    for (var i = 0; i < leftUserRowArr.length; i++)
    {
        var row = leftUserRowArr[i];
        $("#user_data_dialog_left").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
    }
    for (var i = 0; i < rightUserRowArr.length; i++)
    {
        var row = rightUserRowArr[i];
        $("#user_data_dialog_right").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
    }
}
//保存组
function saveGroup()
{
    if (actionType == undefined)
    {
        return;
    }
    $('#group_data_table').datagrid('acceptChanges');
    $('#group_data_table').datagrid("selectRow", rowIndex);
    var sendData = $('#group_data_table').datagrid('getSelected')
    sendData.userIds = getSelectUserIds();
    var jsonData = JSON.stringify(sendData);
    sendRequest(jsonData, httpUrl);
    actionType = undefined;
}
//点击“>>”按钮操作
function appendToGroup()
{
    appendOrDelete("#user_data_dialog_left", "#user_data_dialog_right");
}
//点击“<<”按钮操作
function deleteFromGroup()
{
    appendOrDelete("#user_data_dialog_right", "#user_data_dialog_left");
}
//点击”确定“按钮操作
function groupConfirm()
{
    var allRows = $("#user_data_dialog_right").datagrid("getRows");
    if (allRows.length <= 0)
    {
        alert("请添加用户");
        return;
    }
    var selectUserNames = getSelectUserNames();
    $('#select-user_combo').combo('setText', selectUserNames);
    $('#user_data_div').dialog('close');
}
//点击“取消”按钮操作
function groupCancel()
{
    $('#user_data_div').dialog('close');
    actionType = undefined;
}
//得到用户数据
function getUserData(httpUrl)
{
    $.ajax({
        type: "POST",
        data: '{}',
        contentType: "application/json",
        url: httpUrl,
        dataType: 'json',
        success: function (resultData)
        {
            $('#user_data_dialog_left').datagrid('loadData', resultData.data);
        }
    });
}
//组操作
function appendOrDelete(from, to)
{
    var selectRows = $(from).datagrid("getSelections");
    for (var i = 0; i < selectRows.length; i++)
    {
        var row = selectRows[i];
        $(to).datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
        var rowIndex = $(from).datagrid("getRowIndex", row);
        $(from).datagrid("deleteRow", rowIndex);
    }
}
//得到选择的用户ID
function getSelectUserIds()
{
    var userIds = undefined;
    var allRows = $("#user_data_dialog_right").datagrid("getRows");
    for (var index = 0; index < allRows.length; index++)
    {
        var row = allRows[index];
        if (userIds == undefined)
        {
            userIds = row.userId;
        }
        else
        {
            userIds += "," + row.userId;
        }
    }
    return userIds;
}
//得到选择的用户名称
function getSelectUserNames()
{
    var allRows = $("#user_data_dialog_right").datagrid("getRows");
    var userNames = undefined;
    for (var index = 0; index < allRows.length; index++)
    {
        var row = allRows[index];
        if (userNames == undefined)
        {
            userNames = row.userName;
        }
        else
        {
            userNames += "," + row.userName;
        }
    }
    return userNames;
}
