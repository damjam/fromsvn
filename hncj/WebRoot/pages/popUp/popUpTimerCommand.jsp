<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<jsp:directive.page import="flink.util.Paginater;" />

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

		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<base target="_self"/>
		<script type="text/javascript">
			
			$(function(){
				$('#selTimerCommand').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("��ѡ����Ҫ�Ķ�ʱ������ҽ���һ��");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
					
				});
				
				$('#clearTimerCommand').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
				
				//��ʱ�������
				$('#btnTimerCommandClear').click(function(){
					$('#timerCommand :text').val('');
				});
			});
	 	</script>
	 	<title>��ʱ����ѡ��</title>
	 	
	</head>

	<body>

		<!-- ��ѯ������ -->
	<form id="timerCommand" action="${CONTEXT_PATH}/timerCommand.do?action=queryPopUpTimerCommand" method="post" >
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
		      <input  id="btnTimerCommandClear" style="margin-left:30px;"  type="button" value="���"  />
		    </td>
		    </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		<!-- �����б��� -->
	</form>
	<div class="tablebox">
		<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
			  <tr>
			    <th align="center" nowrap="nowrap" class="titlebg">��ѡ��</th>
			    <th align="center" nowrap="nowrap" class="titlebg">����</th>
			    <th align="center" nowrap="nowrap" class="titlebg">������</th>
			  </tr>
			 </thead>
			 <tbody>
			  <c:if test="${empty timerCommandList}">
				  <tr><td nowrap="nowrap" colspan="2" >û������</td></tr>
			  </c:if>
			  <c:if test="${not empty timerCommandList}">	  	  
				  <c:forEach items="${timerCommandList}" var="timerCommand">
			  		<tr class="shortcut">
			  			<td align="center" nowrap="nowrap">
						  <input name="timerCommand" type="radio" value="${timerCommand.timerClassName}$${timerCommand.classNameCh}"/>
						</td>
					    <td align="center" nowrap="nowrap">${timerCommand.timerClassName }</td>
					    <td align="center" nowrap="nowrap">${timerCommand.classNameCh }</td>
				    </tr>
				 </c:forEach>
			</c:if>
			</tbody>
		</table>
		
			<!-- ��ҳ -->
			<div class="table_navi">
				<f:paginate />	
			</div>
			<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="30" colspan="3" align="center">
						<input type="submit" value="ȷ��" id="selTimerCommand"/>
						<input style="margin-left: 30px;" type="button" value="���" id="clearTimerCommand"/>
						<input style="margin-left: 30px;" type="button" value="�ر�" id="winClose"/>
					</td>
				</tr>
			</table>
			<br />
			<br />
			<br />
	</div>
	</body>
</html>