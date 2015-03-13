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
				     url:CONTEXT_PATH + '/decorateServiceBill.do?action=getHouseInfo',
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
				    	 if(supPay != ''){
				    		 if(window.confirm("ҵ���ϴΰ�80%��ȡ����ҵ��δ���ڣ��Ƿ񲹽��Ѽ����20%?")){
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
	                     alert("���ӷ�����ʧ��");
	                 }   
				});
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="decorateServiceBill.do?action=doAdd" styleId="dataForm" method="post" styleClass="validate">
		<html:hidden property="csBillId" styleId="csBillId"/>
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
						    <td class="formlabel nes">¥��</td>
						    <td>
						    	<html:text property="floor" styleId="floor" styleClass="{required:true}" maxlength="6" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�������˷ѵ���</td>
						    <td>
						    <html:text property="cleanPrice"  styleId="cleanPrice" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�������˷�</td>
						    <td>
						    <html:text property="cleanAmount"  styleId="cleanAmount" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��������ʹ�÷�</td>
						    <td>
						    <html:text property="liftFee"  styleId="liftFee" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ϼƽ��</td>
						    <td>
						    <html:text property="amount"  styleId="amount" styleClass="{required:true,num:true}" maxlength="8"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr id="tr1" style="display: none;">
						    <td class="formlabel nes">������ҵ��</td>
						    <td>
						    <html:text property="supFee"  styleId="supFee" styleClass="{num:true}" maxlength="10" readonly="true"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
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
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/decorateServiceBill.do?action=list')"/>
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
