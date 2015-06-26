<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<title></title>
		<link rel="stylesheet" href="css/page.css" type="text/css"
			media="screen, projection" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/common.js" />
		<script type="text/javascript">
<!--
	//-->         //><!--//--><!   
	function forward() {
		var url = '${url}';
		if (url != '') {
			gotoUrl(url);
		} else {
			toHomePage();
		}
		//window.history.back();	
	}
</script>
	</head>

	<body class="fullwidth">
		<div class="location">
			您当前所在位置：
			<span class="redlink"><a href="javascript: return;">首页</a>
			</span> > 系统提示 > 操作失败
		</div>

		<!-- 操作结果提示区 -->
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table width="100%" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width=""></td>
							<td width="120" rowspan="3" align="center" valign="middle">
								<img src="images/error.gif" width="158" height="141" />
							</td>
							<td width="180" height="30">
								<span
									style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">操作失败信息</span>
							</td>
							<td width=""></td>
						</tr>
						<tr>
							<td colspan="2"></td>
							<td height="60" colspan="2" valign="middle">
								${msg}
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="30" colspan="4">
								<input type="submit" value=" 返 回 " class="inp_L3"
									onclick="forward();" onmouseover="this.className='inp_L4'"
									onmouseout="this.className='inp_L3'" id="input_btn3"
									name="escape" />
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
		</div>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
