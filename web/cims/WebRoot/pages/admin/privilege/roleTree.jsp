<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<logic:present name="list">
	<p style="margin-top:10px; margin-left: 20px;">
		<a href="javascript:openAll(this)" style="color: red">Õ¹¿ª</a>
	</p>
	<div id="systree" style="margin-top:10px;">
	</div>
	<script type="text/javascript">
		d = new dTree('d','dtree/images/system/menu/');
		d.config.folderLinks = false;
		d.config.useCookies = false;
		d.config.check = true;
		<logic:iterate id="element" name="list">
			d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
		</logic:iterate>
		document.getElementById('systree').innerHTML = d;
	</script>
</logic:present>
<html:hidden property="limitGroupId" styleId="limitGroupId" value="${limitGroupId}"/>
