<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
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
					<div class="widget-head">
	                    <div class="pull-left">${ACT.name}</div>
	                </div>
					<table class="form_grid">
					   <tr>
						    <td class="formlabel nes">车位编号</td>
						    <td>
						    	<s:textfield name="sn" id="sn" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车位产权人</td>
						    <td>
						    	<s:textfield name="ownerName" id="ownerName" maxlength="10"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">产权人电话</td>
						    <td>
						    	<s:textfield name="ownerCel" id="ownerCel" maxlength="10"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车位使用人</td>
						    <td>
						    	<s:textfield name="endUser" id="endUser" maxlength="10"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">使用人电话</td>
						    <td>
						    	<s:textfield name="endUserCel" id="endUserCel" maxlength="10"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车位状态</td>
						    <td>
						    	<s:select name="state" id="state" list="#request.parkingStates" listKey="value" listValue="name"></s:select>
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
