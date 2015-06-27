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
				     url:CONTEXT_PATH + '/commonServiceBill.do?action=getHouseInfo',
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
				    	 var servicePrice = data.servicePrice;
				    	 var lightPrice = data.lightPrice;
				    	 var checkinState = data.checkinState;
				    	 $('#ownerName').val(ownerName);
				    	 $('#servicePrice').val(servicePrice);
				    	 $('#area').val(area);
				    	 $('#lightPrice').val(lightPrice);
				    	 toggle(checkinState);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			function toggle(checkinState){
				//未入住
				if(checkinState == '00' || checkinState == ''){
					$('#tr1').show();
					$('#checkinState').attr('disabled', false);
					
				}else{
					//已入住
					$('#tr1').hide();
					$('#checkinState').attr('disabled', true);
					if(totalAmt > 0){
						$('#totalAmount').val(totalAmt);
					}
				}
			}
			var totalAmt = 0;
			var lightPri = 0;
			var lightAmt = 0;
			var serviceAmt = 0;
			function getAcctInfo(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				var beginDate = $('#beginDate').val();
				var monthNum = $('#monthNum').val();
				if(beginDate == '' && monthNum == ''){
					return;
				}
				if(parseInt(monthNum) < 1){
					alert('缴费月数最少一个月');
					return;
				}
				var params = $('#dataForm input').serialize();
				var checkinState = $('#checkinState').val();
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/commonServiceBill.do?action=getAcctInfo&checkinState='+checkinState,
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 var endDate = data.endDate;
				    	 var serviceAmount = data.serviceAmount;
				    	 var lightAmount = data.lightAmount;
				    	 var totalAmount = data.totalAmount;
				    	 $('#endDate').val(endDate);
				    	 $('#serviceAmount').val(serviceAmount);
				    	 totalAmt = parseFloat(totalAmount).toFixed(2);
				    	 lightPri = $('#lightPrice').val();
				    	 lightAmt = lightAmount;
				    	 serviceAmt = serviceAmount;
				    	 if(checkinState == '0'){
				    		 serviceAmount = serviceAmount*0.8;
					    	 $('#lightAmount').val(0);
					    	 $('#totalAmount').val(parseFloat(serviceAmount).toFixed(2));
				    	 }else{
					    	 $('#lightAmount').val(lightAmount);
					    	 $('#totalAmount').val(totalAmount);
				    	 }
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			
			function check(){
				var checkinState = $('#checkinState').val();
				
				//入住
				if(checkinState == '1'){
					$('#tr2').show();
					if(totalAmt > 0){
						$('#totalAmount').val(totalAmt);
						$('#lightPrice').val(lightPri);
						$('#lightAmount').val(lightAmt);
						$('#serviceAmount').val(serviceAmt);
					}
					$('#remark').val('');
				}else{
					//未入住
					$('#tr2').hide();
					$('#lightPrice').val(0);
					$('#lightAmount').val(0);
					if(totalAmt > 0){
						var adAmt = serviceAmt*0.8;
						adAmt = adAmt.toFixed(2);
						$('#totalAmount').val(adAmt);
						$('#remark').val('业主未入住，物业费收80%');
					}
					$('#suPayState').val('N');
				}
			}
			function checkSuPay(){
				var suPayState = $('#suPayState').val();
				if(suPayState == 'Y'){
					var suServiceAmt = serviceAmt*0.2;
					suServiceAmt = suServiceAmt.toFixed(2);
					$('#serviceAmount').val(suServiceAmt);
					$('#totalAmount').val((parseFloat(suServiceAmt)+parseFloat(lightAmt)).toFixed(2));
					$('#remark').val('补缴20%物业费及公共照明费');
				}else{
					$('#serviceAmount').val(serviceAmt);
					$('#totalAmount').val(totalAmt);
					$('#remark').val('');
				}
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<form action="commonServiceBill.do?action=doAdd" id="dataForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" style="width: 100%;border: 0;" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
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
						    <td class="formlabel nes">物业费单价</td>
						    <td>
						    <s:textfield name="servicePrice"  id="servicePrice" class="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">公共照明费单价</td>
						    <td>
						    <s:textfield name="lightPrice"  id="lightPrice" class="{required:true,num:true}" maxlength="5" />
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">计费起始日期</td>
						    <td>
						    <s:textfield name="startDate"  id="startDate" class="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">缴费月数</td>
						    <td>
						    <s:textfield name="monthNum"  id="monthNum" class="{required:true,digit:true}" maxlength="2" value="12" onblur="getAcctInfo()"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">计费结束日期</td>
						    <td>
						    <s:textfield name="endDate"  id="endDate" class="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">物业费金额</td>
						    <td>
						    <s:textfield name="serviceAmount"  id="serviceAmount" class="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">照明费金额</td>
						    <td>
						    <s:textfield name="lightAmount"  id="lightAmount" class="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr id="tr1">
						    <td class="formlabel nes">一年内是否入住</td>
						    <td>
						    	<s:select name="checkinState" id="checkinState" list="#request.yesNos" onchange="check()" listKey="value" listValue="name"></s:select>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr id="tr2">
					   		<td class="formlabel nes">是否补缴款(20%)</td>
						    <td>
						    	<s:select name="suPayState" id="suPayState" list="#{'N':'否','Y':'是'}" listKey="key" listValue="value"></s:select>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">合计金额</td>
						    <td>
						    <s:textfield name="totalAmount"  id="totalAmount" class="{required:true,num:true}" maxlength="8"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
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
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/commonServiceBill.do?action=list')"/>
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
