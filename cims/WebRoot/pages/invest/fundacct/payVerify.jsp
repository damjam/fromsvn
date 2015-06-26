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
		<title>补缴欠款确认</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function back(){
				backToList('${backUrl}', '${params}', 'Y');
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="investFundAction.do?action=doPay" styleId="inputForm" styleClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right">定投账户</td>
							<td>
								${investInfoActionForm.acctNo}
								<html:hidden property="acctNo" value="${investInfoActionForm.acctNo}"/>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">可用余额</td>
							<td>
								${investInfoActionForm.balance}
								<html:hidden property="balance" value="${investInfoActionForm.balance}"/>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">欠费金额</td>
							<td>
								${investInfoActionForm.debt}
								<html:hidden property="debt" value="${investInfoActionForm.debt}"/>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td colspan="3" class="btnbox">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="返回 " onclick="back()"/>
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