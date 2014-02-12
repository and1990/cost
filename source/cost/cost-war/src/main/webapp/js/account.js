//附件处理
function accessoryDo(index, field, value)
{
    if ("accountAccessory" === field)
    {
        if (value == undefined)
        {
            openFileUploadDialog();
            fileUpload(index);
        } else
        {
            browse(value);
        }
    }
}
//打开对话框
function openFileUploadDialog()
{
    $("#fileUploadDialog").dialog({
        title: "文件上传",
        width: 600,
        height: 400,
        resizable: false,
        cache: false,
        modal: true,
        shadow: false,
        buttons: [
            {
                text: '上传',
                handler: startFileUpLoad
            },
            {
                text: '取消',
                handler: cancelFileUpLoad
            }
        ]
    });
}
//文件上传
function fileUpload(index)
{
    var allRowData = $('#account_data_table').datagrid('getRows');
    var accountId = allRowData[index].accountId;
    $('#file_upload').uploadify({
        'swf': 'js/uploadify/uploadify.swf',
        'cancelImg': 'js/uploadify/uploadify-cancel.png',
        'uploader': 'http://localhost:8080/cost/rest/account/fileUpload',
        'auto': false,
        'buttonText': '选择文件',
        'removeCompleted': false,
        'fileTypeDesc': '文件上传',
        'fileTypeExts': '*.gif; *.jpg; *.png',
        'method': 'post',
        'onUploadStart': function (file)
        {
            $("#file_upload").uploadify("settings", "formData", { 'accountId': accountId });
        },
        'onComplete': function ()
        {
        },
        'onUploadSuccess': function (file, data, response)
        {
        }
    });
}
//开始文件上传
function startFileUpLoad()
{
    $('#file_upload').uploadify('upload', '*');
}
//取消文件上传
function cancelFileUpLoad()
{
    $('#file_upload').uploadify('cancel', '*');
}
//文件浏览
function browse(value)
{
    var valueArr = value.split(",");
    var innerHTML = undefined;
    for (var index = 0; index < valueArr.length; index++)
    {
        if (innerHTML === undefined)
            innerHTML = "<a href='javascript:void(0)'><img src='" + valueArr[index] + "'><span>" + (index + 1) + "</span></a>";
        else
            innerHTML += "<a href='javascript:void(0)'><img src='" + valueArr[index] + "'><span>" + (index + 1) + "</span></a>";
    }
    $('#coin-slider').append(innerHTML);
    $('#coin-slider').coinslider({navigation: true, hoverPause: true });
    $('#w').window('open');
}
