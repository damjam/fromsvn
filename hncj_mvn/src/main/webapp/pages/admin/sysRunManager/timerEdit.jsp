<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>

<%@ include file="/pages/common/taglibs.jsp"%>

<html lang="zh-cn">
<head>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<f:js src="/js/jquery.js" />
<f:js src="/js/sys.js" />
<f:js src="/js/paginater.js" />
<f:js src="/js/plugin/jquery.validate.js" />
<f:js src="/js/plugin/jquery.metadata.js" />
<f:js src="/js/common.js" />
<f:css href="/css/page.css" />

<script type="text/javascript">
	$(function() {
		$('#btnReturn').click(function() {
			window.location.href = CONTEXT_PATH + "/timer.do?action=query";
		});
	});
	function save() {
		FormUtils.submitFirstTokenForm();
	}
	$().ready(function() {
		var height = document.body.scrollHeight;
		parent.adjustHeight(height, 0);
	});
</script>
</head>
<body>

	<f:msg />
	<form action="timer.do?action=doEdit" method="post" class="validate">
		<s:hidden name="id" />
		<!-- 用户资料修改区 -->
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					<tr>
						<td class="formlabel nes">beanName</td>
						<td><s:textfield name="beanName" id="beanName" maxlength="20"
								class="{required:true,letter:true}"></s:textfield> <span
							class="field_tipinfo">填写正确的beanName</span></td>
					</tr>
					<tr>
						<td class="formlabel nes">中文名</td>
						<td><s:textfield name="beanNameCh" id="beanNameCh"
								maxlength="20" class="{required:true}"></s:textfield> <span
							class="field_tipinfo">不能为空</span></td>
					</tr>
					<tr>
						<td class="formlabel">参数1</td>
						<td><s:textfield name="para1" id="para1" maxlength="20"></s:textfield>
							<span class="field_tipinfo"></span></td>
					</tr>
					<tr>
						<td class="formlabel">参数2</td>
						<td><s:textfield name="para2" id="para2" maxlength="20"></s:textfield>
							<span class="field_tipinfo"></span></td>
					</tr>
					<tr>
						<td class="formlabel nes">触发时间</td>
						<td><s:textfield name="triggertime" id="triggertime"
								maxlength="6" class="{required:true}"
								onclick="WdatePicker({dateFmt:'HHmmss'})"></s:textfield> <span
							class="field_tipinfo">格式 hhmmss</span></td>
					</tr>
					<tr>
						<td class="formlabel">备注</td>
						<td><s:textfield name="remark" id="remark"></s:textfield></td>
					</tr>
				</table>
				<div class="btnbox">
					<input type="button" id="btnSumit" value="保存" onclick="save()" />
					<input type="button" id="btnReturn" value="取消"
						onclick="gotoUrl('/timer.do?action=query')" />
				</div>
			</div>
		</div>
	</form>
</body>
</html>
