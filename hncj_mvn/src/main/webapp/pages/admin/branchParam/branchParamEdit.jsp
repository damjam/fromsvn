<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		$('#ifmodify').val($('#hidIfmodify').val());

		$('#btnReturn').click(
				function() {
					window.location.href = CONTEXT_PATH
							+ "/branchParamManage.do?action=query";
				});

	});
</script>
</head>
<body>

	<form id="query" action="${uri}?action=saveUpdate" method="post"
		class="validate">
		<!-- 用户资料修改区 -->
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					<tr>
						<td class="formlabel nes">参数编码</td>
						<td><input class="{required:true,maxlength:6} readonly"
							disabled="disabled" name="code" type="text" value="${code}" />
							<span class="field_tipinfo">1-6位字符，包括字母和数字</span> <input
							type="hidden" name="code" value="${code}"></td>
					</tr>
					<tr>
						<td class="formlabel nes">参数名称</td>
						<td><input class="{required:true}" name="parname" type="text"
							value="${parname}" maxlength="100" /> <span class="field_tipinfo">不能为空</span>
						</td>
					</tr>
					<tr>
						<td class="formlabel nes">参数值</td>
						<td><input class="{required:true}" name="parvalue"
							type="text" value="${parvalue}" maxlength="100" /> <span
							class="field_tipinfo">不能为空</span></td>
					</tr>
					<c:if test="${sessionScope.isHQ == true}">
						<tr>
							<td class="formlabel nes">机构</td>
							<td><s:select list="#request.branches" name="branchNo"
									listKey="key" listValue="value" /> <span class="field_tipinfo">不能为空</span>
							</td>
						</tr>
					</c:if>
					<tr>
						<td class="formlabel">备注</td>
						<td><input name="remark" type="text" value="${remark}"
							maxlength="50" /></td>
					</tr>
					<tr>
						<td></td>
						<td colspan="3"><input type="submit" id="input_btn2"
							value="确定" /> <input style="margin-left: 30px;" type="button"
							value="返回" id="btnReturn" /></td>
					</tr>
				</table>
			</div>
		</div>
	</form>

</body>
</html>
