<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<div class="location">����ǰ����λ�ã���ҵ����ϵͳ
	<logic:notEmpty name="PRIVILEGE_PATH">
		<logic:iterate id="menu" name="PRIVILEGE_PATH">&gt;${menu.name}</logic:iterate>
	</logic:notEmpty>
</div>
