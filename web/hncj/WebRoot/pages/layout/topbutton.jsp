<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<s:if test="menus != null">
	<s:iterator value="menus">
		<a href="${entry}" onfocus="this.blur()" target="main_area">${name}</a>
	</s:iterator>
</s:if>