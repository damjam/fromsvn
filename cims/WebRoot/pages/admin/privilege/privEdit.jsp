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
				var parent = $('#parent').val();
				gotoUrl('/privilegeAction.do?action=listPrivs&parent='+parent);
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
				<html:form action="/privilegeAction.do?action=editPrivilege"
					styleId="inputForm" styleClass="validate">
					<html:hidden property="parent" styleId="parent"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3"
						cellpadding="0">
						<caption>
							${ACT.name}
						</caption>
						<tr>
							<td class="formlabel nes">
								Ȩ��ID
							</td>
							<td>
								<logic:empty name="privilegeActionForm" property="limitId">
									<html:text property="limitId"
									styleClass="userbox_bt {required:true}" maxlength="100" />
								</logic:empty>
								<logic:notEmpty name="privilegeActionForm" property="limitId">
									<html:text property="limitId"
									styleClass="userbox_bt {required:true}" maxlength="100" readonly="true"/>
								</logic:notEmpty>
								<span class="field_tipinfo"></span><span class="error_tipinfo">����</span>
							</td>
						</tr>

						<tr>
							<td class="formlabel nes">
								Ȩ������
							</td>
							<td>
								<html:text property="limitName" maxlength="20"></html:text>
								<span class="field_tipinfo"></span><span class="error_tipinfo">����</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								�Ƿ�˵�
							</td>
							<td>
								<html:select property="isMenu">
									<html:option value="Y">��</html:option>
									<html:option value="N">��</html:option>
								</html:select>
							</td>
						</tr>
					</table>
					<div class="btnbox" align="center">
						<input type='submit' value='�ύ' />
						<input type='button' onclick="goBack();"
							value='����' />
					</div>
				</html:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>