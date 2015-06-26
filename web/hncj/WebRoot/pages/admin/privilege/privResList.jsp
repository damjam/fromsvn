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
			function goBack(){
				history.go(-1);
				/*
				var parent = $('#parent').val();
				gotoUrl('/privilegeAction.do?action=listPrivs&parent='+parent);*/
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		<form action="privilegeResourceAction.do?action=listPrivRes" id="searchForm" method="post">
		<s:hidden name="parent" id="parent"/>
		<input type='button' onclick="goBack();" value='返回' />
		<!-- 数据列表区 -->
		<div class="tablebox">			
			<table class="data_grid">
				<thead>
					 <tr align="center" class="titlebg">
					    <td>limit_id</td>
					    <td>url</td>
					    <td>param</td>
					    <td>is_entry</td>
					    <td>操作</td>
					 </tr>
				</thead>
				
				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<c:forEach items="${list}" var="element">
						<tr align="center">
							<td>${element.limitId}</td>
							<td>${element.url}</td>
							<td>${element.param}</td>
							<td>${element.isEntry}</td>
							<td class="redlink">
								<!-- 
								<a href="privilegeResourceAction.do?action=toEdit&id=${element.id}">修改</a>
								 --><a href="privilegeResourceAction.do?action=delete&id=${element.id}">删除</a>
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