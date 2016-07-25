<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<script src="http://cdn.bootcss.com/jqueryui/1.12.0-rc.2/jquery-ui.js"></script>	
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/layer/layer.js"/>
		<!-- 
		<f:js src="/js/plugin/jquery.validate.js"/>		
		 -->
		<script type="text/javascript">
			$().ready(function(){
				var inoutNum = $('#inoutNum').val();
				if(inoutNum == '0'){
					$('#inoutNum').val(1);
				}
			});
		 	function save(){
		 		if($('#orderId').val().trim() == ''){
		 			if($('#remark').val().trim() == ''){
		 				layer.msg('请在备注中填写出库原因',{icon:7,shade:0.2,time:1000});
		 				return;
		 			}
		 		}
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=outstock" id="dataForm" method="post">
	    <s:hidden name="id"/>
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
					   		<td class="formlabel">库存数量</td>
						    <td>
						    	${num }
						    </td>
						</tr>
						<tr>    
						    <td class="formlabel">订单号</td>
						    <td>
						    	<s:textfield name="orderId" id="orderId" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">如订单号为空，请在备注中说明出库原因</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">出库数量</td>
						    <td>
						    	<s:textfield name="inoutNum" id="inoutNum" maxlength="6" size="6" class="{required:true,digit:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" id="remark" maxlength="25"/>
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
