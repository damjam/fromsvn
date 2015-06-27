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
		 	
		 	
			function inputTextNum()
		 	{
		 	   var content=$("#content").val();
		 	   var len=content.length;
// 		 	  for ( var i = 0; i < content.length; i++) {//汉字len要加1
// 					if (content.charCodeAt(i) > 127) {
// 						len++;
// 					}
// 				}
		 	  	$("#txtNumLen").html(len);
		 	}
			
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<form action="houseInfo.do?action=doAdd" id="houseInfoActionForm" method="post" class="validate">
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
						    <td class="formlabel nes">楼号</td>
						    <td>
						    	<s:select name="buildingNo" id="buildingNo" list="#request.buildingNos" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
						    </td>
					   </tr>
					   <!-- 
					   <tr>
						    <td class="formlabel nes">单元</td>
						    <td>
						    	<s:select name="unitNo" id="unitNo" list="#request.unitNos" listKey="key" listValue="value" style="width:166px;"></s:select>
						    </td>
					   </tr>
					     -->
					   <tr>
						    <td class="formlabel nes">房间</td>
						    <td>
						    	<s:textfield name="position" id="position" maxlength="4" class="{required:true,digit:true,minlength:3}"/>
						    	<span class="field_tipinfo">请输入正确的房间号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">面积</td>
						    <td>
						    	<s:textfield name="area" id="area" maxlength="6" class="{required:true,num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">交房日期</td>
						    <td>
						    	<s:textfield name="deliveryDate" id="deliveryDate" maxlength="8" readonly="true" onfocus="WdatePicker();"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/houseInfo.do?action=list')"/>
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
