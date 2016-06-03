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
		<f:js src="/js/jquery.js"/>
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
			function copy(){
				var amount = $('#amount').val();
				$('#paidAmt').val(amount);
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel">商家名称</td>
						    <td>
						    	<s:textfield name="merchantName"  id="merchantName" maxlength="25" readonly="true" onclick="popup('merchantNo','merchantName');"/>
						    	<s:hidden name="merchantNo" id="merchantNo"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    <s:textfield name="houseSn"  id="houseSn" class="{required:true}" maxlength="20"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">开始日期</td>
						    <td>
						    <s:textfield name="startDate"  id="startDate" class="{required:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">结束日期</td>
						    <td>
						    <s:textfield name="endDate"  id="endDate" class="{required:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">金额</td>
						    <td>
						    <s:textfield name="amount"  id="amount" class="{required:true,num:true}" maxlength="8" onblur="copy()"/>
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
						    <td class="formlabel nes">联系电话</td>
						    <td>
						    	<s:textfield name="tel" id="tel" maxlength="20" />
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
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
