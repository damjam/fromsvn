<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	$().ready(function(){
		 		$('#transferType').change(function(){
		 			var transferType = $('#transferType').val();
		 			if(transferType == '01'){
		 				$('#tr1').show();
		 				$('#tr2').show();
		 			}else if(transferType == '02'){//离职
		 				$('#tr1').hide();
		 				$('#tr2').hide();
		 			}
		 		});
		 	});
			
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<form action="${uri}?action=doTransfer" id="dataForm" method="post" class="validate">
		<s:hidden name="id"/>
		<input type="hidden" name="empTransfer.empId" value="${id}">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <tr>
					  		<td class="formlabel nes">异动类型</td>
					  		<td>
					  			<s:select name="empTransfer.transferType" id="transferType" list="#{'岗位/部门调动':'01','离职':'02'}" listKey="value" listValue="key" style="166px;"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
					  		</td>
					  </tr>
					  <tr id="tr1">
						    <td class="formlabel nes">部门</td>
						    <td>
						    	<s:select name="empTransfer.transBranchNo" id="branchNo" list="#request.branches" listKey="key" listValue="value" style="166px;"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr id="tr2">
						    <td class="formlabel nes">职位</td>
						    <td>
						    	<s:select name="empTransfer.transPosition" id="position" list="#request.positionTypes" listKey="key" listValue="value" style="166px;"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">异动日期</td>
						    <td>
						    	<s:textfield name="empTransfer.transferDate" id="transferDate" maxlength="8" class="{required:true}" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">原因</td>
						    <td>
						    	<s:textfield name="empTransfer.reason" id="reason" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
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
