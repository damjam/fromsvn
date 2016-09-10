<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		<f:css href="/css/page.css" />
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/layer/layer.js"/>
		<script type="text/javascript">
			
		</script> 
	</head>
	<body>
		<!-- 数据列表区 -->
		<div class="tablebox">			
			<table class="data_grid">
				<thead>
					 <tr align="center" class="titlebg">
					 	<td >指派责任人</td>
					 	<td >指派时间</td>
					 	<td >反馈结果</td>
					 	<td >反馈时间</td>
					 	<td >备注</td>
					 </tr>
				</thead>
				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<c:forEach items="${list}" var="element">
						<tr align="center">
							<td>${element.processor}</td>
							<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${element.feedback}</td>
							<td><fmt:formatDate value="${element.feedbackDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${element.remark}</td>
					    </tr>
					</c:forEach>
				</f:showDataGrid>
			</table>
		</div> 
	</body>
</html>
