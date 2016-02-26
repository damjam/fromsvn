<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>字典管理页面</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		
		<script type="text/javascript">
			 $(function(){
				$("#btnQry").click(function(){
					$("#branchDictForm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset();
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/branchDict.do?action=toAdd");
				});
				
			 });
			
			 function del(dictValue,dictType){
				 if(window.confirm('确认删除?')){
					 var url="/branchDict.do?action=delete&id.dictValue="+dictValue+"&id.dictType="+dictType;
					 gotoUrl(url);
				 }
				
			 }
			 
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />

		<form action="${CONTEXT_PATH}/branchDict.do?action=list" id="branchDictForm" method="post">
			<div class="userbox">
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" >
						 <caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">名称</td>
							<td><s:textfield name="dictName"></s:textfield></td>
							<td class="formlabel">类型</td>
							<td>
								<s:select name="dictType" headerKey="" headerValue="---全部---" list="#request.dictTypes" listKey="value" listValue="name"/>
							</td>
							<c:if
								test="${sessionScope.branchNo eq '0000' or sessionScope.branchNo == null}">
								<td class="formlabel" align="left">机构</td>
								<td><s:select list="#request.branches" name="branchNo"
										listKey="key" listValue="value" headerKey=""
										headerValue="---全部---" /></td>
							</c:if>
							<c:if
								test="${sessionScope.branchNo != null && sessionScope.branchNo ne '0000' }">
								<td class="formlabel" align="left">&nbsp;</td>
								<td></td>
							</c:if>
						</tr>
						<tr>
							<td>
							</td>	
							<td colspan="3">
								<input type="button" value="查询"  id="btnQry"/>&nbsp;
								<input type="button" value="重置"  id="btnClear"/>&nbsp;
								<input type="button" value="新增"  id="btnAdd"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
			
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>机构</td>
						 	<td>名称</td>
						    <td>值</td>
						    <td>类型</td>
						    <td>备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.branchName}</td>
								<td>${element.dictName}</td>
								<td>${element.id.dictValue}</td>
							     <td><f:type className="BranchDictType" value="${element.id.dictType}" /></td>
							     <td>${element.remark}</td>
							    <td align="center">
							       <span class="redlink">
							 	   		<a href="javascript:del('${element.id.dictValue}','${element.id.dictType}')" id="hrefDelete"">删除</a>
							 	   </span>
							  </td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div>
		</form>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>