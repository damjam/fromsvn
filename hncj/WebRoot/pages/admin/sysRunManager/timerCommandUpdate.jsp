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

	
	<form id="query" action="${CONTEXT_PATH}/timerCommand.do?action=updateSave" method="post" class="validate">
		<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
						<div class="contentb">
							<table class="form_grid">
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
