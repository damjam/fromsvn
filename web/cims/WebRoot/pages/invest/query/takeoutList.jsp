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
		<title>��ؼ�¼��ѯ</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
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
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investQueryAction.do?action=queryTakeoutRecord" styleClass="validate-tip" styleId="dataForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}A2038</caption>
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
								����
							</td>
							<td colspan="5">
								<html:text property="startDate" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px"/>
								&nbsp;
								-
								&nbsp;
								<html:text property="endDate" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" onclick="FormUtils.reset('dataForm', ['startDate', 'endDate']);"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- �����б��� -->
			<div class="tablebox">	
				<table class="detail_grid" width="20%" border="1" cellspacing="0" cellpadding="0">
					<tr>
						<td>�ۼ��������</td>
						<td><bean:write name="totalWeight" format="#0.0000"/> </td>
					</tr>
				</table>
				<br/>
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>����</td>
						    <td>Ʒ��</td>
						    <td>����</td>
						    <td>��̷�</td>
						    <td>Ʒ�ƽ����ӹ���</td>
						    <td>������</td>
						    <td>׼����</td>
						    <td>�������</td>
						    <td>�������</td>
						    <td>�������</td>
						    <td>���״̬</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.exch_date.value}</td>
								<td>${element.variety_name.value}</td>
								<td>${element.take_weight.value}</td>
								<td>${element.diff_bal.value}</td>
								<td>${element.process_cost.value}</td>
								<td>${element.take_fare.value}</td>
								<td>${element.take_margin.value}</td>
								<td>${element.re_margin.value}</td>
								<td>${element.city_code.value}</td>
								<td>${element.branch_id.value}</td>
								<td>${element.stat.value}</td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
