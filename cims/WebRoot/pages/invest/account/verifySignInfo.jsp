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
		<title>确认签约信息</title>
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>	
		<!-- 
		<f:js src="/js/plugin/ui.core.js"/> -->
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		 	
		 	function back(){
		 		gotoUrl('/investAcctAction.do?action=toAddSign&u=Y');
		 	}
		 	
		 	function sign(form){
		 		 //打开银行签约页面
		 		 window.open('${CONTEXT_PATH}/investAcctAction.do?action=doSign','','');
		 		 $.blockUI({ message: $('#block'), css: { width: '275px' } }); 
            }
		 	
            function checkSign() { 
	             gotoUrl('/investAcctAction.do?action=checkSign');
	        }
            
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="investAcctAction.do?action=signFinish" method="post" styleClass="validate" styleId="dataForm">
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
						    <td class="formlabel">签约业务类型1</td>
						    <td>
						    	黄金定投
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">支付渠道</td>
						    <td>
						    	${investInfoActionForm.payChnlTypeName}
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">支付账号/卡号</td>
						    <td>
						    	${investInfoActionForm.payChnlNo}
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">是否自动延期</td>
						    <td>
						    	<f:type className="SymbolType" value="${investInfoActionForm.extend}"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="确定" onclick="sign(this.form)" id="btn"/>
					 <input type="button"  value="返回修改" onclick="back()"/>
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
	
	<div id="block" style="display:none; cursor: default;padding: 20px;"> 
        <input type="button" value="签约成功" onclick="checkSign()"/>&nbsp;&nbsp; 
        <input type="button" value="签约失败" onclick="checkSign()"/> 
	</div> 
</body>
</html>
