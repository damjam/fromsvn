<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<title></title>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
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
			$().ready(function(){
				//var height = document.body.scrollHeight;
				var height = $(document.body).height();
				parent.adjustHeight(height, 1);
			});
			function showItems(id){
				var url="${uri}?action=toAddItem&id="+id;
				gotoUrl(url);  
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								房屋类型
							</td>
							<td>
								<s:select name="rangeCode" id="rangeCode" list="#request.paramRanges" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
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
						    <td >计费适用类型</td>
						    <td >计费对象</td>
						    <td >计费项</td>
						    <td >备注</td>
						    <td >创建时间</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td><f:type className="ParamRange" value="${element.rangeCode}" /> </td>
								<td>${element.chargeObj}</td>
								<td>${element.chargeDesc}</td>
								<td>${element.remark}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.id}')" >修改</a>
							    	<a href="javascript:delInfo('${element.id}')" >删除</a>
							    	<a href="javascript:showItems('${element.id}')" >计费项</a>
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
