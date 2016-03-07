<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>权限资源管理</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<script type="text/javascript">
			 $(function(){
				$("#btnQry").click(function(){
					$("#privilegeResourceFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#privilegeResourceFrm");
				});
				
				$("#btnAdd").click(function(){
					var url="/privilegeResourceAction.do?action=toAddPage&limitId="+$("#limitId").val();
					gotoUrl(url);
				});
				
				$("#btnReturn").click(function(){
					var url="/privilegeAction.do?action=listPrivs";
					gotoUrl(url);
				}); 
			 });
			
			 function deletePrivilegeResource(id){
				 var url="/privilegeResourceAction.do?action=deletePrivilegeResource&id="+id;
				 gotoUrl(url);
			 }
		</script>
	</head>
    
	<body>
		
		<f:msg />
		<form action="privilegeResourceAction.do?action=listPrivilegeResource" id="privilegeResourceFrm" method="post">
			<div class="userbox">
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" >
						 <caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">权限名称</td>
							<td>
								<s:textfield name="limitName" id="limitName"></s:textfield>
								<s:hidden name="limitId" id="limitId"/>
							</td>
						</tr>
						<tr>
							<td></td>
							<td colspan="5">
								<input type="button" value="查询"  id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear"/>&nbsp;
								<input type="button" value="新增"  id="btnAdd"/>&nbsp;
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
						    <td>资源ID</td>
						    <td>权限ID</td>
						    <td>URL</td>
						    <td>Method</td>
						    <td>菜单</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr>
								<td>${element.id}</td>
							    <td>${element.limitId}</td>
							    <td>${element.url}</td>
							    <td>${element.param}</td>
							    <td>${element.isEntry}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:deletePrivilegeResource('${element.id}')" id="hrefDelete">删除</a>
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