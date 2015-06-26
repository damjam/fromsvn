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
		<title>新增账户</title>
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		 	function addAcct(form){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	function back(){
		 		gotoUrl('/investAcctAction.do?action=list');
		 	}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="investAcctAction.do?action=toVerify" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>签约信息</caption>
					  <tr>
						    <td class="formlabel nes">支付渠道</td>
						    <td>
						    	<html:select property="payChnlType" styleClass="{required:true}" >
									<html:options collection="payChnlTypes" labelProperty="value" property="key"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">支付账号/卡号</td>
						    <td>
						    	<html:text property="payChnlNo"  styleClass="{required:true,stringNum:true}" maxlength="32"/>
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">是否自动延期</td>
						    <td>
						    	<html:select property="extend" styleClass="{required:true}" >
									<html:options collection="symbolTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="确定" onclick="addAcct(this.form)"/>
					 <input type="button"  value="取消" onclick="back()"/>
				</div>
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
