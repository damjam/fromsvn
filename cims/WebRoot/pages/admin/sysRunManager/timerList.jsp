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
		<f:js src="/js/paginater.js" />
		<f:js src="/js/common.js" />
		<f:css href="/css/page.css" />
		<f:css href="/css/jquery-ui-1.7.2.custom.css"/>

		<style type="text/css">
			html {
				overflow-y: scroll;
			}
		</style>

	<script type="text/javascript">
		   $(function(){
		   		//��ʱ�ƻ�����
				$('#btnTimerAdd').click(function(){
					window.location.href=CONTEXT_PATH+"/pages/admin/sysRunManager/timerAdd.jsp";
				});
				
				//��ʱ�ƻ����
				$('#btnTimerClear').click(function(){
					FormUtils.reset();
				});
		   });
		   
	    </script>
	</head>

<body>
	<form id="timer" action="${CONTEXT_PATH}/timer.do?action=query" method="post" >
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
		  <tr>
		    <td colspan="4"  align="left"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;"> ��ʱ�ƻ���ѯ</span></td>
		  </tr>
		  <tr>
		    <td width="100" height="30" align="right" nowrap="nowrap">����</td>
		    <td width="270" height="30" nowrap="nowrap">
		   		 <input  id='timerClassName' type="text" class="userbox_bt"  name="timerClassName" value="${timer.timerClassName}"  />
		    </td>
		    <td width="100" height="30" align="right" nowrap="nowrap">������</td>
		    <td height="30" nowrap="nowrap">
		    	<input id="classNameCh" type="text" class="userbox_bt"  name="classNameCh"  value="${timer.classNameCh}" />
		    </td>
		  </tr>
		  <tr></tr>
		  <tr></tr>
		  <tr>
		    <td width="100" height="30" align="right">&nbsp;</td>
		    <td height="30" colspan="3" nowrap="nowrap">
		      <input type="submit" value="��ѯ" id="input_btn2"   />
		      <input  id="btnTimerClear" style="margin-left:30px;"  type="button" value="����"  />
		   	  <input  id="btnTimerAdd" style="margin-left:30px;"  type="button" value="����"   />
		    </td>
	    </tr>
		  </table>
		</div>
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
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			    <th align="center" nowrap="nowrap" class="titlebg">����ʱ��</th>
			     <th align="center" nowrap="nowrap" class="titlebg">��ע</th>
			     <th align="center" nowrap="nowrap" class="titlebg">����</th>
			  </tr>
			 </thead>
			 <tbody>
			  <c:if test="${empty timerList}">
				  <tr><td nowrap="nowrap" colspan="7" >û������</td></tr>
			  </c:if>
			  <c:if test="${not empty timerList}">	  	  
				  <c:forEach items="${timerList}" var="timer">
			  		<tr>
					    <td align="left" nowrap="nowrap">${timer.timerClassName }</td>
					    <td align="left" nowrap="nowrap">${timer.classNameCh }</td>
					    <td align="left" nowrap="nowrap">
					    	${timer.para2}
					    </td>
					    <td align="left" nowrap="nowrap">
					    	<fmt:parseDate value="${timer.triggertime}" pattern="HHmmss" var="triggerTime"></fmt:parseDate>
					    	<fmt:formatDate value="${triggerTime}" pattern="HH:mm:ss"/>
					    						    
					    </td>
					    <td align="left" nowrap="nowrap">${timer.remark }</td>
					    <td align="left" nowrap="nowrap">
					   	 <span class="redlink">
					   		 <a href="timer.do?action=update&id=${timer.id}">�޸�</a>
					   		 <a onclick="return confirm('��ȷ��Ҫɾ����?');" href="timer.do?action=delete&id=${timer.id }" >ɾ��</a>
					    </span>
					    </td>
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
	</form>
</body>
</html>