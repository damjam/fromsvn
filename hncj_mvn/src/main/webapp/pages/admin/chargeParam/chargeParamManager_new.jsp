<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
<head>
      	<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<link href="https://cdn.bootcss.com/jqueryui/1.12.0/jquery-ui.min.css" rel="stylesheet">
		<f:css href="/css/page.css" />
		<f:js src="/js/paginater.js" />
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		
		<script src="https://cdn.bootcss.com/jqueryui/1.12.0/jquery-ui.min.js"></script>
		
		<script type="text/javascript">
			$(function() {
				$('#tabs').tabs();
				/*
				//初始化
				ifrm = document.getElementById('frame0');
				if(!ifrm.src){
		    		ifrm.src=CONTEXT_PATH+"/chargeItem.do?action=list";
		    	}
		    	
				$('#tabs').bind('tabsselect', function(event, ui) {   
				    if (ui.index==0){	
				    	ifrm = document.getElementById('frame0');
						ifrm.src=CONTEXT_PATH+"/chargeItem.do?action=list";
	                }   
				    if (ui.index==1){	
				    	ifrm = document.getElementById('frame1');
						ifrm.src=CONTEXT_PATH+"/chargeParam.do?action=list";
	                }  
				});*/  
		});
			
			function adjustHeight(height, num){
				$('#frame'+num).height(height);
				$('#tabs'+num).height(height+30);
			}
		</script>
	</head>
	<body>
	
		<br />
		<div id="tabs" style="width: 98%;margin: auto;">
			<ul>
				<li>
					<a id="t0" href="#tabs-0">计费项管理</a>
				</li>
				<li>
					<a id="t1" href="#tabs-1">计费参数管理</a>
				</li>
			</ul>
			<div id="tabs-0" >
				<iframe id="frame0" style="border: 0" width="100%"  height="100%" src="${CONTEXT_PATH}/chargeItem.do?action=list"></iframe>
			</div>
			<div id="tabs-1">
				<iframe id="frame1" style="border: 0" width="100%"  height="100%" src="${CONTEXT_PATH}/chargeParam.do?action=list"></iframe>
			</div>
		</div>
		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
</body>
</html>