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
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">	
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
		<!-- 操作结果提示区 -->
		<div class="userbox">
			<div class="widget" style="height: 200px;">
				<div class="widget-head">
                  <div class="pull-left">操作提示</div>
                  <div class="widget-icons pull-right">
                   
                  </div>  
                  <div class="clearfix"></div>
                </div>
					<table style="width: 100%;padding-top: 30px;">
						<tr align="center">
							<td width="30%"></td>
							<td width="10%" rowspan="3" align="left" valign="middle">
								<i class="fa fa-times-circle" style="color: #D52B2B; font-size: 96px; line-height: 96px;"></i>
							</td>
							<td rowspan="3" align="left">
								<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">
									<c:if test="${msg == null || msg eq ''}">
										系统错误，请与管理员联系
									</c:if>
									<c:if test="${msg != null && msg ne  ''}">
										${msg}
									</c:if>
								</span>
							</td>
						</tr>
						
					</table>
				</div>
		</div>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
