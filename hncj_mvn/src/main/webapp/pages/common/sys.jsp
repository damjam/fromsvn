<%
	String _contextPath = request.getContextPath();
	request.setAttribute("CONTEXT_PATH", _contextPath);
%>
<script type="text/javascript">
	CONTEXT_PATH = '<%=_contextPath%>';
	var loginUrl = CONTEXT_PATH+'/${tag}';
	function toHomePage(){
		window.location.href = CONTEXT_PATH+'/pageHome.jsp';
	}
	function toLoginPage(){
		//alert(CONTEXT_PATH+'/'+tag);
		window.top.location.href = loginUrl;
	}
	var defStartDate = '${defStartDate}';
	var defEndDate = '${defEndDate}';
</script>