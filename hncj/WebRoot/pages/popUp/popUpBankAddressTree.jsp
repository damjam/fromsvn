<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	 	<title>银行地区码选择</title>
	 	<f:js src="/js/dtree.js"/>
		<base target="_self"/>
	 	
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