<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<div class="location">您当前所在位置：首页
	<c:forEach var="element" items="${PRIVILEGE_PATH}">
		&gt;${element.name}
	</c:forEach>
</div>
