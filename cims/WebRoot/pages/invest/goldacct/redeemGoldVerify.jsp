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
		<title>实物赎回确认</title>
		
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
				//gotoUrl('/investGoldAction.do?action=toRedeemGold&updateSymbol');
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="investGoldAction.do?action=doRedeemGold" styleId="inputForm" styleClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">积存重量(克)</td>
							<td>
								${investInfoActionForm.balance}
								<bean:write name="investInfoActionForm" property="balance" format="#0.0000"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">赎回重量(克)</td>
							<td>
								<bean:write name="investInfoActionForm" property="amount" format="#"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">赎回类别</td>
							<td>
								${investInfoActionForm.goldTypeName}
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货地址</td>
							<td>
								${investInfoActionForm.cityName}
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货网点</td>
							<td>
								${investInfoActionForm.takeBankName}
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货日期</td>
							<td>
								${investInfoActionForm.takeDate}
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