var actionType = undefined;
var httpUrl = undefined;
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
//点击行操作
function onClickRow(index)
{
    if (actionType != undefined)
    {
        $('#group_data_table').datagrid('selectRow', rowIndex);
    } else
    {
        rowIndex = index;
    }
}
//弹出选择用户对话框
function openUserDialog(index, field, value)
{
    $('.combobox-f').combo('hidePanel');
    $('#user_data_div').dialog('open');
    getUserData("/cost/rest/user/getUserByFilter");
}
//组增加操作
function addGroup(url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 0;
    getUserData("/cost/rest/user/getUserByFilter");
    httpUrl = url;
}
//组修改操作
function editGroup(url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 1;
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
    display();
    $("#group_name_text").val(selectRow.groupName);
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
    httpUrl = url;
}
//删除操作
function deleteGroup(url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 2;
    if (!window.confirm("是否删除？"))
    {
        actionType = undefined;
        return;
    }
    var groupId = $('#group_data_table').datagrid('getSelected').groupId;
    url += "?groupId=" + groupId;
    $.ajax({
        type: "POST",
        data: '{}',
        contentType: "application/json",
        url: url,
        dataType: 'json',
        success: function (resultData)
        {
            alert("操作成功");
        }
    });
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
function groupSave()
{
    var allRows = $("#user_data_dialog_right").datagrid("getRows");
    if (allRows.length <= 0)
    {
        alert("请添加用户");
        return;
    }
    var selectUserNames = getSelectUserNames(allRows);
    $('.combobox-f').combo('setText',selectUserNames);
    $('#user_data_div').dialog('close');
    /*var jsonData = undefined;
     if (actionType == 0)
     {
     jsonData = '{"userIds":"' + userIds + '"}';
     } else if (actionType == 1)
     {
     var selectData = $('#group_data_table').datagrid('getSelected');
     selectData.userIds = userIds;
     jsonData = JSON.stringify(selectData);
     }
     $.ajax({
     type: "POST",
     data: jsonData,
     contentType: "application/json",
     url: httpUrl,
     dataType: 'json',
     success: function (resultData)
     {
     alert("操作成功");
     }
     });
     actionType = undefined;*/
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
function getSelectUserIds(allRows)
{
    var userIds = undefined;
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
function getSelectUserNames(allRows)
{
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
            userNames += ","+row.userName;
        }
    }
    return userNames;
}
