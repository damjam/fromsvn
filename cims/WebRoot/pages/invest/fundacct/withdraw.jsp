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
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		
			$().ready(function(){
				$('#amount').blur(function(){
					formatCurrency(this);
					var val = $(this).val();
					if(val.length > 0){
						var pattern = /^\d*(\.\d+)?$/;
						if(!pattern.test(val)){
							alert('金额不合法!');
							$(this).val('').focus();
						}
						if(parseFloat(val) == 0){
							alert('金额必须大于0');
							$(this).val('').focus();
						}
					}
				});
			});
			function changeAcct(){
				var acctNo = $('#acctNo').val();
				var params = 'acctNo='+acctNo;
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/investFundAction.do?action=getBankAcct',
				     async:true,
				     dataType: "json",
				     data:params,
					 success:function(data) {
				    	$('#signContractId').val(data.id);
				    	$('#bankAcctInfo').val(data.bankAcctInfo);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			$().ready(function(){
				changeAcct();
			});
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="investFundAction.do?action=toVerifyWithdraw" styleId="inputForm" styleClass="validate">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td width="100" height="30" align="right">资金账户</td>
							<td>
								<html:select property="acctNo" style="width:200px;" onchange="changeAcct()" styleId="acctNo">
									<logic:iterate id="element" name="aipAccts">
										<html:option value="${element.key}">${element.key} 可用余额 ${element.value}</html:option>
									</logic:iterate>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">银行账户</td>
							<td>
								<!-- 
								<html:select property="signContractId">
									<logic:iterate id="element" name="payChnls">
										<html:option value="${element.id}">${element.payChnlTypeName} ${element.payChnlNo}</html:option>
									</logic:iterate>
								</html:select> -->
								<html:hidden property="signContractId" styleId="signContractId"/>
								<html:text property="bankAcctInfo" readonly="true" styleId="bankAcctInfo"></html:text>
							</td>
						</tr>
						<tr>
							<td width="100" height="30" align="right">金额</td>
							<td>
								<html:text property="amount" maxlength="16" styleClass="{required:true,num:true,max:99999999999.99,min:0.01}" styleId="amount"/>
								<span class="field_tipinfo">请输入正确格式的金额</span>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td colspan="3" class="btnbox">
								<input type="submit" value="提交" id="input_btn2" name="ok" />
								<input type="button" value="取消 " onclick="toHomePage()"/>
							</td>
						</tr>
					</table>
				</html:form>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>