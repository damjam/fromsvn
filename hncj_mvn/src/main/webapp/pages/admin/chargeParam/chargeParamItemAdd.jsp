<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
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
			
			function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
			function updateInfo(id){
				var url="${uri}?action=toEdit&id="+id;
				gotoUrl(url);  
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=doAddItem" id="queryForm">
			<s:hidden name="id"/>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td> </td>
						 	<td >计费项</td>
						 	<td >计费类型</td>
						    <td >计费方式</td>
						    <td >计费规则</td>
						    <td >创建时间</td>
						    <td >备注</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>
									<input type="checkbox" name="itemIds" value="${element.id}">
								</td>
								<td>${ element.itemName}</td>
								<td><f:type className="ChargeType" value="${ element.item}" /> </td>
								<td><f:type className="ChargeWay" value="${ element.way}" /> </td>
								<td>${element.ruleDesc}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>${element.remark }</td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>
				<div class="btnbox clear">
					<input type="button" id="" onclick="save()" value="保存"/>
					<input type="button" id="" onclick="gotoUrl('${uri}?action=list')" value="返回"/>
				</div>		
			</div> 
		</form>
	</body>
</html>
