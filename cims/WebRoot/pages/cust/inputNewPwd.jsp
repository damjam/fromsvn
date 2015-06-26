<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html>
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<style type="text/css">
			body{margin-left:260px;margin-right: 260px;}
		</style>
		<script type="text/javascript">
			function save(){
				var loginPwd = $('#loginPwd').val();
				$('#loginPwd').val(hex_md5(loginPwd));
				var confirmPwd = $('#confirmPwd').val();
				$('#confirmPwd').val(hex_md5(confirmPwd));
				FormUtils.submitFirstTokenForm();
			}
		</script>
<%--		<title><bean:message key="user.pageTitle.changePassword"/></title>--%>
	</head>
	<body>
		<f:msg styleClass="msg"/>
		<html:form action="/custInfoAction.do?action=doResetPwd" styleId="userInfoForm" styleClass="validate">
		<div class="userbox" style="padding-top: 100px;">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			<table class="form_grid">
				<caption>密码重置</caption>
				<tr>
					<td class="formlabel nes">新密码</td>
					<td><html:password property="loginPwd" styleId="loginPwd" styleClass="userbox_bt {required:true,minlength:6}"/>
					<span class="field_tipinfo">请输入新密码</span>
					</td>
				</tr>
				<tr>
					<td class="formlabel nes">再次输入</td>
					<td><html:password styleClass="{required:true,minlength:6,equalTo:'#loginPwd'}" property="confirmPwd" styleId="confirmPwd"/>
					<span class="field_tipinfo">确认新密码</span>
					</td>
				</tr>
				<tr align="center">
				    <td></td>
					<td align="left">
					<input type="button" onclick="save()" value="提交"/>&emsp;
					<html:reset onclick="toLoginPage()">取消</html:reset>&emsp;
					</td>
				</tr>
			</table>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</html:form>
	</body>
</html>
