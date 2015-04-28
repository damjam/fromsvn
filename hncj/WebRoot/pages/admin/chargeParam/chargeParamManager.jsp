<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="tab" uri="http://ditchnet.org/jsp-tabs-taglib" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      	<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<link rel="stylesheet" type="text/css"
			href="css/jquery-ui-1.7.2.custom.css" />
		<f:css href="/css/jquery-ui-1.7.2.custom.css" />
		<f:css href="/css/page.css" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		
		<f:js src="/js/plugin/ui.core.js" />
		<f:js src="/js/plugin/ui.tabs.js" />
		
		<script type="text/javascript">
			$(function() {
				$('#tabs').tabs();
				
				//��ʼ��
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
				});  
		});
			
			function adjustHeight(height, num){
				$('#frame'+num).height(height);
				$('#tabs'+num).height(height+30);
			}
		</script>
	</head>
	<body>

	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<br />
		<div id="tabs" style="width: 98%;">
			<ul>
				<li>
					<a id="t0" href="#tabs-0">�Ʒ������</a>
				</li>
				<li>
					<a id="t1" href="#tabs-1">�ƷѲ�������</a>
				</li>
			</ul>
			<div id="tabs-0" >
				<iframe id="frame0" scrolling="auto" frameborder="0" width="100%"  height="100%" onload="this.height=frame0.document.body.scrollHeight"></iframe>
			</div>
			<div id="tabs-1">
				<iframe id="frame1" scrolling="auto" frameborder="0" width="100%"  height="100%" onload="this.height=frame1.document.body.scrollHeight"></iframe>
			</div>
		</div>
		<!--��Ȩ����-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
</body>
</html>