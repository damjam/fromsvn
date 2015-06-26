<!-- 
	@ submit element.
	@ 2006-12-23.
//-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ include file="/pages/common/sys.jsp"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<html>
	<head>
		<title>操作超时</title>
		<link rel="stylesheet" href="${CONTEXT_PATH}/css/page.css"
			type="text/css" media="screen, projection" />
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
							style="text-decoration: underline;color: red" id="link">这里</sapn></a>重新登录.
					</p>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
	</body>
</html>
