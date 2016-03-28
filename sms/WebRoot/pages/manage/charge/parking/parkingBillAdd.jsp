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
			function getOwnerName(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:'${uri}?action=getOwnerName',
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
				    	 $('#ownerName').val(ownerName);
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
				     url:'${uri}?action=getAcctInfo',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 var endDate = data.endDate;
				    	 var amount = data.amount;
				    	 $('#endDate').val(endDate);
				    	 $('#amount').val(amount);
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
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">车位号</td>
						    <td>
						    	<s:textfield name="parkingSn"  id="parkingSn" class="{required:true}" maxlength="10" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车牌号</td>
						    <td>
						    	<s:textfield name="carSn" id="carSn" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">是否小区业主</td>
						    <td>
						    	<s:select name="isInternal" id="isInternal" list="#request.yesNos" listKey="value" listValue="name" onchange="change()"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="houseSn">
						    <td class="formlabel">房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn" id="houseSn" maxlength="10" onblur="getOwnerName()"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车主姓名</td>
						    <td>
						    	<s:textfield name="ownerName" id="ownerName" class="{required:true}" maxlength="10" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">起始日期</td>
						    <td>
						    <s:textfield name="beginDate"  id="beginDate" class="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">缴费月数</td>
						    <td>
						    <s:textfield name="monthNum"  id="monthNum" class="{required:true,digit:true}" maxlength="2" onblur="getAcctInfo()"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr> 
					   <tr>
						    <td class="formlabel nes">结束日期</td>
						    <td>
						    <s:textfield name="endDate"  id="endDate" class="{required:true}" maxlength="8" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">金额</td>
						    <td>
						    <s:textfield name="amount"  id="amount" class="{required:true,num:true}" maxlength="8"/>
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
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</form>
</body>
</html>
