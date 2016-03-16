<!DOCTYPE html>
<html lang="zh-cn">  
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>


<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<head>
        <meta http-equiv="Content-Type" content="text/html; charset=ut-8" />
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
				$('#ifmodify').val($('#hidIfmodify').val());
				
				$('#btnReturn').click(function(){
					window.location.href=CONTEXT_PATH+"/sysParmManage.do?action=query";
				});
					
			});
		</script>
	</head>
<body>

	
	<form id="query" action="sysParmManage.do?action=saveUpdate" method="post" class="validate">
		<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
						<div class="contentb">
							<table class="form_grid">
							  <caption>${ACT.name}修改</caption>	
							  <tr>
							    <td class="formlabel nes">参数编码</td>
							    <td>
							    	<s:textfield class="{required:true,maxlength:6} readonly" name="code"/>
							    	<span class="field_tipinfo">1-6位字符，包括字母和数字</span>
							    </td>
							    </tr>
							   <tr>
							    <td class="formlabel nes">参数名称</td>
							    <td>
							    	<s:textfield class="{required:true}" name="parname" maxlength="100"/>
							    	<span class="field_tipinfo">不能为空</span>
							    </td>
							  </tr>
							  <tr>
							    <td class="formlabel nes">参数值</td>
							    <td>
							   		 <s:textfield class="{required:true}"  name="parvalue" maxlength="100" />
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
