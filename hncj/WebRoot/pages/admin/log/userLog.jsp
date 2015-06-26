<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
<head>
	
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css"/>


		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
		
		 function detailUserLog(userLogId){
			 var url = "/userLog.do?action=queryUserLogContent&userLogId="+userLogId;
				gotoUrl(url);
		 }
		</script>
</head>

<body>
	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<!-- 查询功能区 -->
	<html:form styleId="query" action="userLog.do?action=queryUserLog" method="post" >
		<div class="userbox">
		<div>
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
		  <caption>用户日志查询</caption>
		  <tr>
		    <td class="formlabel">用户编号</td>
		    <td><input type="text"  name="userId" id="userId" value ="${param.userId}"/></td>
		    <td class="formlabel">模块编号</td>
		    <td><input name="limitId" type="text" id="limitId" value ="${param.limitId}" /></td>
		    <td class="formlabel">日志类型</td>
		    <td>
				<html:select property="logType">
					<html:option value="">---请选择---</html:option>
					<html:options collection="userLogTypes" labelProperty="name" property="value"/>
				</html:select>
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
		</div>
	</html:form>
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				  <tr align="center">
				    <th class="titlebg" width="10%">用户编号</th>
				    <th class="titlebg" width="10%">用户姓名</th>
				    <th class="titlebg" width="10%">模块编号</th>
				    <th class="titlebg" width="10%">模块名称</th>
				    <th class="titlebg" width="10%">日志类型</th>
				    <th class="titlebg" width="15%">创建时间</th>
				    <th class="titlebg" width="30%">内容</th>
				    <th class="titlebg" width="10%">操作</th> 
				  </tr>
				 </thead>
				 <tbody>
			  	<f:showDataGrid name="list" msg=" " styleClass="data_grid">
				<logic:iterate id="userLog" name="list">
				  <tr align="center">
				    <td>${userLog.userId}</td>
				    <td>${userLog.userName}</td>
				    <td>${userLog.limitId}</td>
				    <td>${userLog.limitName}</td>
				    <td><f:type className="UserLogType" value="${userLog.logType}"/></td>
				    <td>
						<fmt:formatDate value="${userLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>   
					</td>
					 <td align="left" title="${userLog.content}">
					 	${userLog.contentAbbr}
					 </td>
					 <td>
						 <span class="redlink">
						 	<a href="#" onclick='detailUserLog(${userLog.id})'>明细</a>
						 </span>
					 </td>
				  </tr>
				 </logic:iterate>
				 </f:showDataGrid>
				</tbody>
			</table>
			<f:paginate />	
		</div>

	<!--版权区域-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>