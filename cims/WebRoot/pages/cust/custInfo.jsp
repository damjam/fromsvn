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
		<title>查看客户资料</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		 	function toSign(){
		 		var url = '/custInfoAction.do?action=toAddSignInfo';
		 		gotoUrl(url);
		 	}
		 	$().ready(function(){
		 		var subsPhone = '${cust.subsPhone}';
		 		var subsEmail = '${cust.subsEmail}';
		 		if(subsPhone == 'Y'){
		 			$('#subsPhone').attr('checked', true);
		 		}
		 		if(subsEmail == 'Y'){
		 			$('#subsEmail').attr('checked', true);
		 		}
		 	});
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=toAddSignInfo" styleId="custInfoForm" method="post" styleClass="validate">
		<input type="hidden" id="oriMobile" value="${custInfoActionForm.mobile}"/>
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
						    <td class="formlabel">手机号</td>
						    <td>
						    	${cust.mobile}
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">邮箱</td>
						    <td>
						    	${cust.email}
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">订阅资讯</td>
						     <td>
						        <html:multibox property="subsPhone" value="Y" onclick="javascript:return false;" styleId="subsPhone"></html:multibox>手机订阅
						        <html:multibox property="subsEmail" value="Y" onclick="javascript:return false;" styleId="subsEmail"></html:multibox>邮箱订阅
							</td>
					   </tr>
							<c:if test="${cust.custType eq '1'}">
								<tr>
									<td class="formlabel">
										真实姓名
									</td>
									<td>
										${cust.name}
									</td>
								</tr>
								<tr>
									<td class="formlabel">
										证件类型
									</td>
									<td>
										<f:type className="IdCardType" value="${cust.cardType}"/>
									</td>
								</tr>
								<tr>
									<td class="formlabel">
										证件号码
									</td>
									<td>
										${cust.idCard}
									</td>
								</tr>
								<tr>
									<td class="formlabel">
										出生日期
									</td>
									<td>
										${cust.birthday}
									</td>
								</tr>
								<tr>
									<td class="formlabel">
										性别
									</td>
									<td>
										<f:type className="SexType" value="${cust.sex}"/>
									</td>
								</tr>
								<tr>
									<td class="formlabel">
										通讯地址
									</td>
									<td>
										${cust.addr}
									</td>
								</tr>
							</c:if>
						</table>
				  <div class="btnbox">
				  	 <c:if test="${cust.custType eq '0'}">	
					 	<input type="button"  value="成为会员" onclick="toSign()"/>
					 </c:if>
					 <input type="button" value="返回" onclick="toHomePage()" />
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
