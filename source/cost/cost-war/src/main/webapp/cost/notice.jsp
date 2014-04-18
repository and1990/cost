<%@page language="java" contentType="text/html; charset=utf8"
        pageEncoding="utf8" %>
<div class="easyui-panel" title="公告"
     data-options="closable:false,tools:'#main_notice_button',fit:true">
    <span id="notice_text">
       1.hello
       2.world
    </span>
</div>
<div id="main_notice_button">
    <a href="javascript:void(0)" class="icon-add" onclick="addMainNotice()"></a>
    <a href="javascript:void(0)" class="icon-edit" onclick="editMainNotice()"></a>
    <a href="javascript:void(0)" class="icon-reload" onclick="loadNotice()"></a>
</div>

<!-- 公告对话框 -->
<div id="notice_dailog" class="easyui-dialog" title="公告信息" style="width: 550px; height: 300px"
     data-options="iconCls: 'icon-save',resizable:true,closed:true,buttons: '#notice_dialog_button'">
    <textarea id="notice_dialog_text" class="easyui-validatebox" data-options="fit:true"
              style="width: 530px;height:223px">
    </textarea>
</div>

<!-- 按钮 -->
<div id="notice_dialog_button">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveNotice();">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       onclick="javascript:$('#notice_dailog').dialog('close')">关闭</a>
</div>