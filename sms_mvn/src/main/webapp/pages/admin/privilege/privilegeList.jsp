<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>权限管理页面</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<script type="text/javascript">
			 $(function(){
				$("#btnQry").click(function(){
					$("#privilegeFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#privilegeFrm");
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/privilegeAction.do?action=toEditPrivilege");
				});
				
			 });
			
			 function deletePrivilege(limitId){
				 var url="/privilegeAction.do?action=deletePrivilege&limitId="+limitId;
				 gotoUrl(url);
			 }
			 
			 function gotoPrivilegeResource(limitId){
				 var url="/privilegeResourceAction.do?action=listPrivilegeResource&limitId="+limitId;
				 gotoUrl(url);
			 }
		</script>
	</head>
    
	<body>
		
		<f:msg />
		<form action="privilegeAction.do?action=listPrivs" id="privilegeFrm" method="post">
			<div class="userbox">
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" >
						 <caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel" align="left">权限ID</td>
							<td><s:textfield name="limitId" maxlength="50"/></td>
							<td class="formlabel">权限名称</td>
							<td><s:textfield name="limitName"></s:textfield></td>
							<td class="formlabel" align="left">
								父级权限ID
							</td>
							<td>
								<s:textfield name="parent" id="parent" class="userbox_bt"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="3">
								<input type="button" value="查询"  id="btnQry"/>&nbsp;
								<input type="button" value="重置"  id="btnClear"/>&nbsp;
								<input type="button" value="新增"  id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
			
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>权限ID</td>
						    <td>权限名称</td>
						    <td>父节点</td>
						    <td>菜单(?)</td>
						    <td>顺序</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr>
								<td>${element.limitId}</td>
							    <td>${element.limitName}</td>
							    <td>${element.parent}</td>
							    <td>${element.isMenu}</td>
							    <td>${element.menuOrder}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:deletePrivilege('${element.limitId}')" id="hrefDelete">删除</a>
							 	  		<a href="javascript:gotoPrivilegeResource('${element.limitId}')" id="hrefPrivilegeResource">资源管理</a>
							 	   </span>
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