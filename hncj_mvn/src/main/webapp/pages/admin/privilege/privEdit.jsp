<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

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

	<script type="text/javascript">
			<!--//--><![CDATA[//><!--
			function submit(){
				submitFirstTokenForm();
			}
			function goBack(){
				//history.go(-1);
				var parent = $('#parent').val();
				gotoUrl('${uri}?action=listPrivs&parent='+parent);
			}
		//--><!]]></script>
	</head>

	<body>
		
		<f:msg />

		<div class="userbox">
			<div class="widget">
				<form action="privilegeAction.do?action=editPrivilege"
					id="inputForm" class="validate" method="post">
					<s:hidden name="parent" id="parent"/>
					<table class="form_grid">
						<caption class="widget-head">${ACT.name}</caption>
						<tr>
							<td class="formlabel nes">
								权限ID
							</td>
							<td>
								<s:textfield name="limitId" class="userbox_bt {required:true}"></s:textfield>
								
								<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
							</td>
						</tr>

						<tr>
							<td class="formlabel nes">
								权限名称
							</td>
							<td>
								<s:textfield id="limitName" maxlength="20" name="limitName"/>
								<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								是否菜单
							</td>
							<td>
								<s:select list="#{'Y':'是','N':'否' }" name="isMenu" listKey="key" listValue="value"></s:select>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type='submit' value='提交' />
						<input type='button' onclick="goBack();"
							value='返回' />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>