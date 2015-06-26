<%@ page language="java"  pageEncoding="utf-8"%>
<%
	String backlevel = request.getParameter("gobacklevel");
	int nBacklevel;
	if (backlevel==null)
		nBacklevel=0;
	else
		nBacklevel=Integer.valueOf(backlevel);
	
	nBacklevel++;
	request.setAttribute("gobacklevel",nBacklevel);
%>
<script type="text/javascript">
	function goback()
	{
		var backlevel = -1*<%=nBacklevel%>;
		history.go(backlevel);
	}	
</script>
<input name="gobackbutton"  type="button" value="返回" onclick="goback()"/>
<input name="gobacklevel"  type="hidden" value=${gobacklevel} />

