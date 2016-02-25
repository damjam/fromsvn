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
					$("#branchDictFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#branchDictFrm");
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/branchDictAction.do?action=toAddPage");
				});
				
			 });
			
			 function del(dictValue,dictType){
				 var url="/branchDictAction.do?action=delete&id.dictValue="+dictValue+"&id.dictType="+dictType;
				 gotoUrl(url);
			 }
			 
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />

		<form action="branchDictAction.do?action=listbranchDict" id="branchDictFrm" method="post">
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
						</tr>
						<tr>
							<td>
							</td>	
							<td>
								<input type="button" value="查询"  id="btnQry"/>&nbsp;
								<input type="button" value="清除"  id="btnClear"/>&nbsp;
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
						    <td>值</td>
						    <td>名称</td>
						    <td>类型</td>
						    <td>备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id.dictValue}</td>
							    <td>${element.dictName}</td>
							     <td><f:type className="branchDictType" value="${element.id.dictType}" /></td>
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