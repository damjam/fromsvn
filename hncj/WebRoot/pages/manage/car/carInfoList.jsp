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
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
					<div class="widget-head">
                  <div class="pull-left">${ACT.name}</div>
                </div>
					<table class="form_grid">
						<tr>
							<td class="formlabel">
								车主姓名
							</td>
							<td>
								<s:textfield name="ownerName" id="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								车牌号
							</td>
							<td>
								<s:textfield name="carSn" id="carSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								车主手机号
							</td>
							<td>
								<s:textfield name="ownerCel" id="ownerCel" maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								车主房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								品牌
							</td>
							<td>
								<s:textfield name="brand" id="brand" maxlength="10"/>
							</td>
							<td class="formlabel">
								型号
							</td>
							<td>
								<s:textfield name="model" id="model" maxlength="10"/>
							</td>
						</tr>
						<c:if test="${sessionScope.isHQ == true}">
							<tr>
								<td class="formlabel">机构</td>
								<td>
									<s:select list="branches" name="branchNo" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
								</td>
							</tr>
						</c:if>	
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
						 	<c:if test="${sessionScope.isHQ == true}">
						 		<td>机构</td>
						 	</c:if>
						 	<td >车牌号</td>
						    <td >品牌型号</td>
						    <td >车主姓名</td>
						    <td >车主电话</td>
						    <td >车主房屋编号</td>
						    <td >车位号</td>
						    <td >创建时间</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.carSn}</td>
								<td>${element.brand}${element.model}</td>
								<td>${element.ownerName}</td>
								<td>${element.ownerCel}</td>
								<td>${element.houseSn}</td>
								<td>${element.parkingSn}</td>
								<td>
									<fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.id}')" >修改</a>
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
