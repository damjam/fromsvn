<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<title>${comInfo.sp}</title>
		<link rel="stylesheet" href="css/page.css" type="text/css" />
		<script type="text/javascript">
		function rlRelogin() {
			window.location.href=CONTEXT_PATH;
		}
		</script>
	</head>

	<body class="fullwidth">
	    <div class="location">您当前所在位置： <span class="redlink"><a href="javascript: return;">首页</a></span> &gt; 系统提示 &gt; 操作成功</div>

		<!-- 操作结果提示区 -->
		<div class="userbox">
		<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
		<div class="contentb">		
		<table style="width: 100%;">
			<tr>
				<td width="200" rowspan="3" align="center" valign="middle"><img src="images/succes.jpg" width="158" height="141" /></td>
				<td width="100" height="30"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;">操作成功</span></td>
				<td height="30" colspan="3">&nbsp;</td>
			</tr>
			<tr>
				<td height="60" colspan="4" align="left" valign="middle">${msg}</td>
			</tr>
			<tr>
				<td height="30" colspan="4">
				<input type="submit"  onclick="rlRelogin()" value=" 返回登录页面 " class="inp_L3" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn2"  name="ok"/>
				</td>
			</tr>
		</table>   
		</div>
		<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>	
		</div>

		<!--版权区域-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
