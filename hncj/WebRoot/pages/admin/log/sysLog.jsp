<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html>
<head>
	
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css"/>
		<script type="text/javascript">
		</script>
</head>

<body>
	
	<!-- 查询功能区 -->
	<form action="sysLog.do?action=querySysLog" method="post">
		<div class="userbox">
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table class="form_grid">
		  <caption>系统日志查询</caption>
		  <tr>
		    <td class="formlabel">模块编号</td>
		    <td><s:textfield name="limitId" id="limitId"/></td>
		    <td class="formlabel">日志级别</td>
		    <td>
		    	<s:select list="#request.logClassTypes" name="logClass" headerKey="" headerValue="---请选择---" listKey="name" listValue="value"></s:select>
			</td>
		  </tr>
		   <tr>
		    <td></td>
		    <td colspan="5">
	   			 <input type="submit" value="查询"/>&nbsp;&nbsp;
	   			 <input type="button" value="重置" onclick="FormUtils.reset('query')"/>
		    </td>
		  </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
	</form>
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' style="width: 100%">
				<thead>
				  <tr align="center">
				    <th class="titlebg">模块编号</th>
				    <th class="titlebg">模块名称</th>
				    <th class="titlebg">日志级别</th>
				    <th class="titlebg">创建时间</th>
				    <th class="titlebg">内容</th>
				    <!-- 
				    <th class="titlebg">操作</th> -->
				  </tr>
				 </thead>
				 <tbody>
			  	<f:showDataGrid name="list" msg=" " styleClass="data_grid">
				<c:forEach var="element" items="${list}">
				  <tr align="center">
				    <td>${element.limitId}</td>
				    <td>${element.limitName}</td>
				    <td><f:type className="LogClassType" value="${element.logClass}"/></td>
				    <td>
						<fmt:formatDate value="${element.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					 <td title="${element.content}">${element.contentAbbr}</td>
				  </tr>
				 </c:forEach>
				 </f:showDataGrid>
				</tbody>
			</table>
			<f:paginate />	
		</div>

	<!--版权区域-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>