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
		<title>实物赎回</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function queryStoreBranch(){
				var oriTakeBank = $('#takeBank').val();
				var cityCode = $('#cityCode').val();
				if(cityCode == ''){
					alert('请先选择提货城市');
					return;
				}
				var param = 'cityCode='+$('#cityCode').val();
				popUp.popUpStoreBranch('takeBank', 'takeBankName', param);
				var takeBank = $('#takeBank').val();
				if(oriTakeBank != takeBank){
					queryTakeDate();
				}
			}
			function queryGoldVariety(){
				popUp.popUpgoldVariety('goldType', 'goldTypeName');
			}
			function queryTakeDate(){
				var takeBank = $('#takeBank').val();
				if(takeBank == ''){
					//alert('请先选择提货网点');
					return;
				}
				var params='branchId='+takeBank;
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/investGoldAction.do?action=queryTakeDate',
				     async:true,
				     dataType: "json",
				     data:params,
					 success:function(data) {
				    	var jsonObj = data; 
			    		$("#takeDate").empty();
						for(var i=0; i<jsonObj.length; i++){
							var takeDate = jsonObj[i];
							$("#takeDate").append("<option value='"+takeDate+"'>"+takeDate+"</option>");
						}
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			function changeCity(){
				$('#takeBank').val('');
				$('#takeBankName').val('');
				$('#takeDate').empty();
			}
			function save(){
				var goldType = $('#goldType').val();
				var amount = $('#amount').val();
				var pattern = /^\d+0$/;
				if(!pattern.test(amount)){
					//return;
				}
				var multiple;
				if(goldType == '201'){
					multiple = 1000;
				}else if(goldType == '207'){
					multiple = 100;
				}else {
					multiple = 10;
				}
				if(amount%multiple != 0){
					alert('提货重量必须是'+multiple+'的整数倍');
					return;
				}
				//设置城市名称
				var cityName = $('#cityCode').find('option:selected').text();
				$('#cityName').val(cityName);
				setBalance();
				FormUtils.submitFirstTokenForm();
			}
			$().ready(function(){
				$('#amount').blur(function(){
					formatCurrency(this);
					var val = $(this).val();
					if(val.length > 0){
						var pattern = /^\d+0$/;
						if(!pattern.test(val)){
							alert('数量不合法!');
							$(this).val('').focus();
						}
					}
				});
				queryTakeDate();
			});
			function setBalance(){
				var acctInfo = $('#acctNo').find('option:selected').text();
				if(acctInfo == ''){
					return;
				}
				var balance = acctInfo.substr(acctInfo.indexOf(':')+1, acctInfo.length);
				$('#balance').val(balance);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg />
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<html:form action="investGoldAction.do?action=verifyRedeemGold" styleId="inputForm" styleClass="validate">
					<html:hidden property="balance" styleId="balance" value="0"/>
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">定投账户</td>
							<td>
								<html:select property="acctNo" style="width:280px;" styleId="acctNo">
									<logic:iterate id="element" name="aipAccts">
										<html:option value="${element.key}">${element.key} 可赎回重量:${element.value} </html:option>
									</logic:iterate>
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="formlabel">赎回类别</td>
							<td>
								<html:hidden property="goldType" styleId="goldType"/>
								<html:text property="goldTypeName" readonly="readonly" styleClass="{required:true}" styleId="goldTypeName"/>
								<img align="left" src="${CONTEXT_PATH}/images/search.jpeg" alt="搜索" onclick="queryGoldVariety()"/>
								<span class="field_tipinfo">请输入赎回类别</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel">赎回重量(克)</td>
							<td>
								<html:text property="amount" maxlength="16" styleClass="{required:true,num:true,max:99999999999.99,min:0.01}" styleId="amount"/>
								<span class="field_tipinfo">请输入正确格式的重量</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货地址</td>
							<td>
								<html:select property="cityCode" styleId="cityCode" onchange="changeCity()" styleClass="{required:true}">
									<html:options collection="cityMap" property="key" labelProperty="value"/>
								</html:select>
								<html:hidden property="cityName" styleId="cityName"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货网点</td>
							<td>
								<html:hidden property="takeBank" styleId="takeBank" />
								<html:text property="takeBankName" styleId="takeBankName" readonly="true" styleClass="{required:true}"></html:text>
								<img align="left" src="${CONTEXT_PATH}/images/search.jpeg" alt="查询" onclick="queryStoreBranch()"/>
								<span class="field_tipinfo">请选择提货网点</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel">提货日期</td>
							<td>
								<html:select property="takeDate" styleId="takeDate" styleClass="{required:true}"></html:select>
								<span class="field_tipinfo">提货日期不能为空</span>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td colspan="3" class="btnbox">
								<input type="button" value="提交" id="input_btn2" name="ok" onclick="save()"/>
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