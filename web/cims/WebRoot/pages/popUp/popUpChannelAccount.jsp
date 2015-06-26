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
				$('#selChannelAccountId').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("��ѡ����Ҫ�������˻�");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				
				$('#clearChannelAccountId').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
			});
			
			
			function checkBoxToRadio(theForm,checkBox)    
			{      
				for (var i=0;i<theForm.elements.length;i++) {       
					 var e = theForm.elements[i];           
					 var eName = e.name;           
					  if (eName != 'allbox' && (e.type.indexOf("checkbox") == 0)) {                
					  	if(e!=checkBox)e.checked=false;                
					  	else e.checked=true;     
					   }  
					  } 
			}  
	 	</script>
	 	<title>�����˻�ѡ��</title>
	 	
	</head>

	<body>

		<form id="query" action="${CONTEXT_PATH}/channelAccountManage.do?action=queryPopUpChannelAccount" method="post" >
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">
						<table width="100%">
							<tr>
								<td align="center">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">�����˻�ѡ��</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td  height="30" align="right">
									�����к�
								</td>
								<td width="270" height="30">
									<input type="text" class="userbox_bt"  name="bankNo" id="bankNo"  value="${channelAccount.bankNo }"/>
								</td>
								<td  height="30" align="right">
									�˺�
								</td>
								<td height="30">
									<input class="userbox_bt"  name="accountNo" type="text" id="accountNo"  value="${channelAccount.accountNo }" />
								</td>
							</tr>
							<tr>
								<td  height="30" align="right">
									�˺�����
								</td>
								<td width="270" height="30">
									<input type="text" class="userbox_bt"  name="accountName" id="accountName"  value="${channelAccount.accountName }"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td  height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="��ѯ" />
									<input style="margin-left: 30px;" type="reset" value="���" />
								</td>
							</tr>
						</table>
					</div>
					<b class="b4"></b>
					<b class="b3"></b>
					<b class="b2"></b>
					<b class="b1"></b>
				</div>
			</div>
		</form>
		

		<!-- �����б��� -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<thead>
					<tr>
						<th align="center" nowrap="nowrap" class="titlebg">
							��ѡ��
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							�����к�
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							�����˺�
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							�����˻�����
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty channelAccountList}">
						<tr>
							<td colspan="10">
								û������
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty channelAccountList}">
						<c:forEach items="${channelAccountList}" var="channelAccount">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									<input name="channelAccount" type="radio"" value="${channelAccount.id}$${channelAccount.accountName}$${channelAccount.accountNo}"/>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${channelAccount.bankNo}
								</td>
								<td align="left" nowrap="nowrap">
									&nbsp;${channelAccount.accountNo}
								</td>
								<td align="left" nowrap="nowrap">
									&nbsp;${channelAccount.accountName}
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<f:paginate />
			<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="30" colspan="3" align="center">
						<input type="submit" value="ȷ��" id="selChannelAccountId"/>
						<input style="margin-left: 30px;" type="submit" value="���" id="clearChannelAccountId"/>
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