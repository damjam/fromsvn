<!DOCTYPE html>
<html lang="zh-cn">


	<%@ page language="java" contentType="text/html; charset=utf-8"%>
	<%response.setHeader("Cache-Control", "no-cache");%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<%@ include file="/pages/common/taglibs.jsp" %>
		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />

	</head>
	<body>

		
		<div class="userbox">
			<div class="widget">
					<table class="data_table" style="width: 100%">
						<caption class="widget-head">${ACT.name}</caption>
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
									<s:date name="viewDate" format="yyyy-MM-dd HH:mm:ss"/>
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
									<s:date name="sysLog.createTime" format="yyyy-MM-dd HH:mm:ss"/>
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
			</div>
		</div>

		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
	</body>
</html>