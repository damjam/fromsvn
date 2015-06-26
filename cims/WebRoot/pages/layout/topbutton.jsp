<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<logic:notEmpty name="menus">
	<logic:iterate id="menu" name="menus">
		<html:link page="${menu.entry}" onfocus="this.blur()" target="main_area">${menu.name}</html:link>
	</logic:iterate>
</logic:notEmpty>
