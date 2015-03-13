<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

	<%@ page language="java" contentType="text/html; charset=GBK"%>
	<%response.setHeader("Cache-Control", "no-cache");%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />

		<style type="text/css">
		html {
			overflow-y: scroll;
		}
		
	</style>

		<script type="text/javascript"><!--//--><![CDATA[//><!--
		//--><!]]></script>

	</head>
	<body>

		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="data_table" width="80%" border="0" cellspacing="3"
						cellpadding="0">
						<c:forEach items="${sysLogList}" var="sysLog" >
							<tr>
								<td height="30" align="center" colspan="4">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">查看日志明细</span>
								</td>
							</tr>
							<tr>
								<td align="right"  nowrap="nowrap">
									用户编号:&nbsp;
								</td>
								<td height="30">
									${sysLog.userId}
								</td>
								<td height="30" align="right" nowrap="nowrap">
									用户姓名:&nbsp;
								</td>
								<td height="30">
									${sysLog.userName}
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									所属机构号:&nbsp;
								</td>
								<td height="30">
									${sysLog.branchNo}
								</td>
									
								<td height="30" align="right">
									机构名称:&nbsp;
								</td>
								<td height="30">
									${sysLog.branchName}
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									商户编号:&nbsp;
								</td>
								<td height="30">
									${sysLog.merchantNo}
								</td>
								<td height="30" align="right">
									商户名称:&nbsp;
								</td>
								<td height="30">
									${sysLog.merchantName}
								</td>
							</tr>
							<tr>
								<td align="right">
									模块编号:&nbsp;
								</td>
								<td height="30">
									${sysLog.limitId}
								</td>
								<td height="30" align="right">
									模块名称:&nbsp;
								</td>
								<td height="30">
									${sysLog.limitName}
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									日志类型:&nbsp;
								</td>
								<td height="30">
									${sysLog.logType}
								</td>
								<td height="30" align="right">
									日志种类:&nbsp;
								</td>
								<td height="30">
									${sysLog.logClass}
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									第一次查看者:&nbsp;
								</td>
								<td height="30">
									${sysLog.viewUser}
								</td>
								<td height="30" align="right">
									第一次查看时间:&nbsp;
								</td>
								<td height="30" nowrap="nowrap">
									<fmt:formatDate value="${sysLog.viewDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									状态:&nbsp;
								</td>
								<td height="30">
									${sysLog.state}
								</td>
								<td height="30" align="right">
									创建时间:&nbsp;
								</td>
								<td height="30" >
									<fmt:formatDate value="${sysLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td height="30" align="right">
									建议处理方式:&nbsp;
								</td>
								<td height="30" colspan="3">
									${sysLog.approach}
								</td>
							</tr>
							<tr>
								<td height="30" align="right" >
									日志内容:&nbsp;
								</td>
								<td height="30" colspan="3"  >
									${sysLog.content}
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="button" value="关闭" id="input_btn2" name="ok"
										onclick="javascript:window.close();" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
		</div>

		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
	</body>
