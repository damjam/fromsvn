<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">  
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>


<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<head>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>

		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css" />
		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript"><!--//--><![CDATA[//><!--
		
		$(function(){
			$('#ifmodify').val($('#hidIfmodify').val());
			
			$('#btnReturn').click(function(){
				window.location.href=CONTEXT_PATH+"/timerCommand.do?action=queryTimerCommand";
					 
			});
				
		});
		//--><!]]></script>
		
	</head>
<body>

	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<form id="query" action="${CONTEXT_PATH}/timerCommand.do?action=updateSave" method="post" class="validate">
		<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
						<div class="contentb">
							<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							  <tr>
							    <td width="100" height="30" align="right">
							    	<span style="font-size:14px; font-weight:bold; padding-bottom:10px;">新增定时命令</span>
							    </td>
							    <td height="30" colspan="3"><br /><span style="color:#CCC"></span><br /></td>
							  </tr>
							  <tr><td colspan="4" align="center"><f:msg /></td></tr>
							  <tr>
							    <td height="30" align="right">类名</td>
							    <td height="30">
							    	<input  value="${timerCommand.timerClassName}"  disabled="disabled"/>
							    	<input type="hidden" name="timerClassName" type="text" value="${timerCommand.timerClassName}"  />
							    </td>
							    <td align="right" class="formlabel nes">类中文</td>
							    <td>
							    	<input class="{required:true}" name="classNameCh"  type="text" value="${timerCommand.classNameCh}" maxlength="60"/>
							    	<span class="field_tipinfo"></span>
							    	<span class="error_tipinfo">请输入</span>
							    </td>
							  </tr>
							  <tr>
							    <td height="30" align="right">&nbsp;</td>
							    <td height="30" colspan="3">
								    <input type="submit"  id="input_btn2"   value="确定"  />
								    <input style="margin-left:30px;" type="button" value="清除" id="btnClear"/>
								    <input style="margin-left:30px;" type="button"  value="返回" id="btnReturn" />
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
	</form>
	
</body>
</html>
