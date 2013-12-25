<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<div class="easyui-panel" title="公告"  
        data-options="closable:false,tools:'#main_notice_button',fit:true">
    <span id="notice_text">
        easyui is a collection of user-interface plugin based on jQuery.
        easyui provides essential functionality for building modem, interactive, javascript applications.
        using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.
    </span>
</div>
<div id="main_notice_button">
    <a href="javascript:void(0)" class="icon-add" onclick="addMainNoticeDialog()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="editMainNoticeDialog()"></a>
    <a href="javascript:void(0)" class="icon-reload" onclick="loadMainNotice()"></a>
</div>

<!-- 公告对话框 -->
<div id="main_notice_dailog" class="easyui-dialog" title="公告信息"
	style="width: 550px; height: 300px"
	data-options="iconCls: 'icon-save',resizable:true,closed:true,
                buttons: '#main_notice_dialog_button'">
	<textarea id="notice_dialog_text" class="easyui-validatebox" data-options="fit:true" style="width: 530px;height:223px"></textarea>
</div>
<div id="main_notice_dialog_button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:saveNoticeDialogText()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#main_notice_dailog').dialog('close')">关闭</a>
</div>