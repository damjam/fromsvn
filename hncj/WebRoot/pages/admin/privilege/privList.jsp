<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<script type="text/javascript">
			var parent = '${param.parent}';
			function goBack(){
				//gotoUrl('/privilegeAction.do?action=listPrivs&parent='+);
			}
		</script>
	</head>
    
	<body>
		
		<f:msg />
		<form action="privilegeAction.do?action=listPrivs" id="searchForm" method="post">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
						<tr>
							<td class="formlabel" align="left">权限ID</td>
							<td><s:textfield name="limitId" maxlength="50"/></td>
							<td class="formlabel" align="left">
								权限名称
							</td>
							<td>
								<s:textfield name="limitName" id="limitName" class="userbox_bt"/>
							</td>
							<td class="formlabel" align="left">
								父级权限ID
							</td>
							<td>
								<s:textfield name="parent" id="parent" class="userbox_bt"/>
							</td>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5" align="left">							
							<input type="submit" value="查询" id="query" name="ok" />
							<input style="margin-left:20px;" type="button" value="重置"  onclick="FormUtils.reset('searchForm')" />&nbsp;&nbsp;
							${privilege.parent}
							<c:if test="${!empty privilege.parent}">
								<input type="button" onclick="javascript:history.go(-1)" value="返回"/>
							</c:if>
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
					    <td>权限ID</td>
					    <td>权限名称</td>
					    <td>父级权限ID</td>
					    <td>是否菜单</td>
					    <td>操作</td>
					 </tr>
				</thead>
				
				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<c:forEach items="${list}" var="element">
						<tr align="center">
							<td>${element.limitId}</td>
							<td>${element.limitName}</td>
							<td>${element.parent}</td>
							<td>
								<c:if test="${element.isMenu eq 'Y'}">是</c:if>
								<c:if test="${element.isMenu eq 'N'}">否</c:if>
							</td>
							<td class="redlink">
								<c:if test="${element.hasSubPris eq 'Y'}">
									<a href="privilegeAction.do?action=listPrivs&parent=${element.limitId}">子权限</a>
								</c:if>
								
								<a href="privilegeAction.do?action=toEditPrivilege&parent=${element.limitId}">添加子权限</a>
								<c:if test="${!(element.hasSubPris eq 'Y')}">
									<a href="privilegeResourceAction.do?action=listPriRes&limitId=${element.limitId}">权限资源</a>
									<a href="privilegeResourceAction.do?action=toEdit&limitId=${element.limitId}">添加权限资源</a>
								</c:if>
								 <!-- 
								<a href="privilegeAction.do?action=toEditPrivilege&limitId=${element.limitId}">修改</a>
								<c:if test="${element.hasSubPris ne 'Y'}">
									<a href="privilegeAction.do?action=delPri&limitId=${element.limitId}">删除</a>
								</c:if>
								 -->
							</td>
						</tr>
					</c:forEach>
				</f:showDataGrid>
			</table>
			<f:paginate/>
		</div>
		</form>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>