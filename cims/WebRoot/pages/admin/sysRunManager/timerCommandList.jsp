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


		<style type="text/css">
			html {
				overflow-y: scroll;
			}
		</style>

	<script type="text/javascript">
		  $(function(){
		  		//��ʱ�������
				$('#btnTimerCommandClear').click(function(){
					FormUtils.reset();
				});
				
				//��ʱ��������
				$('#btnTimerCommandAdd').click(function(){
					window.location.href=CONTEXT_PATH+"/pages/admin/sysRunManager/timerCommandAdd.jsp";
				});
				
		  });
		   
	    </script>
	</head>

<body >
	<form id="timerCommand" action="${CONTEXT_PATH}/timerCommand.do?action=queryTimerCommand" method="post" >
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
		  <tr>
		    <td colspan="4"  align="left"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;"> ��ʱ�����ѯ</span></td>
		  </tr>
		  <tr>
		    <td width="100" height="30" align="right">����</td>
		    <td width="270" height="30">
		   		 <input type="text" class="userbox_bt"  name="timerClassName" value="${timerCommand.timerClassName}"  />
		    </td>
		    <td width="100" height="30" align="right">������</td>
		    <td height="30">
		    	<input type="text" class="userbox_bt"  name="classNameCh"  value="${timerCommand.classNameCh}" />
		    </td>
		  </tr>
		  <tr></tr>
		  <tr></tr>
		  <tr>
		    <td width="100" height="30" align="right">&nbsp;</td>
		    <td height="30" colspan="3">
		      <input type="submit" value="��ѯ" id="input_btn2"   />
		      <input  id="btnTimerCommandClear" style="margin-left:30px;"  type="button" value="����"  />
		   	  <input  id="btnTimerCommandAdd" style="margin-left:30px;"  type="button" value="����"   />
		    </td>
		    </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
	</form>
		<!-- �����б��� -->
	<div class="tablebox">
		<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			  <tr>
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			    <th align="center" nowrap="nowrap" class="titlebg">������</th>
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			  </tr>
			 </thead>
			 <tbody>
			  <c:if test="${empty timerCommandList}">
				  <tr><td nowrap="nowrap" colspan="7" >û������</td></tr>
			  </c:if>
			  <c:if test="${not empty timerCommandList}">	  	  
				  <c:forEach items="${timerCommandList}" var="timerCommand">
			  		<tr>
					    <td align="left" nowrap="nowrap">${timerCommand.timerClassName }</td>
					    <td align="left" nowrap="nowrap">${timerCommand.classNameCh }</td>
					    <td align="left" nowrap="nowrap">
					   	 <span class="redlink">
					   		 <a href="timerCommand.do?action=updateQuery&timerClassName=${timerCommand.timerClassName}">�޸�</a>
					   		 <a onclick="return confirm('��ȷ��Ҫɾ����?');" href="timerCommand.do?action=delete&timerClassName=${timerCommand.timerClassName }" >ɾ��</a>
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
	
</body>
</html>