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
				    		 $('#houseSn').focus();
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 var mobile = data.mobile;
				    	 $('#ownerName').val(ownerName);
				    	 $('#mobile').val(mobile);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			function change(){
				var isInternal = $('#isInternal').val();
				if(isInternal == '1'){
					$("tr[class='houseSn']").show();
				}else{
					$("tr[class='houseSn']").hide();
				}
			}
			function getAcctInfo(){
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
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/parkingBill.do?action=getAcctInfo',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 var endDate = data.endDate;
				    	 var amount = data.amount;
				    	 $('#endDate').val(endDate);
				    	 //$('#amount').val(amount);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="parkingBill.do?action=doAdd" styleId="dataForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">车位号</td>
						    <td>
						    	<html:text property="parkingSn"  styleId="parkingSn" styleClass="{required:true}" maxlength="10" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车牌号</td>
						    <td>
						    	<html:text property="carSn" styleId="carSn" styleClass="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">是否小区业主</td>
						    <td>
						    	<html:select property="isInternal" onchange="change()" styleId="isInternal">
						    		<html:options collection="yesNos" property="value" labelProperty="name" />
						    	</html:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="houseSn">
						    <td class="formlabel">房屋编号</td>
						    <td>
						    	<html:text property="houseSn" styleId="houseSn" maxlength="10" onblur="getOwnerName()"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车主姓名</td>
						    <td>
						    	<html:text property="ownerName" styleId="ownerName" styleClass="{required:true}" maxlength="10" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">电话</td>
						    <td>
						    	<html:text property="mobile" styleId="mobile" maxlength="20" styleClass="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">起始日期</td>
						    <td>
						    <html:text property="beginDate"  styleId="beginDate" styleClass="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">缴费月数</td>
						    <td>
						    <html:text property="monthNum"  styleId="monthNum" styleClass="{required:true,digit:true}" maxlength="2" onblur="getAcctInfo()"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr> 
					   <tr>
						    <td class="formlabel nes">结束日期</td>
						    <td>
						    <html:text property="endDate"  styleId="endDate" styleClass="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">金额</td>
						    <td>
						    <html:text property="amount"  styleId="amount" styleClass="{required:true,num:true}" maxlength="8"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    <html:text property="remark"  styleId="remark" maxlength="25"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/parkingBill.do?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</html:form>
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
