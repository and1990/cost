var actionType = undefined;
var httpUrl = undefined;
//var jsonData = undefined;
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
//组增加操作
function addGroup(url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 0;
    display();
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
    var allUserRows = $("#user_data_table_left").datagrid("getRows");
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
    $('#user_data_table_left').datagrid('loadData', { total: 0, rows: [] });
    for (var i = 0; i < leftUserRowArr.length; i++)
    {
        var row = leftUserRowArr[i];
        $("#user_data_table_left").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
    }
    for (var i = 0; i < rightUserRowArr.length; i++)
    {
        var row = rightUserRowArr[i];
        $("#user_data_table_right").datagrid("appendRow", {'userName': row.userName, 'userId': row.userId});
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
    appendOrDelete("#user_data_table_left", "#user_data_table_right");
}
//点击“<<”按钮操作
function deleteFromGroup()
{
    appendOrDelete("#user_data_table_right", "#user_data_table_left");
}
//点击”保存“按钮操作
function groupSave()
{
    var groupName = $("#group_name_text").val();
    if (groupName == undefined || groupName.length == 0)
    {
        alert("请输入组名称");
        return;
    }
    var allRows = $("#user_data_table_right").datagrid("getRows");
    if (allRows.length <= 0)
    {
        alert("请添加用户");
        return;
    }
    var userIds = undefined;
    for (var i = 0; i < allRows.length; i++)
    {
        var userId = allRows[i].userId;
        if (userIds == undefined)
            userIds = userId;
        else
            userIds += "," + userId;
    }
    var jsonData = undefined;
    if (actionType == 0)
    {
        jsonData = '{"groupName":"' + groupName + '","userIds":"' + userIds + '"}';
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
    actionType = undefined;
}
//点击“取消”按钮操作
function groupCancel()
{
    noDisplay();
    actionType = undefined;
}
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
            $('#user_data_table_left').datagrid('loadData', resultData.data);
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
//显示“组增加、修改”界面
function display()
{
    $("#group_data_west").fadeIn(500);
    $("#group_data_center").fadeIn(500);
    $("#group_data_east").fadeIn(500);
    $("#group_data_south").fadeIn(500);
}
//不显示“组增加、修改”界面
function noDisplay()
{
    $("#group_data_west").fadeOut(500);
    $("#group_data_center").fadeOut(500);
    $("#group_data_east").fadeOut(500);
    $("#group_data_south").fadeOut(500);
}