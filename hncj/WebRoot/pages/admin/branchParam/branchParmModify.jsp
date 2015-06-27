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
		
		<script type="text/javascript">
		
			$(function(){
				$('#ifmodify').val($('#hidIfmodify').val());
				
				$('#btnReturn').click(function(){
					window.location.href=CONTEXT_PATH+"/branchParmManage.do?action=query";
						 
				});
					
			});
		</script>
	</head>
<body>

	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<form id="query" action="${CONTEXT_PATH}/branchParmManage.do?action=saveUpdate" method="post" class="validate">
		<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
						<div class="contentb">
							<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
							  <caption>${ACT.name}</caption>	
							  <tr>
							    <td class="formlabel nes">参数编码</td>
							    <td>
							    	<input class="{required:true,maxlength:6} readonly" disabled="disabled"  name="code" type="text" value="${branchParm.code}"  />
							    	<span class="field_tipinfo">1-6位字符，包括字母和数字</span>
							    	<input type="hidden" name="code" value="${branchParm.code}">
							    </td>
							    </tr>
							   <tr>
							    <td class="formlabel nes">参数名称</td>
							    <td>
							    	<input class="{required:true}" name="parname"  type="text" value="${branchParm.parname}" maxlength="100"/>
							    	<span class="field_tipinfo">不能为空</span>
							    </td>
							  </tr>
							  <tr>
							    <td class="formlabel nes">参数值</td>
							    <td>
							   		 <input class="{required:true}"  name="parvalue" type="text" value="${branchParm.parvalue}" maxlength="100"/>
						    		 <span class="field_tipinfo">不能为空</span>
							    </td>
							  </tr>
							  <tr>
							    <td class="formlabel">备注</td>
							    <td>
							   		 <input name="remark" type="text"  value="${branchParm.remark}" maxlength="50"/>
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
	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
