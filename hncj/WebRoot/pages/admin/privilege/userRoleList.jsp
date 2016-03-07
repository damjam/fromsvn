<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>

<%@ include file="/pages/common/taglibs.jsp" %>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title>用户角色列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		 
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#userInfoForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("userInfoForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/userRoleAction.do?action=toAssignRolePage&userId="+$("#userId").val();
					gotoUrl(url);   
				});
				
				$('#btnReturn').click(function(){
					var url="/userInfoAction.do?action=listUserInfo";
					gotoUrl(url);   
				});
			});
			
			
			function deleteUserRole(userId,roleId){
				if(confirm("确认要删除吗?")){
					var url="/userRoleAction.do?action=deleteUserRole&userId="+userId+"&roleId="+roleId;
					gotoUrl(url);
				}else{
					return false;
				}
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg  />
		<form action="/userRoleAction.do?action=listUserRole" id="userInfoForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption> 
						<tr>
							<td class="formlabel">
								角色名称
							</td>
							<td>
								<s:textfield name="roleName" id="roleName" maxlength="20"></s:textfield>
								<s:hidden name="userId" id="userId"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="清除" id="btnClear"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>&nbsp;
								<input type="button" value="返回" id="btnReturn"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>用户编号</td>
						    <td>登录名</td>
						    <td>用户名</td>
						    <td>用户类型</td>
						    <td>角色ID</td>
						    <td>角色名称</td>
						    <td>操作类型</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<s:iterator value="list">
							<tr>
								<td>${id.userId}</td>
							    <td>${loginId}</td>
							    <td>${userName}</td>
							    <td>${userType}</td>
							    <td>${id.roleId}</td>
							    <td>${roleName}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:void(0)" onclick="return deleteUserRole('${element.id.userId}','${element.id.roleId}');">删除</a>
							 	   </span>
							  </td>
						    </tr>
						</s:iterator>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</form>
	</body>
</html>
