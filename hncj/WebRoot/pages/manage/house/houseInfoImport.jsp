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
				//alert(getFileExt("/hncj/template/房屋信息导入模板.xlsx"));
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
			function changeFileFormat(){
				var suffix = $('#suffix').val();
				var href = $('#linkHref').attr('href');
				if(suffix == 'xls'){
					$('#linkHref').attr('href', href.replace('xlsx','xls'));
				} else if(suffix == 'xlsx'){
					$('#linkHref').attr('href', href.replace('xls','xlsx'));
				}
			}
			function getFileExt(str) { 
				//var d=/\.[^\.]+$/.exec(str); 
				var d=/[^\.]+$/.exec(str); 
				return d; 
			}
		</script>
	</head>
	<body>
		
		<f:msg />
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<form action="${uri}?action=doImport" id="dataForm" enctype="multipart/form-data" class="validate" method="post">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel nes">文件格式</td>
							<td>
							<s:select list="#{'xlsx':'xlsx','xls':'xls'}" name="suffix" id="suffix" onchange="changeFileFormat()" listKey="key" listValue="value"></s:select>
							</td>
						</tr>
						<tr>
							<td class="formlabel nes">请选择导入的文件</td>
							<td>
								<s:file id="file" name="file" class="userbox_bt {required:true}"></s:file>
								<span class="field_tipinfo">请选择文件</span>
								<span><a href="${CONTEXT_PATH}/template/房屋信息导入模板.xlsx" id="linkHref">下载模板</a></span>
							</td>
						</tr>
					</table>
				</form>
				<div class="btnbox">
					<input type="button" value="提交" id="input_btn2" class="inp_L3" name="ok" onclick="submitUpload()" />
					<input type="button" value="返回" class="inp_L3" style="margin-left:30px;" onclick="gotoUrl('${uri}?action=list');"/>
				 </div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<div class="msg" style="position:absolute;top:100px;left:300px;display: none;"  id="loadingBarDiv">
			<div style="float: left; width: 40%; text-align: right;">
				<img src="${CONTEXT_PATH}/images/ajax_loader.gif" align="middle" />
			</div>
			<div style="width: 55%;height:30px;text-align: left;line-height:30px; vertical-align: middle;padding-left: 10px;" id="processMsg">
				正在处理，请稍候...
			</div>
		</div>
	</body>
</html>