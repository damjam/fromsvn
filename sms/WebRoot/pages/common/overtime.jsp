<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/common/sys.jsp"%>
<html lang="zh-cn"> 
	<head>
		<title>操作超时</title>
		<f:css href="/css/page.css"/>	
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<script>
			$(function(){
				try{
					window.parent.unblock();
					
				}catch(e){
						
				}
			});
			$().ready(function(){
				var link = $('#link');
				link.attr('href', top.loginUrl);
			});
			
		</script>
	</head>
	<body>
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb" style="height: 150px; padding: 10px;">
				<div style="float: left; width: 30%; text-align: right">
					<img src="${CONTEXT_PATH}/images/error.gif" alt="" />
				</div>
				<div style="width: 68%;text-align: left;vertical-align: center">
					<p align="left"
						style="font-size: 16px; font-weight: bold; margin-top:55px;">
						您还没有登录或操作超时，点<a href="" target="_top"
							style="text-decoration: underline;color: red" id="link">这里</a>重新登录.
					</p>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
	</body>
</html>
