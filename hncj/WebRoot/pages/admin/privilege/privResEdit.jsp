<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>

		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/plugin/jquery.validate.js" />

		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/popUp.js" />

		<style type="text/css">
html {
	overflow-y: scroll;
}
</style>
	<script type="text/javascript">
			<!--//--><![CDATA[//><!--
			function submit(){
				submitFirstTokenForm();
			}
			function goBack(){
				history.go(-1);
				/*
				var limitId = $('#limitId').val();
				gotoUrl('/privilegeAction.do?action=listPrivs&limitId='+limitId);
				*/
			}
		//--><!]]></script>
	</head>

	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />

		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="/privilegeAction.do?action=editPrivRes"
					styleId="inputForm" styleClass="validate">
					<html:hidden property="limitId" styleId="limitId"/>
					<html:hidden property="id"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3"
						cellpadding="0">
						<caption>
							${ACT.name}
						</caption>

						<tr>
							<td class="formlabel nes">
								路径
							</td>
							<td>
								<html:text property="url"
									styleClass="userbox_bt {required:true}" maxlength="100" size="100" />
								<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
							</td>
						</tr>

						<tr>
							<td class="formlabel nes">
								参数
							</td>
							<td>
								<html:text property="param"
									styleClass="userbox_bt {required:true}" maxlength="100" size="100" />
								<span class="field_tipinfo"></span><span class="error_tipinfo">必填</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel nes">
								是否入口
							</td>
							<td>
								<html:select property="isEntry">
									<html:option value="N">否</html:option>
									<html:option value="Y">是</html:option>
								</html:select>
							</td>
						</tr>
					</table>
					<div class="btnbox" align="center">
						<input type='submit' value='提交' />
						<input type='button' onclick="goBack();"
							value='返回' />
					</div>
				</html:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>