<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
<head>
      	<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:css href="/css/jquery-ui-1.7.2.custom.css" />
		<f:css href="/css/page.css" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		
		<f:js src="/js/plugin/jquery-ui.js" />
		<style type="text/css">
		
		<style type="text/css">
		<!--
			html { overflow-y: auto; }
		-->
		</style>
		<script type="text/javascript">
			$(function() {
				$('#tabs').tabs();
				/*
				//初始化
				ifrm = document.getElementById('frame0');
				if(!ifrm.src){
		    		ifrm.src=CONTEXT_PATH+"/timer.do?action=query";
		    	}
		    	
				$('#tabs').bind('tabsselect', function(event, ui) {   
				    if (ui.index==0){	
				    	ifrm = document.getElementById('frame0');
						ifrm.src=CONTEXT_PATH+"/timer.do?action=query";
	                }   
				    if (ui.index==1){	
				    	ifrm = document.getElementById('frame1');
						ifrm.src=CONTEXT_PATH+"/timerDo.do?action=list";
	                }  
				});  */
			});
			
			function adjustHeight(height, num){
				$('#frame'+num).height(height);
			}
		</script>
	</head>
	<body>

	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<br />
		<div id="tabs" style="width: 98%;">
			<ul>
				<li>
					<a id="t0" href="#tabs-0">定时计划管理</a>
				</li>
				<li>
					<a id="t1" href="#tabs-1">定时执行查询</a>
				</li>
			</ul>
			<div id="tabs-0" >
				<iframe id="frame0" style="border:0" width="100%"  height="100%" src="${CONTEXT_PATH}/timer.do?action=query"></iframe>
			</div>
			<div id="tabs-1">
				<iframe id="frame1" style="border:0" width="100%"  height="100%" src="${CONTEXT_PATH}/timerDo.do?action=list"></iframe>
			</div>
		</div>
		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
</body>
</html>