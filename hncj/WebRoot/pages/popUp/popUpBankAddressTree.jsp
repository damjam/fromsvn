<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
	 	
	</head>
 
	<body>
	 	<a onclick="javascript:alert(this.href);alert(2);" href="111">1111</a>
			${msg}
			
			<!--隐藏选择值  -->
			<input type='text' id='hidSelectVal' name='hidSelectVal' />  
			<!-- 根结点的隐藏 -->
			<input type='text' id='hidSelect' name='hidSelect' value='${bankAddress.hidSelect}'/>
	</body>
</html>