<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
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
		
		<script type="text/javascript"><!--//--><![CDATA[//><!--
		
		$(function(){
			$('#ifmodify').val($('#hidIfmodify').val());
			
			$('#btnReturn').click(function(){
				window.location.href=CONTEXT_PATH+"/timerCommand.do?action=queryTimerCommand";
					 
			});
				
			$('#btnClear').click(function(){
				$(':text').val('');
				$('select').val('');
				 
			});
		});
		//--><!]]></script>
		
	</head>
<body>

	
	<form id="query" action="${CONTEXT_PATH}/timerCommand.do?action=save" method="post" class="validate">
		<!-- 用户资料修改区 -->
			<div class="userbox">
				<div class="widget">
				<table class="form_grid">
				  <caption class="widget-head">${ACT.name}</caption>
				  <tr>
				    <td width="100" height="30" align="right">
				    	<span style="font-size:14px; font-weight:bold; padding-bottom:10px;">新增定时命令</span>
				    </td>
				    <td height="30" colspan="3"><br /><span style="color:#CCC"></span><br /></td>
				  </tr>
				  <tr><td colspan="4" align="center"><f:msg /></td></tr>
				  <tr>
				    <td height="30" align="right" class="formlabel nes">类名</td>
				    <td height="30">
				    	<input class="{required:true}"  name="timerClassName" type="text" value="${timerCommand.timerClassName}"  />
				    	<span class="field_tipinfo">1-200位字符，字母</span>
				    	<span class="error_tipinfo">格式错误</span>
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
		</div>
	</form>
	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
