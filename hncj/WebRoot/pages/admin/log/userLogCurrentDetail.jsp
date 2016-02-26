<!DOCTYPE html>
<html lang="zh-cn">
<%@ include file="/pages/common/taglibs.jsp" %>

	<%@ page language="java" contentType="text/html; charset=utf-8"%>
	<%response.setHeader("Cache-Control", "no-cache");%>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css" />

		<style type="text/css">
			html {
				overflow-y: scroll;
			}
		</style>

		<script type="text/javascript">
		function goBack(){
			 var url = "/userLog.do?action=queryUserLog";
				gotoUrl(url);
		}
		</script>

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
					<table class="data_table">
							<tr>
								<td height="30" align="center" colspan="4">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">查看日志明细</span>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap="nowrap">
									用户号:&nbsp;
								</td>
								<td height="30" nowrap="nowrap">
									${userLog.userId}
								</td>
								<td height="30" align="right" nowrap="nowrap">
									机构编号:&nbsp;
								</td>
								<td height="30" nowrap="nowrap">
									<f:type className="BranchType" value="${userLog.branchNo}" />
								</td>
							</tr>
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									登录IP:&nbsp;
								</td>
								<td height="30">
									${userLog.loginIp}
								</td>
								<td height="30" align="right" nowrap="nowrap">
									日志类型:&nbsp;
								</td>
								<td height="30">
									<f:type className="UserLogType" value="${userLog.logType}" />
								</td>
							</tr>
							<tr>
							</tr>
							<tr>
								<td height="60" align="right" >
									日志内容:&nbsp;
								</td>
								<td height="60" colspan="3">
									<label>
										${userLog.content}
									</label>
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
									<input type="button" value="返回" id="input_btn2" name="ok" onclick='goBack();' />
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

		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
	</body>
</html>
