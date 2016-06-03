<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ include file="/pages/common/sys.jsp"%>
<html lang="zh-cn"> 
	<head>
		<title>操作超时</title>
		<f:css href="/css/page.css"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">	
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
			<div class="widget" style="height: 200px;">
				<div class="widget-head">
                  <div class="pull-left">操作提示</div>
                  <div class="widget-icons pull-right">
                   
                  </div>  
                  <div class="clearfix"></div>
                </div>
                <div style="padding-top: 30px;">
				<div style="float: left; width: 40%; text-align: right;line-height:96px;height: auto;">
					<i class="fa fa-times-circle" style="color: #D52B2B; font-size: 96px;"></i>
				</div>
				<div style="float:left;text-align: left;line-height:96px;height: auto;padding-left: 24px;">
					<p align="left"
						style="font-size: 16px; font-weight: bold;">
						您还没有登录或操作超时，点<a href="" target="_top"
							style="text-decoration: underline;color: red" id="link">这里</a>重新登录.
					</p>
				</div>
				</div>
			</div>
		</div>
	</body>
</html>
