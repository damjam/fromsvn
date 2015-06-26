<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<logic:notEmpty name="menus">
	<logic:iterate id="menu" name="menus">
		<html:link page="${menu.entry}" onfocus="this.blur()" target="main_area">${menu.name}</html:link>
	</logic:iterate>
</logic:notEmpty>
