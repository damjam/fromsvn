<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>基本资料</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function() {
	
				$('#btnUpdate').click(function() {
					var url = "/custInfoAction.do?action=toUpdateInfo";
					gotoUrl(url);
				});
	
			});
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=toUpdateInfo" styleId="custInfoForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel">
								登录名
							</td>
							<td>
								<html:text property="loginId" readonly="true" styleId="loginId"  />
							</td>
					   </tr>
					   <tr>
						   <td class="formlabel">
								真实姓名
							</td>
							<td>
								<html:text property="name" readonly="true" styleId="name" />
							</td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes" align="right">
								性别
							</td>
							<td>
<%--								<html:select property="sex" styleClass="{required:false}" >--%>
<%--									<html:option value="">---请选择---</html:option>--%>
<%--									<html:options collection="sexVal" labelProperty="name" property="value" />									--%>
<%--								</html:select>--%>
								<span class="field_tipinfo">性别</span>
							</td>
					   </tr>
					   <tr>
					   	 <td></td>
							<td colspan="5">
								<input type="button" value="编辑" id="btnUpdate"/>&nbsp;
								<input type="button" value="返回" onclick="history.go(-1)"/>&nbsp;
							</td>
					   </tr>
				  </table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</html:form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
