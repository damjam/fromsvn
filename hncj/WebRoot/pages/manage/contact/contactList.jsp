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
					gotoUrl('${uri}?action=toEdit');
				});
				
			});
			function update(id){
				gotoUrl('${uri}?action=toEdit&id='+id);
			}
			
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('${uri}?action=del&id='+id);
			}
			function refund(id){
				if(!window.confirm("确认退款?")){
					return;
				}
				gotoUrl('${uri}?action=refund&id='+id);
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								姓名
							</td>
							<td>
								<s:textfield name="contactName" id="contactName" maxlength="20"/>
							</td>
							<td class="formlabel">
								电话
							</td>
							<td>
								<s:textfield name="mobile" id="mobile" maxlength="20"/>
							</td>
							<td class="formlabel">
								备注关键字
							</td>
							<td>
								<s:textfield name="remark" id="remark" maxlength="20"/>
							</td>
					    </tr>
						<c:if test="${sessionScope.isHQ == true}">
							<tr>
								<td class="formlabel">机构</td>
								<td>
									<s:select list="branches" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
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
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<c:if test="${sessionScope.isHQ == true}">
						 		<td>机构</td>
						 	</c:if>
						 	<td >姓名</td>
						 	<td >性别</td>
						 	<td >行业</td>
						    <td >职位</td>
						    <td >电话</td>
						    <td >工作单位</td>
						    <td>创建时间</td>
						    <td >备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.contactName}</td>
								<td><f:type className="SexType" value="${element.sex}" /> </td>
								<td>${element.industry}</td>
								<td>${element.position}</td>
								<td>${element.mobile}</td>
								<td>${element.merchantName}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${element.remark}</td>
								<td class="redlink">
						    		<a href="javascript:update('${element.id}')">修改</a>
						    		<a href="javascript:delRecord('${element.id}')">删除</a>
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
