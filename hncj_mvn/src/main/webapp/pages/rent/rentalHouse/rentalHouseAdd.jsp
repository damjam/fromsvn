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
		<link href="http://cdn.bootcss.com/chosen/1.5.1/chosen.css" rel="stylesheet">
		
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		<script src="http://cdn.bootcss.com/chosen/1.5.1/chosen.jquery.js"></script>
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
			function checkSaleState(){
				var saleState = $('#saleState').val();
				if(saleState == '是'){
					$('#ownerName').attr('disabled',false);
					$('#ownerNameTr').show();
					$('#ownerTel').attr('disabled',false);
					$('#ownerTelTr').show();
				}else{
					$('#ownerName').attr('disabled',true);
					$('#ownerNameTr').hide();
					$('#ownerTel').attr('disabled',true);
					$('#ownerTelTr').hide();
				}
			}
			function upper(){
				var houseSn = $('#houseSn').val();
				$('#houseSn').val(houseSn.toUpperCase(houseSn));
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" method="post" class="validate">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn" maxlength="20" onblur="upper(this)"/>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes">是否已售</td>
						    <td>
						    	<s:select list="{'是','否' }" id="saleState" name="saleState" onchange="checkSaleState()"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr id="ownerNameTr">
						    <td class="formlabel">业主姓名</td>
						    <td>
						    	<s:textfield name="ownerName" id="ownerName" maxlength="20"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr id="ownerTelTr">
						    <td class="formlabel">业主电话</td>
						    <td>
						    	<s:textfield name="ownerTel" id="ownerTel" maxlength="20"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">面积</td>
						    <td>
						    	<s:textfield name="area" id="area" maxlength="6" class="{required:true,num:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">户型</td>
						    <td>
						    	<s:textfield name="houseType" id="houseType" maxlength="10"/>
						    	<span class="field_tipinfo">如：一室一厅</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">装修类型</td>
						    <td>
						    	<s:select list="#request.decorateTypes" name="decorateType" listKey="value" listValue="name"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <!-- 
					   <tr>
					   		<td class="formlabel">房间配置</td>
						    <td>
						    	<s:textfield name="roomConfig" maxlength="50"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr> -->
					   <tr>
						    <td class="formlabel">房间配置</td>
						    <td nowrap="nowrap">
						    	<div style="float: left; width: 360px;">
						    	 <select data-placeholder="请输入关键字" name="configArray" multiple class="chosen-select" style="width:350px;" id="multiple-clients">
						           ${optStr}
						         </select>	
						         </div>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">押金与付款要求</td>
						    <td>
						    	<s:textfield name="payRule" maxlength="50"/>
						    	<span class="field_tipinfo">如:押一付一</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">对租客其他要求</td>
						    <td>
						    	<s:textfield name="otherRule" maxlength="50"/>
						    	<span class="field_tipinfo">如:仅限女性租客,至少签一年合同等</span>
						    </td>
					   </tr>
					   <tr>
					   	   <td class="formlabel">参考租金</td>
						    <td>
						    	<s:textfield name="rentFee" id="rentFee" maxlength="6" class="{digit:true}"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" maxlength="50"/>
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
<script type="text/javascript">
    var config = {
      '.chosen-select': {width:"95%"}
    }
    for (var selector in config) {
      $(selector).chosen(config[selector]);
    }
  </script>
</html>
