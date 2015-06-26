<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title>推送资讯列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				$('#btnAdd').click(function(){
					var url="/pushMngAction.do?action=toAdd";
					gotoUrl(url);   
				});
			});
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/pushMngAction.do?action=list" styleClass="validate-tip" styleId="dataForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								创建日期
							</td>
							<td>
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker()"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker()"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="submit" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" onclick="FormUtils.reset('dataForm');"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>编号</td>
						    <td >主题</td>
						    <td width="120px;" >内容</td>
						    <td>客户类型</td>
						    <td>所属机构</td>
						    <td>业务类型</td>
						    <td>订阅状态</td>
						    <td>推送类型</td>
						    <td>推送状态</td>
						    <td>创建时间</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.subject}</td>
								<td align="left" style="width:120px;word-wrap: break-word; word-break: normal;">${fn:substring(element.content,0, 50)}...</td>
								<td><f:type className="CustType" value="${element.custType}"/> </td>
								<td><f:type className="BranchType" value="${element.branchNo}"/> </td>
								<td><f:type className="BusiType" value="${element.busiType}"/> </td>
								<td><c:if test="${element.subsState eq 'Y'}">已订阅</c:if> </td>
								<td><f:type className="PushType" value="${element.pushType}"/> </td>
								<td>
									<c:if test="${element.state eq 'Y'}">已推送</c:if>
									<c:if test="${element.state eq 'N'}">未推送</c:if>
								</td>
							    <td><bean:write name="element" property="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
