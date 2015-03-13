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
		<title>添加定投计划</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>	
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function add(){
				var aipAmount = $('#aipAmount').val();
				var aipMode = $('#aipMode').val();
				if(aipMode == '1'){
					if(parseInt(aipAmount) > 10000){
						return;
					}
					$('#aipAmount').val(parseInt(aipAmount)+100);
				}else{
					if(parseInt(aipAmount) > 100){
						return;
					}
					$('#aipAmount').val(parseInt(aipAmount)+1);
				}
			}
			function reduce(){
				var aipAmount = parseInt($('#aipAmount').val());
				var aipMode = $('#aipMode').val();
				var aipType = $('#aipType').val();
				if(aipMode == '1'){
					var limit = 100;
					if(aipType == '1'){
						limit = 500;
					}
					if(aipAmount <= limit){
						return;
					}
					$('#aipAmount').val(aipAmount-100);
				}else{
					if(aipAmount <= 1){
						return;
					}
					$('#aipAmount').val(aipAmount-1);
				}
			}
			function changeAipMode(){
				var aipMode = $('#aipMode').val();
				var optionNum = $("#aipType").find("option").length;
				if(aipMode == '1'){
					$('#amountTd').text('金额(元)');
					var aipType = $('#aipType').val();
					if(aipType == '1'){
						$('#aipAmount').val(500);
					}else{
						$('#aipAmount').val(100);
					}
					if(optionNum == 1){
						 $("#aipType").append("<option value='1'>日均定投</option>"); 
					}
				}else{
					$('#amountTd').text('重量(克)');
					$('#aipAmount').val(1);
					if(optionNum == 2){
						$("#aipType option[value='1']").remove(); 
					}
				}
				changeAipType();
			}
			$().ready(function(){
				changeAipMode();
				var aipAmount = '${investInfoActionForm.aipAmount}';
				if(aipAmount != ''){
					$('#aipAmount').val(aipAmount);
				}
			});
			function check(){
				var pattern;
				var aipMode = $('#aipMode').val();
				if(aipMode == '1'){
					pattern = /^[1-9]([0-9]*)00$/;
				}else{
					pattern = /^[1-9]([0-9]*)$/;
				}
				var aipAmount = $('#aipAmount').val();
				if(!pattern.test(aipAmount)){
					alert('输入数量不合法');
					$('#aipAmount').val('');
					$('#aipAmount').focus();
					return;
				}
			}
			function changeAipType(){
				var aipType = $('#aipType').val();
					var aipMode = $('#aipMode').val();
					if(aipType == '1'){
					//日均
						if(aipMode == '1'){
							$('#aipAmount').val(500);
						}
						$('#daypick').hide();
					}else{
						//指定日
						if(aipMode == '1'){
							$('#aipAmount').val(100);
						}
						$('#daypick').show();
					}
			}
			function save(){
				var aipType = $('#aipType').val();
				if(aipType == '2'){
					if(!FormUtils.hasSelected('investDays')){
						alert('请选择定投日');
						return false;
					}
				}
				FormUtils.submitFirstTokenForm();
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="investPlanAction.do?action=verify" styleId="dataForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">支付渠道</td>
						    <td>
						    	<html:select property="signContractId">
									<logic:iterate id="element" name="payChnls">
											<html:option value="${element.id}">${element.payChnlTypeName} ${element.payChnlNo}</html:option>
									</logic:iterate>
								</html:select>
						    	<span class="field_tipinfo">选择支付渠道</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">定投模式</td>
						    <td>
						    	<html:select property="aipMode" styleId="aipMode" onclick="changeAipMode()">
						    		<html:options collection="aipModes" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">选择定投模式</span>
						    </td>
					   </tr>
					   <tr id="tr1">
						    <td class="formlabel nes">定投类别</td>
						    <td>
						    	<html:select property="aipType" styleId="aipType" onchange="changeAipType()">
						    		<html:options collection="aipTypes" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">选择定投类别</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes" id="amountTd">重量</td>
						    <td><a href="javascript:void(0)" onclick="reduce()">-</a>
						    	<html:text property="aipAmount" value="1" style="width:35px;" styleId="aipAmount" onblur="check()" maxlength="5" styleClass="{required:true}"></html:text>
						    	<a href="javascript:void(0)" onclick="add()">+</a>
						    	<span class="field_tipinfo">请输入合法的数量</span>
						    </td>
					   </tr>
					   <tr id="daypick" style="display: none;">
					   		<td class="formlabel nes" id="amountTd" style="vertical-align:middle">定投日</td>
					   		<td>
					   			<table width="25%" border="0" cellspacing="3" cellpadding="0"">
						   			<logic:iterate id="element" name="investDays">
						   				<tr>
						   				<logic:iterate id="row" name="element">
						   					<td>
						   						<html:multibox property="investDays" value="${row}"/>${row}
						   					</td>
						   				</logic:iterate>
										</tr>
									</logic:iterate>
								</table>
					   		</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="提交 " onclick="save(this.form)"/>
					 <input type="button" value="取消" onclick="gotoUrl('/investPlanAction.do?action=list')" />
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
