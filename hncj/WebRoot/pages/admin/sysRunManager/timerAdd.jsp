<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">  
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
							var ss=retval.split("$");
							$('#timerClassName').val(ss[0]);
							$('#classNameCh').val(ss[1]);
					}
					
				});
			});
		</script>
</head>

<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>	
		<form id="timer" action="${CONTEXT_PATH}/timer.do?action=save" method="post" class="validate" >
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
						    	<span style="font-size:14px; font-weight:bold; padding-bottom:10px;">新增定时计划</span>
						    </td>
						    <td height="30" colspan="3"><br /><span style="color:#CCC"></span><br /></td>
						  </tr>
						  <tr><td colspan="4" align="center"><f:msg /></td></tr>
						  <tr>
						    <td align="right"nowrap="nowrap" class="formlabel nes">程序类名</td>
						    <td>
						    	<input id="timerClassName" class="{required:true}" name="timerClassName"  type="text" value="${timer.timerClassName}" maxlength="200" readonly="readonly"/>
						    	<img align="left" src="<%=request.getContextPath()+"/images/search.jpeg" %>" alt="搜索" id="btnTimerCommand"/>
						    	<span class="field_tipinfo">1-200位字符</span>
						    	<span class="error_tipinfo">请输入</span>
						    </td>
						    <td height="30" align="right" nowrap="nowrap" class="formlabel nes">程序中文名称</td>
						    <td height="30">
						    	<input id="classNameCh" class="{required:true}" name="classNameCh" type="text" value="${timer.classNameCh}" maxlength="600" />
						    	<span class="field_tipinfo"></span>
						    	<span class="error_tipinfo">请输入</span>
						    </td>
						  </tr>
						  <tr>
						    <td align="right" nowrap="nowrap">参数</td>
						    <td>
						   		 <input  name="para2" id="para2" type="text" value="${timerDo.para2}" maxlength="200"/> 
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

</body>
</html>
