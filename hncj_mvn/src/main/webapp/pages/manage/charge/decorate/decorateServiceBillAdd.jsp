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
			function checkNum(){
				var prenum = $('#prenum').val();
				var curnum = $('#curnum').val();
				if(prenum != '' && curnum != ''){
					if(curnum < prenum){
						alert('本期读数不能小于上期读数');
						return;
					}
					$('#num').val(curnum-prenum);
				}
			}
			function getHouseInfo(){
				if($('#houseSn').val() == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:'${uri}?action=getHouseInfo',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 if(data.error){
				    		 alert(data.error);
				    		 $('#houseSn').val('');
				    		 $('#houseSn').focus();
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 var area = data.area;
				    	 var cleanPrice = data.cleanPrice;
				    	 var cleanAmount = data.cleanAmount;
				    	 var floor = data.floor;
				    	 var liftFee = data.liftFee;
				    	 var amount = data.amount;
				    	 var supPay = data.supPay;
				    	 var csBillId = data.csBillId;
				    	 $('#ownerName').val(ownerName);
				    	 $('#cleanPrice').val(cleanPrice);
				    	 $('#area').val(area);
				    	 $('#cleanAmount').val(cleanAmount);
				    	 $('#floor').val(floor);
				    	 $('#liftFee').val(liftFee);
				    	 $('#amount').val(amount);
				    	 $('#paidAmount').val(amount);
				    	 if(supPay != ''){
				    		 if(window.confirm("业主上次按80%收取的物业费未到期，是否补缴已减免的20%?")){
				    			 $('#tr1').show();
				    			 $('#supFee').val(supPay);
				    			 $('#csBillId').val(csBillId);
				    		 }else{
				    			 $('#tr1').hide();
				    			 $('#supFee').val('');
				    		 }
				    	 }else{
				    		  $('#tr1').hide();
			    			  $('#supFee').val('');
			    			  $('#remark').val('');
				    	 }
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
		<s:hidden name="csBillId" id="csBillId"/>
		<div class="userbox">
			<div class="widget">
				<div class="widget-head">
                    <div class="pull-left">${ACT.name}</div>
                </div>
				<table class="form_grid">
				   <tr class="houseSn">
					    <td class="formlabel nes">房屋编号</td>
					    <td>
					    	<s:textfield name="houseSn" id="houseSn" maxlength="10" onblur="getHouseInfo()" class="{required:true}"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">业主姓名</td>
					    <td>
					    	<s:textfield name="ownerName" id="ownerName" class="{required:true}" maxlength="10" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">房屋面积</td>
					    <td>
					    	<s:textfield name="area" id="area" class="{required:true}" maxlength="6" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">楼层</td>
					    <td>
					    	<s:textfield name="floor" id="floor" class="{required:true}" maxlength="6" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">垃圾清运费单价</td>
					    <td>
					    <s:textfield name="cleanPrice"  id="cleanPrice" class="{required:true}" maxlength="8" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">垃圾清运费</td>
					    <td>
					    <s:textfield name="cleanAmount"  id="cleanAmount" class="{required:true}" maxlength="8" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">电梯上料使用费</td>
					    <td>
					    <s:textfield name="liftFee"  id="liftFee" class="{required:true}" maxlength="8" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr id="tr1" style="display: none;">
					    <td class="formlabel nes">补缴物业费</td>
					    <td>
					    <s:textfield name="supFee"  id="supFee" class="{num:true}" maxlength="10" readonly="true"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">合计金额</td>
					    <td>
					    <s:textfield name="amount"  id="amount" class="{required:true,num:true}" maxlength="8"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
				   		<td class="formlabel nes">实收金额</td>
				   		<td>
				   			 <s:textfield name="paidAmount"  id="paidAmount" class="{required:true,num:true}" maxlength="8"/>
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
