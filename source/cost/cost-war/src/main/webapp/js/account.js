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
            browse(index);
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
                text: '确定',
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
            $("#file_upload").uploadify("settings", "formData", { 'accountId': '12' });
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
function browse(index)
{
}
//开始文件上传
function startFileUpLoad()
{
    $('#file_upload').uploadify('upload', '*');
}
//取消文件上传
function cancelFileUpLoad()
{
    $('#file_upload').uploadify('cancelUpload', '*');
}
