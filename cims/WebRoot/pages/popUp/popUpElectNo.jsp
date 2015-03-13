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
				$('#selElectNo').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("��ѡ����Ҫ�ĵ������кţ����ҽ���һ��");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				
				$('#clearElectNo').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
			});
	 	</script>
	 	<title>�������к�ѡ��</title>
	 	
	</head>

	<body>

		<form id="query" action="${CONTEXT_PATH}/electronBankManage.do?action=queryPopUpElectNo" method="post" >
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
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">�������к�ѡ��</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td  height="30" align="right">
									�������к�
								</td>
								<td width="270" height="30">
									<input type="text" class="userbox_bt"  name="electNo" id="electNo"  value="${electronBank.electNo }"/>
								</td>
								<td  height="30" align="right">
									����
								</td>
								<td height="30">
									<input class="userbox_bt"  name="electName" type="text" id="electName"  value="${electronBank.electName }" />
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
							�������к�
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							����
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty electronBankList}">
						<tr>
							<td colspan="10">
								û������
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty electronBankList}">
						<c:forEach items="${electronBankList}" var="electronBank">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									<input name="electronBank" type="radio" value="${electronBank.electNo}$${electronBank.electName}"/>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${electronBank.electNo}
								</td>
								<td align="left" nowrap="nowrap">
									&nbsp;${electronBank.electName}
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
						<input type="submit" value="ȷ��" id="selElectNo"/>
						<input style="margin-left: 30px;" type="submit" value="���" id="clearElectNo"/>
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