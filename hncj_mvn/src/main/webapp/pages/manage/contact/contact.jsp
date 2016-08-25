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
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<f:js src="/dtree/wtree.js"/>
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/layer/layer.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>	
		<script type="text/javascript">
			
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
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doEdit" id="dataForm" method="post" class="validate">
		<s:hidden name="id"/>
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">姓名</td>
						    <td>
						    	<s:textfield name="contactName"  id="contactName" class="{required:true}" maxlength="20"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">性别</td>
						    <td>
						    	<s:select name="sex" id="sex" list="#request.sexTypes" listKey="value" listValue="name"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">所属行业</td>
						    <td>
						    <s:textfield name="industry"  id="industry" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">职位</td>
						    <td>
						    <s:textfield name="position"  id="position" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">工作单位</td>
						    <td>
						    	<s:textfield name="workUnit"  id="workUnit" class="{required:true}" maxlength="20"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">电话</td>
						    <td>
						    	<s:textfield name="mobile" id="mobile" maxlength="15" class="{required:true}"/>
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
