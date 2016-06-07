<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		
		<script type="text/javascript">
			$(document).ready(function(){
				//window.parent.unblock();
			});
			function submitUpload() {
				
		 		var oraFile = $('#file').val();
		 		if(oraFile.length == 0){
					alert("请先选择要导入的文件。");
					return;
				}
		 		oraFile = oraFile.toLowerCase();
				if(!(endWith(oraFile, '.xlsx')) && !(endWith(oraFile, '.xls'))){
					alert('文件格式扩展名必须是xlsx或xls。');
					return;
				}
				/*
				if (!confirm('确定提交文件?')) {
					return;
				}*/
				FormUtils.submitFirstTokenForm();
				showWaiter();
			}
			
			function showWaiter(){
				$('div.msg:first').hide();
				$("#loadingBarDiv").css("display","block");
				$('input').attr('disabled', true);
			}
			function endWith(s1,s2) {  
				if(s1.length<s2.length) {
				 return false;  
				} 
				if(s1==s2){
				 return  true; 
				}  
				if(s1.substring(s1.length-s2.length)==s2){
				 return  true;  
				}  
				return false;  
			}
		</script>
	</head>
    
	<body>
		
		<f:msg />
		<div class="userbox">
			<div class="widget">
				<div class="widget-head">
                    <div class="pull-left">${ACT.name}</div>
                </div>
				<form action="${uri}?action=doImport" id="dataForm" enctype="multipart/form-data" class="validate" method="post">
					<table class="form_grid">
						<tr>
							<td class="formlabel nes">请选择导入的文件</td>
							<td>
								<s:file id="file" name="file" class="userbox_bt {required:true}"></s:file>
								<span class="field_tipinfo">请选择文件</span>
								<span class="redlink"><a href="${CONTEXT_PATH}/template/${templateName}">下载模板</a> </span>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="button" value="提交" id="input_btn2" name="ok" onclick="submitUpload()" />
								<input type="button" value="返回" onclick="gotoUrl('${uri}?action=list');"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="msg" style="position:absolute;top:100px;left:300px;display: none;"  id="loadingBarDiv">
			<div style="float: left; width: 30%; text-align: right;">
				<img src="${CONTEXT_PATH}/images/ajax_loader.gif" align="middle" />
			</div>
			<div style="width: 65%;height:30px;text-align: left;line-height:30px; vertical-align: middle;padding-left: 10px;" id="processMsg">
				正在处理，请稍候...
			</div>
		</div>
	</body>
</html>