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
			$(document).ready(function(){
				changeAipMode();
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
				if(aipType == '1'){
					//日均
					$('#aipAmount').val(500);
					$('#daypick').hide();
				}else{
					//指定日
					$('#aipAmount').val(100);
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
			function back(){
				backToList('${backUrl}', '${params}', 'Y');
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="investPlanAction.do?action=doAdd" styleId="dataForm" method="post" styleClass="validate">
		<html:hidden property="updateSymbol" value="Y"/>
		<html:hidden property="acctNo" value="${investInfoActionForm.acctNo}"/>
		<html:hidden property="aipMode" value="${investInfoActionForm.aipMode}"/>
		<html:hidden property="aipPeriod" value="${investInfoActionForm.aipPeriod}"/>
		<html:hidden property="aipAmount" value="${investInfoActionForm.aipAmount}"/>
		<html:hidden property="isDelay" value="${investInfoActionForm.isDelay}"/>
		<html:hidden property="isEffect" value="${investInfoActionForm.isEffect}"/>
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
						    <td class="formlabel">定投账号</td>
						    <td>${investInfoActionForm.acctNo}
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">定投模式</td>
						    <td>
						    	<f:type className="AipMode" value="${investInfoActionForm.aipMode}"/>
						    </td>
					   </tr>
					   <tr id="tr1">
						    <td class="formlabel">定投周期</td>
						    <td>
						    	<f:type className="AipPeriod" value="${investInfoActionForm.aipPeriod}"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel" id="amountTd">重量</td>
						    <td>${investInfoActionForm.aipAmount}
						    </td>
					   </tr>
					    <tr>
						    <td class="formlabel" id="amountTd">是否无限延期</td>
						    <td>
						    	<f:type className="YesNoType" value="${investInfoActionForm.isDelay}" />
						    </td>
					   </tr>
					    <tr>
						    <td class="formlabel" id="amountTd">是否立即生效</td>
						    <td>
						   		 <f:type className="YesNoType" value="${investInfoActionForm.isEffect}" />
						    </td>
					   </tr>
					    <c:if test="${investInfoActionForm.aipPeriod eq '1'}">
					   <tr id="daypick">
					   		<td class="formlabel" id="amountTd" style="vertical-align:middle">定投日</td>
					   		<td>
					   			<table width="25%" border="0" cellspacing="3" cellpadding="0"">
						   			<logic:iterate id="element" name="investDays" >
						   				<tr>
							   				<logic:iterate id="row" name="element">
							   					<td>
							   						<html:multibox property="investDays" value="${row}" onclick="javascript:return false;"/>${row}
							   					</td>
							   				</logic:iterate>
										</tr>
									</logic:iterate>
									<tr><td colspan="7" style="color: red;">若定投日遇节假日（上金所休市）则顺延至节假日后第一个交易日</td></tr>
								</table>
					   		</td>
					   </tr>
					   </c:if>
				  </table>
				  
				  <div class="btnbox">
					 <input type="button"  value="提交 " onclick="save(this.form)"/>
					 <input type="button" value="返回修改" onclick="back()" />
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
