<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<style type="text/css">
			html {
				overflow-y: scroll;
			}
		</style>

	</head>

	<body>
		
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<form id="timerDo" action="${CONTEXT_PATH}/timerDo.do?action=queryTimerDo" method="post" >
			<div class="contentb">
			
			<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
			  <tr>
			    <td colspan="4"  align="left"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;"> ��ʱ�����ѯ</span></td>
			  </tr>
			  <tr>
			    <td width="100" height="30" align="right" nowrap="nowrap">����</td>
			    <td width="270" height="30" nowrap="nowrap">
			   		 <input id="timerDoTimerClassName" type="text" class="userbox_bt"  name="timerClassName" value="${timerDo.timerClassName}"  />
			    </td>
			    <td width="100" height="30" align="right" nowrap="nowrap">������</td>
			    <td height="30" nowrap="nowrap">
			    	<input id="timerDoClassNameCh" type="text" class="userbox_bt"  name="classNameCh"  value="${timerDo.classNameCh}" />
			    </td>
			  </tr>
			  <tr></tr>
			  <tr></tr>
			  <tr>
			    <td width="100" height="30" align="right">&nbsp;</td>
			    <td height="30" colspan="3" nowrap="nowrap">
			      <input type="submit" value="��ѯ" id="input_btn2"   />
			      <input style="margin-left:30px;" onclick="FormUtils.reset('timerDo');"  type="button" value="����"  />
			    </td>
			    </tr>
			  </table>
			</div>
		</form>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		<!-- �����б��� -->
		<div class="tablebox">
		<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			  <tr>
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			    <th align="center" nowrap="nowrap" class="titlebg">������</th>
			    <th align="center" nowrap="nowrap" class="titlebg">״̬</th>
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			    <th align="center" nowrap="nowrap" class="titlebg">����ʱ��</th>
			     <th align="center" nowrap="nowrap" class="titlebg">��ע</th>
			  </tr>
			 </thead>
			 <tbody>
			  <c:if test="${empty timerDoList}">
				  <tr><td nowrap="nowrap" colspan="7" >û������</td></tr>
			  </c:if>
			  <c:if test="${not empty timerDoList}">	  	  
				  <c:forEach items="${timerDoList}" var="timerDo">
			  		<tr>
					    <td align="left" nowrap="nowrap">${timerDo.timerClassName }</td>
					    <td align="left" nowrap="nowrap">${timerDo.classNameCh }</td>
					    <td align="left" nowrap="nowrap">
					    
					    	<c:if test="${timerDo.state eq '0'}">δ����</c:if>
					    	<c:if test="${timerDo.state eq '1'}">���óɹ�</c:if>
					    	<c:if test="${timerDo.state eq '2'}">����ʧ��</c:if>
					    	<c:if test="${timerDo.state eq '3'}">����ִ�гɹ�</c:if>
					    	<c:if test="${timerDo.state eq '4'}">����ִ��ʧ��</c:if>
					    </td>
					    <td align="left" nowrap="nowrap">
					    	${timerDo.para2}
					    </td>
					    <td align="left" nowrap="nowrap">
					    	<fmt:parseDate value="${timerDo.triggerDate}${timerDo.triggerTime}" pattern="yyyyMMddHHmmss" var="triggerTime"></fmt:parseDate>
					    	<fmt:formatDate value="${triggerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					    </td>
					    <td align="left" nowrap="nowrap">${timerDo.remark }</td>
				    </tr>
				 </c:forEach>
			</c:if>
			</tbody>
		</table>
		
		<!-- ��ҳ -->
		<div class="table_navi">
			<f:paginate />	
		</div>
	
		</div>
</body>
</html>