//var basePath = 'http://192.168.1.101:8080/cost';
var basePath = 'http://cost.coolfire.com';

// 行索引
var rowIndex = undefined;
// 操作类型，0：增加，1：修改，2：删除
var actionType = undefined;
// 请求地址
var httpUrl = undefined;
// 发送数据
var sendData = undefined;

// 增加记录
function addData(dataTableId)
{
	if (actionType != undefined)
	{
		return;
	}
	$(dataTableId).datagrid('appendRow', {});
	rowIndex = $(dataTableId).datagrid('getRows').length - 1;
	$(dataTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit',
			rowIndex);
	actionType = 0;
	sendData = $(dataTableId).datagrid('getSelected');
	if (dataTableId === '#user_data_table')
	{
		httpUrl = basePath + '/rest/user/addUser';
	} else if (dataTableId === '#account_data_table')
	{
		httpUrl = basePath + '/rest/account/addAccount';
	}
}

// 编辑记录
function editData(dataTableId)
{
	if (actionType != undefined)
	{
		return;
	}
	$(dataTableId).datagrid('selectRow', rowIndex).datagrid('beginEdit',
			rowIndex);
	actionType = 1;
	sendData = $(dataTableId).datagrid('getSelected');
	if (dataTableId === '#user_data_table')
	{
		httpUrl = basePath + '/rest/user/editUser';
	} else if (dataTableId === '#account_data_table')
	{
		httpUrl = basePath + '/rest/account/editAccount';
	}
}

// 删除记录
function removeData(dataTableId)
{
	if (actionType != undefined)
	{
		return;
	}
	$(dataTableId).datagrid('cancelEdit', rowIndex).datagrid('deleteRow',
			rowIndex);
	actionType = 2;
	sendData = $(dataTableId).datagrid('getSelected');
	if (dataTableId === '#user_data_table')
	{
		httpUrl = basePath + '/rest/user/deleteUser';
	} else if (dataTableId === '#account_data_table')
	{
		httpUrl = basePath + '/rest/account/deleteAccount';
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
	sendRequest(sendData, httpUrl);
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
function sendRequest(sendData, httpUrl)
{
	var jsonData = JSON.stringify(sendData);
	//alert("url:"+httpUrl);
	$.ajax({
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		url : httpUrl,
		success : function(data)
		{
			alert("添加成功");
		},
		error : function(data)
		{
			alert("添加失败");
		}
	});
}

