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
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript">$(function(){
				$('#selBankTypeInfo').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的银行类型，有且仅有一条");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
				});
				
				$('#clearBankTypeInfo').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
				
			});
			
	    </script>
	</head>

	<body>
		<form id="bankTypeInfo" action="${CONTEXT_PATH}/bankTypeInfo.do?action=queryPopUpBankTypeInfo" method="post" >
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
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">银行行别选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									行别
								</td>
								<td  height="30">
									<input type="text"  style="width: 260px;" name="bankType" id="bankType" value="${bankTypeInfo.bankType }"/>
								</td>
								<td height="30" align="right">
									行别名称
								</td>
								<td height="30">
									<input type="text"  style="width: 260px;" name="bankName" id="bankName" value="${bankTypeInfo.bankName }"/>
								</td>
							</tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" />
									<input onclick="clearData();" style="margin-left: 30px;"
										type="button" value="清除" />
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

			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">
								请选择
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								行别
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								行别名称
							</th>
						</tr>
					</thead>
					
					
					<tbody>
						<c:if test="${empty bankTypeInfoList}">
							<tr>
								<td colspan="10">
									没有数据
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty bankTypeInfoList}">
							<c:forEach items="${bankTypeInfoList}" var="bankTypeInfo">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										<input name="bankType" type="radio" value="${bankTypeInfo.bankType}$${bankTypeInfo.bankName}"/>
									</td>
								
									<td align="center" nowrap="nowrap">
										&nbsp;${bankTypeInfo.bankType}
									</td>
									<td align="left" nowrap="nowrap">
										&nbsp;${bankTypeInfo.bankName}
									</td>
								</tr>
							</c:forEach>

						</c:if>
					</tbody>
				</table>
				<f:paginate />
			</div>
		</form>

		<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="submit" value="确定" id="selBankTypeInfo"  />
					<input style="margin-left: 30px;" type="submit" value="清除" id="clearBankTypeInfo"/>
					<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>