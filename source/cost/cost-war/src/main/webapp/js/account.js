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
        'uploader': 'http://localhost:8090/cost/rest/account/fileUpload',
        'auto': false,
        'buttonText': '选择文件',
        'removeCompleted': false,
        'fileTypeDesc': '文件上传',
        'fileTypeExts': '*.gif; *.jpg; *.png',
        'method': 'post',
        'onUploadStart': function (file)
        {
            //在jsp页面中添加一个<img src=""></img>,当上传完成后在onComplete事件中修改img的src值
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
//文件浏览
function browse(value)
{
    var imgContent = "<img width='640' height='466' src='image/FileUpload/6131_1392108000311.jpg' />";
    TINY.box.show(imgContent, 0, 0, 0, 1);
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
