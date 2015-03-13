<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html>
	<head>
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<base target="_self"/>
	</head>
	<body>
			<div class="tablebox">
			 <table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<thead>
					<tr>
						<th>操作时间</th>
						<th>操作类型</th>
					</tr>
				<thead>
				<logic:iterate name="list" id="element">
					<tr align="center">
						<td><f:formatDate sourcePattern="yyyyMMddhh:mm:ss" value="${element.u_time.value}" /></td>
						<td>${element.u_oper.value}</td>
					</tr>
				</logic:iterate>
			</table>
			</div>
			<p align="center">
				<input type="button" value="关闭" class="inp_L3" onclick="closeDetail('detail_box')" />
			</p>
	</body>
</html>
