<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>
<html>
	<head>
		
		<title>用户列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
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
					var url="/userInfoAction.do?action=toAddPage";
					gotoUrl(url);   
				});
			});
			
			function updateUserInfo(userId){
				var url="/userInfoAction.do?action=toUpdatePage&userId="+userId;
				gotoUrl(url);
			}
			
			function deleteUserInfo(userId){
				if(confirm("确认要删除吗?")){
					var url="/userInfoAction.do?action=deleteUserInfo&userId="+userId;
					gotoUrl(url);
				}else{
					return false;
				}
			}
			
			function manageUserRole(userId){
				var url="/userRoleAction.do?action=toAssignRole&userId="+userId;
				gotoUrl(url);
			}
			function resetPwd(userId){
				if(window.confirm('确定重置密码?')){
					var url = '/userInfoAction.do?action=resetPwd&userId='+userId;
					gotoUrl(url);
				}
			}
		</script> 
	</head>
	<body>
		
		<f:msg  />
		<form action="userInfoAction.do?action=listUserInfo" id="userInfoForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								登录名
							</td>
							<td>
								<s:textfield name="loginId" id="loginId" maxlength="20"></s:textfield>
							</td>
							<td class="formlabel">
								用户名称
							</td>
							<td>
								<s:textfield name="userName" id="userName" maxlength="20"></s:textfield>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
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
						    <td>所属机构</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.userId}</td>
							    <td>${element.loginId}</td>
							    <td>${element.userName}</td>
							    <td>
							    	${element.userTypeName}
							    </td>
							    <td>${element.branchName}</td>
							    <td align="center">
							       <span class="redlink">
							       		<c:if test="${element.loginId ne 'super'}">
							       			<a href="javascript:void(0)" onclick="return updateUserInfo('${element.userId}');">修改</a>
								       		<a href="javascript:void(0)" onclick="return deleteUserInfo('${element.userId}');">删除</a>
								       		<a href="javascript:void(0)" onclick="return manageUserRole('${element.userId}');">用户角色管理</a>
							       		</c:if>
							       		<a href="javascript:void(0)" onclick="return resetPwd('${element.userId}');">密码重置</a>
							 	   </span>
							  </td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</form>
	</body>
</html>
