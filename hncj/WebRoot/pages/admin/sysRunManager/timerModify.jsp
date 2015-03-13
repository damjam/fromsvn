<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">  
<head>
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
		<!--
			html { overflow-y: scroll; }
		-->
		</style>
		
		<script type="text/javascript">
			$(function(){
				$('#btnReturn').click(function(){
					window.location.href=CONTEXT_PATH+"/timer.do?action=query";
				});
					
				$('#btnClear').click(function(){
					$(':text').val('');
				});
				
				$('#btnTimerCommand').click(function(){
					var retval=openContextDialog("/timerCommand.do?action=queryPopUpTimerCommand");
						if(retval){
							var ss=retval.split("$")
							$('#timerClassName').val(ss[0]);
							$('#classNameCh').val(ss[1]);
					}
					
				});
			});
		</script>
</head>

<body>
	<!-- 提示对话框 -->
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<form id="timer" action="${CONTEXT_PATH}/timer.do?action=saveUpdate" method="post" class="validate" >
			<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
						<div class="contentb">
						
						<table  class="form_grid"  border="0" cellspacing="5" cellpadding="0">
						  <tr>
						    <td width="100" height="30" align="right">
						    	<span style="font-size:14px; font-weight:bold; padding-bottom:10px;">修改定时计划</span>
						    </td>
						    <td height="30" colspan="3"><br /><span style="color:#CCC"></span><br /></td>
						  </tr>
						  <tr>
						    <td align="right"nowrap="nowrap" class="formlabel nes">程序类名</td>
						    <td>
						    	<input id="timerClassName" class="{required:true}" name="timerClassName"  type="text" value="${timer.timerClassName}" maxlength="200" readonly="readonly"/>
						    	<img align="left" src="<%=request.getContextPath()+"/images/search.jpeg" %>" alt="搜索" id="btnTimerCommand"/>
						    	<span class="field_tipinfo">1-200位字符</span>
						    	<span class="error_tipinfo">请输入</span>
						    	<input type="hidden" name="id" value="${timer.id}"/>
						    </td>
						    <td height="30" align="right" nowrap="nowrap" class="formlabel nes">程序中文名称</td>
						    <td height="30">
						    	<input id="classNameCh" class="{required:true}" name="classNameCh" type="text" value="${timer.classNameCh}" maxlength="600" readonly="readonly"/>
						    	<span class="field_tipinfo"></span>
						    	<span class="error_tipinfo">请输入</span>
						    </td>
						  </tr>
						  <tr>
						    <td align="right" nowrap="nowrap">参数</td>
						    <td>
						   		 <input  name="para2" id="para2" type="text" value="${timer.para2}" maxlength="200"/> 
						   </td>
						    <td height="30" align="right" nowrap="nowrap" class="formlabel nes">触发时间</td>
						    <td height="30" nowrap="nowrap">
						    	<input  id="triggertime" name="triggertime" type="text" value="${timer.triggertime}"" onclick="WdatePicker({dateFmt:'HHmmss'})"/>
						    	<span style="color: red;">格式 hhmmss</span>
						   	</td >
						  </tr>
						   <tr>
						    <td height="30" align="right" nowrap="nowrap" nowrap="nowrap">备注</td>
						    <td width="400" height="30" nowrap="nowrap" colspan="3">
						   		 <input  style="width:400px" id="remark" name="remark" type="text" value="${timer.remark}" />
						   		 <input type="hidden"  value="00" name="state"/>
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

		<!--版权区域-->
		<div>
	 		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	 	</div>
</body>
</html>
