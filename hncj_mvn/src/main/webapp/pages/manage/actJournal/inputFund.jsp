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
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
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
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=deposit" id="accountActionForm" method="post" class="validate">
		<s:hidden name="id"/>
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">交易类型</td>
						    <td>
						    	<s:select name="tradeType" id="tradeType" list="${'06':'内部充值','09','其他'}" listKey="value" listValue="name"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">收入金额</td>
						    <td>
						    	<s:textfield name="amount" id="amount" class="{required:true,num:true}" maxlength="8"/>
						    	<span class="field_tipinfo">请填写正确的数字</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" id="remark" maxlength="50"/>
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
