<!DOCTYPE html>
<html lang="zh-cn">  
<%@ include file="/pages/common/taglibs.jsp" %>

<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>

<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>

		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:css href="/css/page.css" />
		<script type="text/javascript"><!--//--><![CDATA[//><!--
		
		$(function(){
			$('#ifmodify').val($('#hidIfmodify').val());
			
			$('#btnReturn').click(function(){
				window.location.href=CONTEXT_PATH+"/sysParmManage.do?action=query";
					 
			});
				
			$('#btnClear').click(function(){
				$(':text').val('');
				$('select').val('');
				 
			});
		});
		
		//--><!]]></script>
		
	</head>
<body>

	
	<f:msg/>
	<form id="query" action="sysParmManage.do?action=save" method="post" class="validate">
		<s:token/>
		<!-- 用户资料修改区 -->
		<div class="userbox">
			<div class="widget">
			<table class="form_grid">
				<caption class="widget-head">${ACT.name}</caption>	
				  <tr>
				    <td class="formlabel nes">参数编码</td>
				    <td>
				    	<s:textfield class="{required:true,maxlength:6,stringNum:true}"  name="code"  maxlength="6"/>
				    	<span class="field_tipinfo">1-20位字符，包括字母和数字</span>
				    </td>
				    </tr>
				   <tr>
				    <td class="formlabel nes">参数名称</td>
				    <td>
				    	<s:textfield class="{required:true}" name="parname"  maxlength="50"/>
				    	<span class="field_tipinfo">不能为空</span>
				    </td>
				  </tr>
				  <tr>
				    <td class="formlabel nes">参数值</td>
				    <td>
				   		 <s:textfield class="{required:true}"  name="parvalue" maxlength="25"/>
			    		 <span class="field_tipinfo">不能为空</span>
				    </td>
				  </tr>
				  <tr>
				    <td class="formlabel">备注</td>
				    <td>
				   		 <s:textfield name="remark" maxlength="50"/>
				    </td>
				    </tr>
				  <tr>
				  	<td></td>
				    <td colspan="3">
					    <input type="submit"  id="input_btn2"   value="确定"  />
					    <input type="button"  value="返回" id="btnReturn" />
				    </td>
				  </tr>
			  </table>
			</div>
		</div>
	</form>
</body>
</html>
