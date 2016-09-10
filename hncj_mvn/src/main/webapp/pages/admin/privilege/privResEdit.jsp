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
<title></title>

<f:css href="/css/page.css" />
<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<f:js src="/js/plugin/jquery.metadata.js" />
<f:js src="/js/plugin/jquery.validate.js" />

<f:js src="/js/sys.js" />
<f:js src="/js/common.js" />
<f:js src="/js/popUp.js" />
<style type="text/css">
</style>
<script type="text/javascript">
<!--//--><![CDATA[//><!--
	function submit() {
		submitFirstTokenForm();
	}
	function goBack() {
		var limitId = $('#limitId').val();
		gotoUrl('/privilegeAction.do?action=listPrivs&limitId=' + limitId);
	}
	//--><!]]>
</script>
</head>

<body>

	<f:msg />

	<div class="userbox">
		<form action="privilegeResourceAction.do?action=doEdit" id="inputForm"
			class="validate" method="post">
			<s:hidden name="limitId" id="limitId" />
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					<tr>
						<td class="formlabel nes">路径</td>
						<td><s:textfield name="url"
								class="userbox_bt {required:true}" maxlength="100" size="100" />
							<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
						</td>
					</tr>
					<tr>
						<td class="formlabel nes">参数</td>
						<td><s:textfield name="param"
								class="userbox_bt {required:true}" maxlength="100" size="100" />
							<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
						</td>
					</tr>
					<tr>
						<td class="formlabel nes">是否入口</td>
						<td><s:select list="#{'Y':'是','N':'否'}" name="isEntry"
								listKey="key" listValue="value"></s:select></td>
					</tr>
				</table>
				<div class="btnbox" align="center">
					<input type='submit' value='提交' /> <input type='button'
						onclick="goBack();" value='返回' />
				</div>
			</div>
		</form>
	</div>
</body>
</html>