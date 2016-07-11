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
			
			function delInfo(id) {
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			
			function showDetail(url){
				layer.open({
					title:'详情',
				    type: 2,
				    area: ['720px', '350px'],
				    fix: false, //不固定
				    maxmin: true,
				    content: url
				});
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
								物料名称
							</td>
							<td>
								<s:textfield name="item" id="item" maxlength="10"/>
							</td>
							<td class="formlabel">
								状态
							</td>
							<td>
								<s:select list="#request.materialStates" headerKey="" headerValue="---请选择---" listKey="value" listValue="name" name="state"/>
							</td>
							<s:if test="#session.isHQ == true">
								<td class="formlabel">机构</td>
								<td>
									<s:select name="branchNo" list="branches" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
								</td>
							</s:if>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="新增" id="btnAdd"/>&nbsp;
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
						 	<td >物料名称</td>
						 	<td >品牌</td>
						 	<td >型号</td>
						 	<!-- 
						    <td >购置日期</td>
						    <td >购置人</td>
						    <td >购置来源</td> -->
						    <td >存放位置</td>
						    <td >责任人</td>
						    <td >状态</td>
						    <td >备注</td>
						    <td >录入时间</td>
						    <td >录入员</td>
						    <td >操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.item}</td>
								<td>${element.brand}</td>
								<td>${element.modal}</td>
								<td>${element.place}</td>
								<td>${element.manager}</td>
								<td><f:state className="MaterialState" value="${element.state}"/></td>
								<td>${element.remark}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>${element.createUser}</td>
							    <td class="redlink">
						    		<a href="javascript:updateInfo('${element.id}')">修改</a>
						    		<a href="javascript:delInfo('${element.id}')">删除</a>
						    		<a href="javascript:void(0)" onclick="showDetail('${uri}?action=detail&id=${element.id}')">查看详情</a>
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
