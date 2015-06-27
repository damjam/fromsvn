<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
// 		 	  for ( var i = 0; i < content.length; i++) {//����lenҪ��1
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
						alert('���ڶ�������С�����ڶ���');
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
	                     alert("���ӷ�����ʧ��");
	                 }   
				});
			}
			function toggle(checkinState){
				//δ��ס
				if(checkinState == '00' || checkinState == ''){
					$('#tr1').show();
					$('#checkinState').attr('disabled', false);
					
				}else{
					//����ס
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
					alert('�ɷ���������һ����');
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
	                     alert("���ӷ�����ʧ��");
	                 }   
				});
			}
			
			function check(){
				var checkinState = $('#checkinState').val();
				
				//��ס
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
					//δ��ס
					$('#tr2').hide();
					$('#lightPrice').val(0);
					$('#lightAmount').val(0);
					if(totalAmt > 0){
						var adAmt = serviceAmt*0.8;
						adAmt = adAmt.toFixed(2);
						$('#totalAmount').val(adAmt);
						$('#remark').val('ҵ��δ��ס����ҵ����80%');
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
					$('#remark').val('����20%��ҵ�Ѽ�����������');
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
	<html:form action="commonServiceBill.do?action=doAdd" styleId="dataForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					   <tr class="houseSn">
						    <td class="formlabel nes">���ݱ��</td>
						    <td>
						    	<html:text property="houseSn" styleId="houseSn" maxlength="10" onblur="getHouseInfo()" styleClass="{required:true}"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">ҵ������</td>
						    <td>
						    	<html:text property="ownerName" styleId="ownerName" styleClass="{required:true}" maxlength="10" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�������</td>
						    <td>
						    	<html:text property="area" styleId="area" styleClass="{required:true}" maxlength="6" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��ҵ�ѵ���</td>
						    <td>
						    <html:text property="servicePrice"  styleId="servicePrice" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">���������ѵ���</td>
						    <td>
						    <html:text property="lightPrice"  styleId="lightPrice" styleClass="{required:true,num:true}" maxlength="5" />
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">�Ʒ���ʼ����</td>
						    <td>
						    <html:text property="startDate"  styleId="startDate" styleClass="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ɷ�����</td>
						    <td>
						    <html:text property="monthNum"  styleId="monthNum" styleClass="{required:true,digit:true}" maxlength="2" value="12" onblur="getAcctInfo()"/>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�Ʒѽ�������</td>
						    <td>
						    <html:text property="endDate"  styleId="endDate" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��ҵ�ѽ��</td>
						    <td>
						    <html:text property="serviceAmount"  styleId="serviceAmount" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�����ѽ��</td>
						    <td>
						    <html:text property="lightAmount"  styleId="lightAmount" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr id="tr1">
						    <td class="formlabel nes">һ�����Ƿ���ס</td>
						    <td>
						    	<html:select property="checkinState" styleId="checkinState" onchange="check()">
						    		<html:options collection="yesNos" property="value" labelProperty="name" />
						    	</html:select>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr id="tr2">
					   		<td class="formlabel nes">�Ƿ�Ϊ����20%</td>
						    <td>
						    	<html:select property="suPayState" styleId="suPayState" onchange="checkSuPay()">
						    		<html:option value="N">��</html:option>
						    		<html:option value="Y">��</html:option>
						    	</html:select>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ϼƽ��</td>
						    <td>
						    <html:text property="totalAmount"  styleId="totalAmount" styleClass="{required:true,num:true}" maxlength="8"/>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">��ע</td>
						    <td>
						    <html:text property="remark"  styleId="remark" maxlength="25"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="����" onclick="save()"/>
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/commonServiceBill.do?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</html:form>
	<!--��Ȩ����-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
