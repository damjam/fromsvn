<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>

		<link rel="stylesheet" href="css/page.css" type="text/css" media="screen, projection" />
	</head>

	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		
		<!-- 登陆成功提示区 -->
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="height:30px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
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
									如果您在使用过程中有疑问，请联系
									<span class="redfont">技术支持电话：<strong>18520827190</strong>
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
