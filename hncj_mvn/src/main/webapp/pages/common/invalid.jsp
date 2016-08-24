<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<title>操作失败</title>
		<%@ include file="/pages/common/sys.jsp"%>
	</head>
	
	<body>
		<div style="text-align: center; margin: 20px">
			<img alt="" src="${CONTEXT_PATH }/images/error.gif">
		</div>
		
		<div style="text-align: center">没有该项权限</div>
	</body>
</html>