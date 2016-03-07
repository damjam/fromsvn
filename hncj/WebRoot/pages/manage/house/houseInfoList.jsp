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
					var url="/houseInfo.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('/houseInfo.do?action=delete&id='+id);
				}
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="houseInfo.do?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								楼号
							</td>
							<td>
								<s:select name="buildingNo" id="buildingNo" list="#request.buildingNos" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
							</td>
							<td class="formlabel">
								单元
							</td>
							<td>
								<s:select name="unitNo" id="unitNo" list="#request.unitNos" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
							</td>
							<td class="formlabel">
								楼层
							</td>
							<td>
								<s:select name="floor" id="floor" list="#request.floors" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
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
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >房屋编号</td>
						    <td >楼号</td>
						    <td >单元</td>
						    <td >楼层</td>
						    <td >描述</td>
						    <td >面积(平米)</td>
						    <td >交房日期</td>
						    <td >创建时间</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.buildingNo}</td>
								<td>${element.unitNo}</td>
								<td>${element.floor}</td>
								<td>${element.houseDesc}</td>
								<td><fmt:formatNumber value="${element.area}" pattern="##0.00"/></td>
								<td>${element.deliveryDate}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<a href="javascript:delInfo('${element.houseSn}')" >删除</a>
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
