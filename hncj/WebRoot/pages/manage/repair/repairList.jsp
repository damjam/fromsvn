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
			function assign(id){
				gotoUrl('${uri}?action=toAssign&id='+id);
			}
			
			function toAddTrack(id){
				gotoUrl('${uri}?action=toAddTrack&id='+id);
			}
			function cancel(){
				if(window.confirm("确认撤销?")){
					gotoUrl('${uri}?action=cancel&id='+id);
				}
			}
			function showTrack(url){
				layer.open({
					title:'处理记录',
				    type: 2,
				    area: ['720px', '230px'],
				    fix: false, //不固定
				    maxmin: true,
				    content: url
				});
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
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
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								状态
							</td>
							<td>
								<s:select list="#request.repairStates" headerKey="" headerValue="---请选择---" listKey="value" listValue="name" name="state"/>
							</td>
							<c:if test="${sessionScope.BRANCH_NO eq '0000' || sessionScope.BRANCH_NO == null}">
								<td class="formlabel">机构</td>	
								<td><s:select list="#request.branches" headerKey="" headerValue="---请选择---" listKey="key" listValue="value" name="branchNo"/></td>
							</c:if>
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
						 	<td >报事报修人</td>
						 	<td >类型</td>
						 	<td >详情</td>
						    <td >联系电话</td>
						    <td >重要程度</td>
						    <td >紧急程度</td>
						    <td >当前责任人</td>
						    <td >状态</td>
						    <td >处理结果</td>
						    <td >备注</td>
						    <td >创建时间</td>
						    <td >操作员</td>
						    <td >操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.reporter}</td>
								<td><f:type className="ReporterType" value="${element.reporterType}"/> </td>
								<td>${element.item}</td>
								<td>${element.reporterTel}</td>
								<td><f:type className="ImpIndexType" value="${element.impIndex}"/></td>
								<td><f:type className="EmgIndexType" value="${element.emgIndex}"/></td>
								<td>${element.processor}</td>
								<td><f:state className="RepairState" value="${element.state}"/></td>
								<td>${element.feedback}</td>
								<td>${element.remark}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>${element.createUser}</td>
							    <td class="redlink">
							    	<c:if test="${element.state eq '00'}"><!-- 未处理状态下可修改 -->
							    		<a href="javascript:updateInfo('${element.id}')">修改</a>
							    		<a href="javascript:delInfo('${element.id}')">删除</a>
							    		<a href="javascript:assign('${element.id}')">指派责任人</a>
							    	</c:if>
						    		<a href="javascript:cancel('${element.id}')">撤销</a><!-- 任何情况下可撤销 -->
						    		<c:if test="${element.state eq '01'}"><!-- 处理中 -->
						    			<a href="javascript:toAddTrack('${element.id}')">添加处理结果</a>
						    		</c:if>
						    		<c:if test="${element.feedbackDate != null}">
						    			<a href="javascript:void(0)" onclick="showTrack('${uri}?action=showTrack&id=${element.id}')">查看处理记录</a>
						    		</c:if>
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
