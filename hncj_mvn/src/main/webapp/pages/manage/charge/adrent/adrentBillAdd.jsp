<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
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
		<f:js src="/layer/layer.js"/>	
		<script type="text/javascript">
			layer.config({
			    extend: 'extend/layer.ext.js'
			}); 
		 	function save(){
		 		var tradeType = $('#tradeType').val();
		 		if(tradeType == '98'){
		 			var remark = $('#remark').val();
		 			alert('请在备注中注明收款项目');
		 			$('#remark').focus();
		 			return;
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
			
			function getOwnerName(){
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
				    	 $('#payerName').val(ownerName);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
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
			function popup(bindCode, bindName, params){
				var toUrl=CONTEXT_PATH+'/merchantInfo.do?action=queryPopUpMerchantInfo&bindCode='+bindCode+'&bindName='+bindName;
				if(params&&params!=null){
					toUrl=toUrl+"&"+params;
				}
				layer.open({
					title:'商家',
				    type: 2,
				    area: ['720px', '530px'],
				    fix: false, //不固定
				    maxmin: true,
				    content: toUrl
				});
			}
		</script>
	</head>
	
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" method="post" class="validate">
		<div class="userbox" style="padding-top: 10px;padding-bottom: 10px;">
			<div class="widget">
					<div class="widget-head">
                  <div class="pull-left" style="line-height: 20px">${ACT.name}</div>
                </div>
					<table class="form_grid">
					   <tr>
						    <td class="formlabel nes">商家名称</td>
						    <td>
						    	<s:textfield name="merchantName"  id="merchantName" class="{required:true}" maxlength="25" readonly="true" onclick="popup('merchantNo','merchantName');"/>
						    	<s:hidden name="merchantNo" id="merchantNo"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">租用位置</td>
						    <td>
						    <s:textfield name="position"  id="position" class="{required:true}" maxlength="20"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">租用开始日期</td>
						    <td>
						    <s:textfield name="beginDate"  id="beginDate" class="{required:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">租用结束日期</td>
						    <td>
						    <s:textfield name="endDate"  id="endDate" class="{required:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
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
