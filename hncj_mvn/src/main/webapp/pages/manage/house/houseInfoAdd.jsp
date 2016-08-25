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
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		
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

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" method="post" class="validate">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					 
					  <tr>
						    <td class="formlabel nes">楼号</td>
						    <td>
						    	<s:select name="buildingNo" id="buildingNo" list="#request.buildingNos" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">单元</td>
						    <td>
						    	<s:select name="unitNo" id="unitNo" list="#request.unitNos" listKey="key" listValue="value" style="width:166px;"></s:select>
						    </td>
					   </tr>
					    
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
					   <tr>
					   		<td></td>
					   		<td>
					   			<input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 			<input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
					   		</td>
					   </tr>
				  </table>
			</div>
		</div>	
	</form>	
</body>
</html>
