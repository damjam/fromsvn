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
					$("#sysDictFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#sysDictFrm");
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/sysDictAction.do?action=toAddPage");
				});
				
			 });
			
			 function deletesysDict(dictValue,dictType){
				 var url="/sysDictAction.do?action=deleteSysDict&id.dictValue="+dictValue+"&id.dictType="+dictType;
				 gotoUrl(url);
			 }
			 
		</script>
	</head>
    
	<body>
		
		<f:msg />

		<form action="sysDictAction.do?action=listSysDict" id="sysDictFrm" method="post">
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
								<s:select name="id.dictType" headerKey="" headerValue="---全部---" list="#request.dictTypes" listKey="value" listValue="name"/>
							</td>
						</tr>
						<tr>
							<td>
							</td>	
							<td>
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
				<table class="data_grid">
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
							     <td><f:type className="SysDictType" value="${element.id.dictType}" /></td>
							     <td>${element.remark}</td>
							    <td align="center">
							       <span class="redlink">
							 	   		<a href="javascript:deletesysDict('${element.id.dictValue}','${element.id.dictType}')" id="hrefDelete">删除</a>
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