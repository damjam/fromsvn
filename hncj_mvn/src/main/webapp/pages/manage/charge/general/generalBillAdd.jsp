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
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		<script type="text/javascript">
			
		 	function save(){
		 		var tradeType = $('#tradeType').val();
		 		if(tradeType == '98'){
		 			var remark = $('#remark').val();
		 			if(remark == ''){
		 				alert('请在备注中注明收款项目');
			 			$('#remark').focus();
			 			return;
		 			}
		 		}
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	function change(){
				var isInternal = $('#isInternal').val();
				if(isInternal == '1'){
					$("tr[class='houseSn']").show();
				}else{
					$("tr[class='houseSn']").hide();
				}
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
			
			
			function count(){
				var unitPrice = $('#unitPrice').val();
				var num = $('#num').val();
				if(num == '' || unitPrice == ''){
					return;
				}
				if(isNaN(parseFloat(unitPrice))){
					alert('请输入正确的单价');
					$('#unitPrice').val('');
					$('#totalAmt').val('');
					$('#paidAmt').val('');
					$('#unitPrice').focus();
					return;
				}
				if(isNaN(parseInt(num, 10))){
					alert('请输入正确的数量');
					$('#num').val('');
					$('#totalAmt').val('');
					$('#paidAmt').val('');
					$('#num').focus();
					return;
				}
				unitPrice = parseInt(unitPrice, 10);
				num = parseFloat(num);
				var totalAmt = unitPrice*num;
				totalAmt = totalAmt.toFixed(2);
				$('#totalAmt').val(totalAmt);
				$('#paidAmt').val(totalAmt);
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
	<div class="userbox">
		<div class="widget">
			<div class="widget-head">
                 <div class="pull-left">${ACT.name}</div>
             </div>
			<table class="form_grid">
			   <tr>
				    <td class="formlabel nes">收费项目</td>
				    <td>
				    	<s:select name="tradeType" id="tradeType" list="#request.generalTradeTypes" listKey="value" listValue="name"></s:select>
				    	<span class="field_tipinfo">如收费项目为其他，请在备注中说明</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel nes">单价</td>
				    <td>
				    <s:textfield name="unitPrice"  id="unitPrice" class="{required:true,num:true}" maxlength="8" onblur="count()"/>
				    	<span class="field_tipinfo">请输入正确的数字</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel nes">数量</td>
				    <td>
				    <s:textfield name="num"  id="num" class="{required:true,digits:true}" maxlength="8" onblur="count()" value="1"/>
				    	<span class="field_tipinfo">请输入正确的数字</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel nes">金额</td>
				    <td>
				    <s:textfield name="totalAmt"  id="totalAmt" class="{required:true,num:true}" maxlength="12"/>
				    	<span class="field_tipinfo">请输入正确的数字</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel nes">实收</td>
				    <td>
				    <s:textfield name="paidAmt"  id="paidAmt" class="{required:true,num:true}" maxlength="12"/>
				    	<span class="field_tipinfo">请输入正确的数字</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel nes">付款人</td>
				    <td>
				    	<s:textfield name="payerName" id="payerName" class="{required:true}" maxlength="10" />
				    	<span class="field_tipinfo">不能为空</span>
				    </td>
			   </tr>
			   <tr>
				    <td class="formlabel">备注</td>
				    <td>
				    <s:textfield name="remark"  id="remark" maxlength="25"/>
				    	<span class="field_tipinfo"></span>
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
