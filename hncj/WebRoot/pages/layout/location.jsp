<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

<div class="location">您当前所在位置：物业管理系统
	<logic:notEmpty name="PRIVILEGE_PATH">
		<logic:iterate id="menu" name="PRIVILEGE_PATH">&gt;${menu.name}</logic:iterate>
	</logic:notEmpty>
</div>
