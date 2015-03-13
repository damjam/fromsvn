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
		<title>��Ӷ�Ͷ�ƻ�</title>
		
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
				var aipPeriod = $('#aipPeriod').val();
				if(aipMode == '1'){
					var limit = 100;
					if(aipPeriod == '2'){
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
				var optionNum = $("#aipPeriod").find("option").length;
				if(aipMode == '1'){
					$('#amountTd').text('���(Ԫ)');
					var aipPeriod = $('#aipPeriod').val();
					if(aipPeriod == '1'){
						$('#aipAmount').val(500);
					}else{
						$('#aipAmount').val(100);
					}
					if(optionNum == 1){
						 $("#aipPeriod").append("<option value='2'>�¾�</option>"); 
					}
				}else{
					$('#amountTd').text('����(��)');
					$('#aipAmount').val(1);
					if(optionNum == 2){
						$("#aipPeriod option[value='2']").remove(); 
					}
				}
				changeAipPeriod();
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
					alert('�����������Ϸ�');
					$('#aipAmount').val('');
					$('#aipAmount').focus();
					return;
				}
			}
			function changeAipPeriod(){
				var aipPeriod = $('#aipPeriod').val();
					var aipMode = $('#aipMode').val();
					if(aipPeriod == '2'){
					//�¾�
						if(aipMode == '1'){
							$('#aipAmount').val(500);
						}
						$('#daypick').hide();
					}else{
						//ָ����
						if(aipMode == '1'){
							$('#aipAmount').val(100);
						}
						$('#daypick').show();
					}
			}
			function save(){
				var aipPeriod = $('#aipPeriod').val();
				if(aipPeriod == '1'){
					if(!FormUtils.hasSelected('investDays')){
						alert('��ѡ��Ͷ��');
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
						    <td class="formlabel nes">��Ͷ�˺�</td>
						    <td>
						    	<html:select property="acctNo" styleId="acctNo" >
									<html:options collection="signContracts" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
						    	<span class="field_tipinfo">ѡ��Ͷ�˺�</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��Ͷģʽ</td>
						    <td>
						    	<html:select property="aipMode" styleId="aipMode" onclick="changeAipMode()">
						    		<html:options collection="aipModes" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">ѡ��Ͷģʽ</span>
						    </td>
					   </tr>
					   <tr id="tr1">
						    <td class="formlabel nes">��Ͷ����</td>
						    <td>
						    	<html:select property="aipPeriod" styleId="aipPeriod" onchange="changeAipPeriod()">
						    		<html:options collection="aipPeriods" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">ѡ��Ͷ����</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes" id="amountTd">����</td>
						    <td><a href="javascript:void(0)" onclick="reduce()">-</a>
						    	<html:text property="aipAmount" value="1" style="width:35px;" styleId="aipAmount" onblur="check()" maxlength="5" styleClass="{required:true}"></html:text>
						    	<a href="javascript:void(0)" onclick="add()">+</a>
						    	<span class="field_tipinfo">������Ϸ�������</span>
						    </td>
					   </tr>
					   <tr >
						    <td class="formlabel nes">�Ƿ���������</td>
						    <td>
						    	<html:select property="isDelay" styleId="isDelay" >
						    		<html:options collection="yesNos" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">ѡ���Ƿ���������</span>
						    </td>
					   </tr>
					    <tr>
						    <td class="formlabel nes">�Ƿ�������Ч</td>
						    <td>
						    	<html:select property="isEffect" styleId="isEffect" >
						    		<html:options collection="yesNos" labelProperty="name" property="value"/>
						    	</html:select>
						    	<span class="field_tipinfo">ѡ���Ƿ�������Ч</span>
						    </td>
					   </tr>
					   <tr id="daypick" style="display: none;">
					   		<td class="formlabel nes" id="amountTd" style="vertical-align:middle">��Ͷ��</td>
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
									<tr><td colspan="7" style="color: red;">����Ͷ�����ڼ��գ��Ͻ������У���˳�����ڼ��պ��һ��������</td></tr>
								</table>
					   		</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="�ύ " onclick="save(this.form)"/>
					 <input type="button" value="ȡ��" onclick="gotoUrl('/investPlanAction.do?action=toList')" />
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</html:form>	
	<!--��Ȩ����-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
