<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<link rel="stylesheet" href="css/page.css" type="text/css" media="screen, projection" />
	</head>

	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		
		<!-- 登录成功提示区 -->
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="height:30px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table style="width: 100%">
						<tr>
							<td width="52" align="center" valign="top">
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size:14px; font-weight:bold;">
									系统登录提示
								</p>
								<p style=" text-indent:2em; line-height:20px;">
									尊敬的用户，您已经成功登录${comInfo.sp}，请通过左侧导航菜单进行相应功能操作！
									如果您在使用过程中有疑问，请拨打
									<span class="redfont">24小时客服热线：<strong>${comInfo.tel}</strong>
									</span>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!--版权区域-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
