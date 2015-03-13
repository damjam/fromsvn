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
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="/accountWithdraw.do?action=input" styleId="inputForm" styleClass="validate">
					<html:hidden property="accountNo"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						
						<tr>
							<td width="100" height="30" align="right">账户编号</td>
							<td><html:text property="accountNo" readonly="true"/></td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">账户余额</td>
							<td>
								<bean:write name="account" property="balance" format="#,##0.00"/>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">可提金额</td>
							<td>
								<bean:write name="withdrawBalance" format="#,##0.00"/>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">入账账号</td>
							<td>
								<html:select property="bankAccountNo">
									<logic:iterate id="element" name="bankAccounts">
										<html:option value="${element}">${element}</html:option>
									</logic:iterate>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">金额</td>
							<td>
								<html:text property="amount" maxlength="16" styleClass="{required:true,num:true,max:99999999999.99,min:0.01}" onkeyup="formatCurrency(this,'chineseMoney', 'accountMoney')" onblur="formatCurrency(this,'chineseMoney', 'accountMoney');"/>
								元  大写:<span class="field_tipinfo"></span>
								<span class="error_tipinfo">请输入正确格式的金额</span>
								<span id="chineseMoney"></span>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td colspan="3" class="btnbox">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
							</td>
						</tr>
					</table>
				</html:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>