<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>

	<p style="margin-top:10px; margin-left: 20px;">
		<a href="javascript:openAll(this)" style="color: red">展开</a>
	</p>
	<div id="systree" style="margin-top:10px;">
	</div>
	<script type="text/javascript">
		d = new dTree('d','dtree/images/system/menu/');
		d.config.folderLinks = false;
		d.config.useCookies = false;
		d.config.check = true;
		<c:forEach items="${list}" var="element">
			d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
		</c:forEach>
		document.getElementById('systree').innerHTML = d;
	</script>
