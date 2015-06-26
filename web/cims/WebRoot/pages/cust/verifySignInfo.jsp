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
		<title>客户签约</title>
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		 	function back(){
		 		gotoUrl('/custInfoAction.do?action=toSign&u=Y');
		 	}
		 	function sign(){
		 		window.open('${CONTEXT_PATH}/custInfoAction.do?action=doSign','','');
		 		$.blockUI({ message: $('#block'), css: { width: '275px' } }); 
		 	}
		 	function checkSign() { 
	             gotoUrl('/custInfoAction.do?action=checkSign');
	        }
		</script>
		<style type="text/css">
			body{margin-left:260px;margin-right: 260px;}
		</style>
	</head>
<body>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=doSign" method="post" styleClass="validate">
		<html:hidden property="id"/>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>确认签约信息</caption>
					  <tr>
					   		<td class="formlabel">真实姓名</td>
					   		<td>${custInfoActionForm.name}
					   		</td>
					   </tr>
					   <tr>
						    <td class="formlabel">证件类型</td>
						    <td><f:type className="IdCardType" value="${custInfoActionForm.cardType}"/>
						    </td>
					   </tr>
					    <tr>
					   		<td class="formlabel">证件号码</td>
					   		<td>${custInfoActionForm.idCard}
					   		</td>
					   </tr>
					   <tr>
						    <td class="formlabel">出生日期</td>
						    <td>
						    	${custInfoActionForm.birthday}
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">性别</td>
						    <td>
						    	<f:type className="SexType" value="${custInfoActionForm.sex}"/>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel">支付渠道</td>
						    <td>
						    	<f:type className="PayChnlType" value="${custInfoActionForm.payChnlType}"/> 
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">支付账号/卡号</td>
						    <td>
						    	${custInfoActionForm.payChnlNo}
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">开通业务类型</td>
						    <td>
						    	<f:type className="BusiType" value="${custInfoActionForm.busiType}"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">是否自动延期</td>
						    <td>
						    	<f:type className="SymbolType" value="${custInfoActionForm.extend}"/>
						    </td>
					   </tr>
					      <tr>
					   		<td class="formlabel">地区编码</td>
						     <td>
								<f:type className="AreaCodeType" value="${custInfoActionForm.areaCode}" />
							</td>
					   </tr>
					   <tr>
					    	<td class="formlabel">通讯地址</td>
						     <td>
								${custInfoActionForm.addr}
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="确定" onclick="sign()"/>
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
        <input type="button" value="签约完成" onclick="checkSign()"/>
	</div>
</body>
</html>
