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
				
				$('#btnImport').click(function(){
					var url="${uri}?action=toImport";
					gotoUrl(url);   
				});
				$('#btnExport').click(function(){
					var url="${uri}?action=export";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id) {
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function updateInfo(id) {
				gotoUrl('${uri}?action=toEdit&id='+id);
			}
		</script> 
	</head>
	<body>
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
					<div class="widget-head">
	                    <div class="pull-left">${ACT.name}</div>
	                </div>
					<table class="form_grid">
						<tr>
							
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<%--
							<td class="formlabel">
								楼层
							</td>
							<td>
								<s:select name="floor" id="floor" list="#request.floors" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
							</td> --%>
							<td class="formlabel">
								出租状态
							</td>
							<td>
								<s:select name="state" id="state" list="#request.rentStates" listKey="value" listValue="name" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
							</td>
						</tr>
						<tr>
							<c:if test="${sessionScope.isHQ == true}">
								<td class="formlabel">机构</td>
								<td>
									<s:select name="branchNo" list="branches" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
								</td>
							</c:if>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="新增" id="btnAdd"/>&nbsp;
								<input type="button" value="导出" id="btnExport"/>&nbsp;
								<!--  
								<input type="button" value="导入" id="btnImport"/>&nbsp;
								-->
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
						 	<c:if test="${sessionScope.isHQ == true}">
						 		<td>机构</td>
						 	</c:if>
						 	<td >房屋编号</td>
						 	<td >出租状态</td>
						 	<td >租客信息</td>
						 	<!-- 
						    <td >楼号</td>
						    <td >楼层</td> -->
						    <td >面积(平米)</td>
						    <td >户型</td>
						    <td >装修类型</td>
						    <td >房价配置</td>
						    <td >付款要求</td>
						    <td >其他要求</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.houseSn}</td>
								<td><f:state className="RentState" value="${element.state}"/></td>
								<td>${element.renterId}</td>
								<td><fmt:formatNumber value="${element.area}" pattern="##0.00"/></td>
								<td>${element.houseType }</td>
								<td><f:type className="DecorateType" value="${element.decorateType}"/></td>
								<td>${element.roomConfig }</td>
							    <td>${element.payRule }</td>
							    <td>${element.otherRule }</td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.houseSn}')">修改</a>
							    	<a href="javascript:delInfo('${element.houseSn}')">删除</a>
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
