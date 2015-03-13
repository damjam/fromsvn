<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title>��Ͷ�ƻ���ѯ</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/plugin/ui.core.js"/>
		<f:js src="/js/plugin/ui.dialog.js"/>
		<f:css href="/css/jquery-ui-1.7.2.custom.css"/>
		<f:css href="/css/areapanel.css"/>
		<script type="text/javascript">
			$(function(){
				$('#minAmount').blur(function(){
					formatCurrency(this);
					var val = $(this).val();
					if(val.length > 0){
						var pattern = /^\d*(\.\d+)?$/;
						if(!pattern.test(val)){
							alert('���Ϸ�!');
							$(this).val('').focus();
						}
						if(parseFloat(val) == 0){
							alert('���������0');
							$(this).val('').focus();
						}
					}
				});
				$('#maxAmount').blur(function(){
					formatCurrency(this);
					var val = $(this).val();
					if(val.length > 0){
						var pattern = /^\d*(\.\d+)?$/;
						if(!pattern.test(val)){
							alert('���Ϸ�!');
							$(this).val('').focus();
						}
						if(parseFloat(val) == 0){
							alert('���������0');
							$(this).val('').focus();
						}
					}
				});
				
				$("#btnQry").live("click",function(){
					if($("#startDate").val()=="" || $("#endDate").val()==""){
						alert("��ʼ���ڻ�������ڲ���Ϊ�գ�");
						return false;
					}else if(parseInt($("#startDate").val(),10)>parseInt($("#endDate").val(),10)){
						alert("��ʼ���ڲ��ܴ��ڽ������ڣ�");
						return false;
					}else{
						$("#dataForm").submit();
						return true;
					}
				});
			});
			function operList(orderNo){
				showDetail('detail_box','/investQueryAction.do?action=operList&planNo='+orderNo, '������¼', 600, 280);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investQueryAction.do?action=queryPlan" styleClass="validate-tip" styleId="dataForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}A2032</caption>
						<tr>
							<td class="formlabel">
								��Ͷ�˺�
							</td>
							<td>
								<html:select property="acctNo">
									<html:options collection="payChnls" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
							<td class="formlabel">
								�ƻ���
							</td>
							<td>
								<html:text property="planNo" styleId="planNo"/>
							</td>
<!-- 							<td class="formlabel"> -->
<!-- 								��Ͷ���� -->
<!-- 							</td> -->
<!-- 							<td> -->
<%-- 								<html:select property="aipPeriod"> --%>
<%-- 									<html:option value="">--ȫ��--</html:option> --%>
<%-- 									<html:options collection="aipPeriods" labelProperty="name" property="value"/> --%>
<%-- 								</html:select> --%>
<!-- 							</td> -->
						</tr>
						<tr>
						<td class="formlabel">
								���ڷ�Χ
							</td>
							<td>
								<html:text property="startDate" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;" />
								&nbsp;
								-
								&nbsp;
								<html:text property="endDate" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;"/>
							</td>

							<td class="formlabel">
								�ƻ�״̬
							</td>
							<td>
							
								<html:select property="planState">
									<html:option value="">--ȫ��--</html:option>
									<html:options collection="planStates" labelProperty="name" property="value"/>
								</html:select>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							<td class="formlabel"> -->
<!-- 								��Ͷģʽ -->
<!-- 							</td> -->
<!-- 							<td> -->
<%-- 								<html:select property="aipMode"> --%>
<%-- 									<html:option value="">--ȫ��--</html:option> --%>
<%-- 									<html:options collection="aipModes" labelProperty="name" property="value"/> --%>
<%-- 								</html:select> --%>
<!-- 							</td> -->
<!-- 							<td class="formlabel"> -->
<!-- 								��Ͷ�������� -->
<!-- 							</td> -->
<!-- 							<td> -->
<%-- 								<html:text property="minAmount" styleId="minAmount" style="width:70px;" styleClass="{num:true}"/>&nbsp;- --%>
<%-- 								<html:text property="maxAmount" styleId="maxAmount" style="width:70px;"/> --%>
<!-- 							</td> -->
<!-- 						</tr> -->
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry" />&nbsp;
								<input type="button" value="����" onclick="FormUtils.reset('dataForm', ['startDate', 'endDate']);"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>��Ͷ�˺�</td>
						 	<td>��Ͷ�ƻ�����</td>
						    <td>��Ͷ����</td>
						    <td>��Ͷģʽ</td>
						    <td>��Ͷ��������</td>
						    <td>��Ͷ����</td>
						    <td>Ŀǰ��״̬</td>
						    <td>������¼</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.aip_no.value}</td>
								<td>${element.order_no.value}</td>
								<td>
									<f:type className="AipPeriod" value="${element.aip_periods.value}" />
								</td>
								<td>
									<f:type className="AipMode" value="${element.aip_mode.value}" />
								</td>
								<td>${element.aip_amount.value}</td>
								<td>${element.chn_aip_date.value}</td>
								<td>${element.plan_stat.value}</td>
								<td class="redlink"><a href="javascript:void(0)" onclick="operList('${element.order_no.value}')">������¼</a> </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
		<div id="detail_box" style="display: none"></div>
	</body>
</html>
