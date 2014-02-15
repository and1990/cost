// 行索引
var rowIndex = undefined;
// 操作类型，0：增加，1：修改，2：删除
var actionType = undefined;
// 发送URL
var httpUrl = undefined;
// 发送数据
var sendData = undefined;
// 增加记录
function addData(dataTableId, url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 0;
    $(dataTableId).datagrid('appendRow', {});
    rowIndex = $(dataTableId).datagrid('getRows').length - 1;
    $(dataTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
    httpUrl = url;
    sendData = $(dataTableId).datagrid('getSelected');
}
// 编辑记录
function editData(dataTableId, url)
{
    if (actionType != undefined)
    {
        return;
    }
    actionType = 1;
    $(dataTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex);
    httpUrl = url;
    sendData = $(dataTableId).datagrid('getSelected');
}
// 删除记录
function removeData(dataTableId, url)
{
    if (actionType != undefined)
    {
        return;
    }
    if (window.confirm("确定删除？"))
    {
        actionType = 2;
        sendData = $(dataTableId).datagrid('getSelected');
        var jsonData = JSON.stringify(sendData);
        sendRequest(jsonData, url);
        $(dataTableId).datagrid('cancelEdit', rowIndex).datagrid('deleteRow', rowIndex);
        actionType = undefined;
    }
}
// 保存记录
function saveData(dataTableId)
{
    if (actionType == undefined)
    {
        return;
    }
    $(dataTableId).datagrid('acceptChanges');
    $(dataTableId).datagrid("selectRow", rowIndex);
    var jsonData = JSON.stringify(sendData);
    sendRequest(jsonData, httpUrl);
    actionType = undefined;
}
// 撤销
function undoData(dataTableId)
{
    $(dataTableId).datagrid('rejectChanges');
    actionType = undefined;
}
// 变化行数
function getChanges(dataTableId)
{
    var rows = $(dataTableId).datagrid('getChanges');
    alert(rows.length + ' rows are changed!');
}
// 向服务端发送请求
function sendRequest(jsonData, httpUrl)
{
    $.ajax({
        type: "POST",
        contentType: "application/json",
        data: jsonData,
        url: httpUrl,
        success: function (data)
        {
            // 成功
            if (data.result == 1)
            {
                alert("操作成功");
            }
            if (data.result == 2)
            {
                alert(data.exceptionMsg);
            }
        },
        error: function (data)
        {
            alert("系统错误，请联系管理员");
        }
    });
}
//显示状态文本
var statusData = [
    {'value': '1', 'name': '不可用'},
    {'value': '2', 'name': '可用'}
];
function showStatusText(value)
{
    for (var i = 0; i < statusData.length; i++)
    {
        if (statusData[i].value == value)
            return statusData[i].name;
    }
    return value;
}
//显示是否是管理员文本
var isAdminData = [
    {'value': '1', 'name': '否'},
    {'value': '2', 'name': '是'}
];
function showIsAdminText(value)
{
    for (var i = 0; i < isAdminData.length; i++)
    {
        if (isAdminData[i].value == value)
            return isAdminData[i].name;
    }
    return value;
}
