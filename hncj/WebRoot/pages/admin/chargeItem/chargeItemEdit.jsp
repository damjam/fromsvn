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
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
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
			function getOwnerInfo(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/parkingBill.do?action=getOwnerName',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 if(data.error){
				    		 alert(data.error);
				    		 $('#houseSn').val('');
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 var ownerCel = data.mobile;
				    	 $('#ownerName').val(ownerName);
				    	 $('#ownerCel').val(ownerCel);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			$(document).ready(function(){
				var height = document.body.scrollHeight;
				parent.adjustHeight(height, 0);
			});
			$(document).ready(function(){
				changeWay($('#way').val());
			});
			function changeWay(way){
				if(way == '00'){
					$('.unit').show();
					$('.seg').hide();
					$('.step').hide();
				}else if(way == '01'){
					$('.unit').hide();
					$('.seg').show();
					$('.step').hide();
				}else if(way == '02'){
					$('.unit').hide();
					$('.seg').hide();
					$('.step').show();
				}
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doEdit" method="post" class="validate">
		<s:hidden name="id"/>
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">计费项名称</td>
						    <td>
						    	<s:textfield name="itemName" id="itemName" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">计费项目</td>
						    	<td>
						    	<s:select name="item" id="item" list="#request.chargeTypes" listKey="value" listValue="name"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">计费方式</td>
						    <td>
						    	<s:select name="way" id="way" list="#request.chargeWays" listKey="value" listValue="name"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="unit">
						    <td class="formlabel nes">计费单价</td>
						    <td>
						    	<s:textfield name="unitPrice" id="unitPrice" maxlength="10" class="{num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">起步价</td>
						    <td>
						    	<s:textfield name="basePrice" id="basePrice" maxlength="10" styleClass="{num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">起步楼层</td>
						    <td>
						    	<s:textfield name="baseFloor" id="baseFloor" maxlength="10" class="{digit:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">递增价</td>
						    <td>
						    	<s:textfield name="stepPrice" id="stepPrice" maxlength="10" class="{num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">封顶价</td>
						    <td>
						    	<s:textfield name="capPrice" id="capPrice" maxlength="10" class="{num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr class="seg">
						    <td class="formlabel nes">分段价格</td>
						    <td>
						    	<s:textfield name="segRule" id="segRule" maxlength="30"/>
						    	<span class="field_tipinfo">格式如:1-18:10;19-32:20</span>
						    	<span class="error_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" id="remark" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
			</div>
		</div>	
	</form>	
</body>
</html>
