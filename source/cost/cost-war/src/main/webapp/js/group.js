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
function addGroup()
{
    display();
}
//组修改操作
function editGroup()
{
    var selectRow = $('#group_data_table').datagrid('getSelected');
    var userIdArr = selectRow.userIds.split(",");
    var leftUserRows = $("#user_data_table_left").datagrid("getRows");
    var appendLeftUserRows = undefined;
    for (var i = 0; i < leftUserRows.length; i++)
    {
        var leftUserId = leftUserRows[i].userId;
        for (var j = 0; j < userIdArr.length; j++)
        {
            var rightUserId = userIdArr[j];
            if (leftUserId != rightUserId)
            {
                appendLeftUserRows = appendLeftUserRows == undefined ? leftUserId : "," + leftUserId;
            }
        }
    }
    $("#user_data_table_left").datagrid("deleteRow", leftUserId);
    display();
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
function groupSave(httpUrl)
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
        {
            userIds = userId;
        } else
        {
            userIds += "," + userId;
        }
    }
    var jsonData = '{"groupName":"' + groupName + '","userIds":"' + userIds + '"}';
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
}
//点击“取消”按钮操作
function groupCancel()
{
    noDisplay();
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