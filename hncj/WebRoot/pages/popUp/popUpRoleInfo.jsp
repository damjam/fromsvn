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
		<title>角色选择</title>
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
		<script type="text/javascript">
			$(function(){
				$('#selRoleInfo').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的角色，有且仅有一条");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
					});
				
					$('#clearRoleInfo').click(function(){
						var selValue="";
						window.returnValue="$";
						window.close();
					});
					
					$('#winClose').click(function(){
						window.close();
					});
					
					$('#btnClear').click(function(){
						$("form :text").val('');
					});
				
			});
			
    	</script>
	</head>

	<body>
		<form id="roleInfo" action="${CONTEXT_PATH}/roleInfoAction.do?action=queryPopUpRoleInfo" method="post" >
				<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td colspan="4" align="left">
									<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">角色选择</span>
								</td>
							</tr>
							<tr>
								
								<td height="30" align="right">
									角色名称
								</td>
								<td height="30">
									<input  style="width: 260px;" name="roleName" type="text"  value="${param.roleName }"/>
								</td>
								<td height="30" align="right" nowrap="nowrap">
									权限组
								</td>
								<td  height="30">
									<input type="text"  style="width: 260px;" name="limitGroupName"  value="${param.limitGroupName }"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" /> 
									<input id="btnClear" style="margin-left: 30px;" type="button" value="清除" />
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
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th align="center" nowrap="nowrap" class="titlebg">
							请选择
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							角色编号
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							角色名称
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							权限组
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							用户类型
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							机构
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							商户
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							部门
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty roleInfoList}">
						<tr>
							<td colspan="10">
								没有数据
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty roleInfoList}">
						<c:forEach items="${roleInfoList}" var="roleInfo">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									&nbsp;<input name="roleInfo" type="radio" value="${roleInfo.roleId}$${roleInfo.roleName}"/>
								</td>
							
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.roleId}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.roleName}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.limitGroupName}
								</td>
								<td align="center" nowrap="nowrap">
									<c:if test="${roleInfo.userType eq '0'}">银联网络本部</c:if>
									<c:if test="${roleInfo.userType eq '1'}">银联体系机构</c:if>
									<c:if test="${roleInfo.userType eq '2'}">渠道机构</c:if>
									<c:if test="${roleInfo.userType eq '3'}">第三方代理机构</c:if>
									<c:if test="${roleInfo.userType eq '4'}">收单机构</c:if>
									<c:if test="${roleInfo.userType eq '5'}">银联分支机构</c:if>
									<c:if test="${roleInfo.userType eq '30'}">商户</c:if>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.branchName}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.merchantName}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${roleInfo.deptName}
								</td>
							</tr>
						</c:forEach>

					</c:if>
				</tbody>
			</table>
			<f:paginate />
	</div>
		<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="submit" value="确定" id="selRoleInfo"/>
					<input style="margin-left: 30px;" type="submit" value="清除" id="clearRoleInfo"/>
					<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>