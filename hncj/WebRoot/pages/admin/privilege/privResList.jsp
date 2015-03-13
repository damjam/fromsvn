<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function goBack(){
				history.go(-1);
				/*
				var parent = $('#parent').val();
				gotoUrl('/privilegeAction.do?action=listPrivs&parent='+parent);*/
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg"/>
		<html:form action="/privilegeAction.do?action=listPrivRes" styleId="searchForm">
		<html:hidden property="parent" styleId="parent"/>
		<input type='button' onclick="goBack();" value='返回' />
		<!-- 数据列表区 -->
		<div class="tablebox">			
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					 <tr align="center" class="titlebg">
					    <td>limit_id</td>
					    <td>url</td>
					    <td>param</td>
					    <td>is_entry</td>
					    <td>操作</td>
					 </tr>
				</thead>
				
				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<logic:iterate id="element" name="list">
						<tr align="center">
							<td>${element.limitId}</td>
							<td>${element.url}</td>
							<td>${element.param}</td>
							<td>${element.isEntry}</td>
							<td class="redlink">
								<html:link action="/privilegeAction.do?action=toEditPriRes&id=${element.id}">修改</html:link>
								<html:link action="/privilegeAction.do?action=delPriRes&id=${element.id}">删除</html:link>
							</td>
						</tr>
					</logic:iterate>
				</f:showDataGrid>
			</table>
			<f:paginate/>
		</div>
		</html:form>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>