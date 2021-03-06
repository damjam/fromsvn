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
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				
				$('#btnAdd').click(function(){
					var url="${uri}?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function updateInfo(id){
				var url="${uri}?action=toEdit&id="+id;
				gotoUrl(url);  
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >汽车品牌-型号</td>
						 	<td >产品名称</td>
						    <td >材质</td>
						    <td >颜色</td>
						    <td >单价</td>
						    <td >数量</td>
						    <td >订单金额</td>
						    <td >派送方式</td>
						    <td >物流状态</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.carModel}</td>
								<td>${element.productName}</td>
								<td>${element.material}</td>
								<td>${element.color}</td>
								<td><fmt:formatNumber value="${element.price}" pattern="##0.00"/></td>
								<td>${element.num}</td>
								<td><fmt:formatNumber value="${element.amount}" pattern="##0.00"/></td>
							    <td>${element.deliType}</td>
							    <td>${element.deliState}</td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.id}')" >变更物流状态</a>
							    	<a href="javascript:delInfo('${element.id}')" >删除</a>
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
