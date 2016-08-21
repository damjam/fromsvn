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
		<f:js src="/layer/layer.js"/>
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
			function updateInfo(id){
				gotoUrl('${uri}?action=toEdit&id='+id);
			}
			function changeState(id, state){
				if(window.confirm("确认操作?")){
					gotoUrl('${uri}?action=changeState&id='+id+'&state='+state);
				}
			}
			function delInfo(id) {
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function toTransfer(id){
				gotoUrl('${uri}?action=toTransfer&id='+id);
			}
			
			function toApplyVoc(id){
				gotoUrl('${uri}?action=toAddVocation&id='+id);
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
								<s:textfield name="name" id="name" maxlength="10"/>
							</td>
							<td class="formlabel">
								手机号
							</td>
							<td>
								<s:textfield name="tel" id="tel" maxlength="10"/>
							</td>
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
								<input type="button" value="导入" id="btnImport"/>&nbsp;
								<input type="button" value="导出" id="btnExport"/>&nbsp;
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
						 	<td >职位</td>
						    <td >手机号码</td>
						    <td >居住地址</td>
						    <td >入职日期</td>
						    <td >状态</td>
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
								<td>${element.name}</td>
								<td><f:type className="SexType" value="${element.gender}"/> </td>
								<td>${element.positionName}</td>
								<td>${element.tel}</td>
								<td>${element.livePlace}</td>
								<td>${element.hireDate}</td>
								<td><f:state className="EmployeeState" value="${element.state}"/></td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
						    		<a href="javascript:updateInfo('${element.id}')">修改</a>
						    		<c:if test="${sessionScope.BRANCH_NO eq '0000'}">
						    			<a href="javascript:toTransfer('${element.id}')">异动</a>
						    		</c:if>
						    		<c:if test="${element.state eq '00' }">
						    			<a href="javascript:toApplyVoc('${element.id}')" >休假</a>
						    		</c:if>
						    		<c:if test="${element.state eq '01' }">
						    			<a href="javascript:changeState('${element.id}', '00')" >复职</a>
						    		</c:if>
						    		<a href="${uri}?action=detail&id=${element.id}">详情</a>
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
