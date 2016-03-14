<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>

<%@ include file="/pages/common/taglibs.jsp" %>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title>角色信息列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		 
		<script type="text/javascript">
			$(function(){
				$('#btnQry').click(function(){
					$('#roleInfoForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("roleInfoForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/roleInfoAction.do?action=toAddPage";
					gotoUrl(url);   
				});
				
			});
			
			function deleteRoleInfo(roleId){
				if(confirm("确认要删除吗?")){
					var url="/roleInfoAction.do?action=deleteRoleInfo&roleId="+roleId;
					gotoUrl(url);
				}else{
					return false;
				}
			}
			
			function updateRoleInfo(roleId){
				var url="/roleInfoAction.do?action=toUpdatePage&roleId="+roleId;
				gotoUrl(url);
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg  />
		<form action="roleInfoAction.do?action=listRoleInfo" id="roleInfoForm" method="post">
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
								<s:textfield name="roleName" id="roleName" />
							</td>
							<td class="formlabel">
								权限组名称
							</td>
							<td>
								<s:textfield name="limitGroupName" id="limitGroupName" />
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
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>角色编号</td>
						    <td>角色名称</td>
						    <td>权限组编号</td>
						    <td>权限组名称</td>
						    <td>操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.roleId}</td>
							    <td>${element.roleName}</td>
							    <td>${element.limitGroupId}</td>
							    <td>${element.limitGroupName}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:void(0)" onclick="return updateRoleInfo('${element.roleId}');">修改</a>
							       		<a href="javascript:void(0)" onclick="return deleteRoleInfo('${element.roleId}');">删除</a>
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
