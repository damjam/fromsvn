<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
<head>

<title>用户角色分配</title>
<f:css href="/css/page.css" />
<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<f:js src="/js/validate.js" />
<f:js src="/js/sys.js" />
<f:js src="/js/common.js" />
<f:js src="/js/paginater.js" />

<script type="text/javascript">
	$(function() {

		$('#btnQry').click(function() {
			$('#userInfoForm').submit();
		});

		$('#btnReturn').click(function() {
			var url = "/userInfoAction.do?action=listUserInfo";
			gotoUrl(url);
		});
	});

	function assignRole() {
		FormUtils.submitFirstTokenForm();
	}
</script>
</head>
<body>

	<f:msg />
	<form action="userRoleAction.do?action=assignRole" id="userRoleForm"
		method="post">
		<div class="userbox">

			<!-- 数据列表区 -->
			<s:hidden name="userId" id="userId" />
			<div class="tablebox">
				<div class="widget">
					<table class="data_grid">
						<caption class="widget-head">${ACT.name}</caption>
						<tr align="center" class="titlebg">
							<td><input type="checkbox"
								onclick="FormUtils.selectAll(this, 'roleIds')" />全选</td>
							<td>角色编号</td>
							<td>角色名称</td>
							<td>权限组编号</td>
							<td>权限组名称</td>
						</tr>

						<f:showDataGrid name="list" msg=" " styleClass="data_grid">
							<c:forTokens items="" delims=""></c:forTokens>
							<c:forEach items="${list}" var="element">
								<tr align="center">
									<td><c:if test="${empty roleIds}">
											<input type="checkbox" name="roleIds"
												value="${element.roleId}">
										</c:if> <c:forEach items="${roleIds}" var="roleId">
											<c:if test="${roleId eq element.roleId}">
												<input type="checkbox" name="roleIds"
													value="${element.roleId}" checked="checked">
											</c:if>
											<c:if test="${roleId ne element.roleId}">
												<input type="checkbox" name="roleIds"
													value="${element.roleId}">
											</c:if>
										</c:forEach></td>
									<td>${element.roleId}</td>
									<td>${element.roleName}</td>
									<td>${element.limitGroupId}</td>
									<td>${element.limitGroupName}</td>
								</tr>
							</c:forEach>
						</f:showDataGrid>
					</table>
					<f:paginate />
				</div>
			</div>

			<div class="btn_box">
				<input type="button" value="提交" onclick="assignRole()" />&nbsp; 
				<input type="button" value="返回" id="btnReturn" />
			</div>
		</div>
	</form>
</body>
</html>
