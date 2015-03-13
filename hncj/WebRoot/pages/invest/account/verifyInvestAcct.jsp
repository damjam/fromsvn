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
		<title>账号绑定确认</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function back(){
				backToList('${backUrl}', '${params}', 'Y');
			}
			function sign(form){
		 		 //打开银行签约页面
		 		 window.open('${CONTEXT_PATH}/investAcctAction.do?action=doModifyInvestAcct','','');
		 		 $.blockUI({ message: $('#block'), css: { width: '275px' } }); 
            }
			
			function checkSign() { 
	             gotoUrl('/investAcctAction.do?action=checkSign');
	        }
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="investAcctAction.do?action=doModifyInvestAcct" styleId="inputForm" styleClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right">定投账户</td>
							<td>
								${investInfoActionForm.acctNo}
								<html:hidden property="acctNo"/>
							</td>
						</tr>
						
						<tr>
							<td width="100" height="30" align="right">卡号</td>
							<td>
								${investInfoActionForm.payChnlNo}
								<html:hidden property="payChnlNo"/>
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
		<div id="block" style="display:none; cursor: default;padding: 20px;"> 
	        <input type="button" value="签约成功" onclick="checkSign()"/>&nbsp;&nbsp; 
	        <input type="button" value="签约失败" onclick="checkSign()"/> 
		</div> 
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>