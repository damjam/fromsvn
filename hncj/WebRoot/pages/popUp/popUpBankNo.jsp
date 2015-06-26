<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
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
				$('#selBankNo').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的银行行号");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				
				$('#clearBankNo').click(function(){
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
	 	<title>银行行号选择</title>
	 	
	</head>

	<body>

		<form id="query" action="${CONTEXT_PATH}/bankInfo.do?action=queryPopUpBankNo" method="post" >
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
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">银行行号选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td  height="30" align="right">
									银行行号
								</td>
								<td width="270" height="30">
									<input type="text" class="userbox_bt"  name="bankNo" id="bankNo"  value="${bankInfo.bankNo }"/>
								</td>
								<td  height="30" align="right">
									银行名称
								</td>
								<td height="30">
									<input class="userbox_bt"  name="bankName" type="text" id="bankName"  value="${bankInfo.bankName }" />
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td  height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" />
									<input style="margin-left: 30px;" type="reset" value="清除" />
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
		

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<thead>
					<tr>
						<th align="center" nowrap="nowrap" class="titlebg">
							请选择
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							银行行号
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							银行名称
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty bankInfoList}">
						<tr>
							<td colspan="10">
								没有数据
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty bankInfoList}">
						<c:forEach items="${bankInfoList}" var="bankInfo">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									<input name="bankInfo" type="radio"" value="${bankInfo.bankNo}$${bankInfo.bankName}"/>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${bankInfo.bankNo}
								</td>
								<td align="left" nowrap="nowrap">
									&nbsp;${bankInfo.bankName}
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
						<input type="submit" value="确定" id="selBankNo"/>
						<input style="margin-left: 30px;" type="submit" value="清除" id="clearBankNo"/>
						<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
					</td>
				</tr>
			</table>
			<br />
			<br />
			<br />
		</div>
	</body>
</html>