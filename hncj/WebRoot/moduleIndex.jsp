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
		
		<!-- ��¼�ɹ���ʾ�� -->
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
									ϵͳ��¼��ʾ
								</p>
								<p style=" text-indent:2em; line-height:20px;">
									�𾴵��û������Ѿ��ɹ���¼${comInfo.sp}����ͨ�������˵�������Ӧ���ܲ�����
									�������ʹ�ù����������ʣ�����ϵ
									<span class="redfont">����֧�ֵ绰��<strong>${comInfo.tel}</strong>
									</span>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<!--��Ȩ����-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
