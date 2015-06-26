<!-- 
  @ temp page.
  @ author aps-mhc
  @ since 2006-12-25
//-->
<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<% response.setHeader("Cache-Control", "no-cache"); %>

<html>
	<head>
		<title>操作失败</title>
	</head>
	
	<body>
		<div style="text-align: center; margin: 20px">
			<html:img page="/images/error.gif" />
		</div>
		
		<div style="text-align: center">没有该项权限</div>
	</body>
</html>