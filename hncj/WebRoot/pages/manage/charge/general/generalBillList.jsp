<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title></title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				$('#btnAdd').click(function(){
					gotoUrl('/generalBill.do?action=toAdd');
				});
				
			});
			function charge(id){
				if(window.confirm("ȷ���շ�?")){
					gotoUrl('/generalBill.do?action=charge&id='+id);
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=generalBill&id='+id);
			}
			function delRecord(id){
				if(!window.confirm("ȷ��ɾ��?")){
					return;
				}
				gotoUrl('/generalBill.do?action=deleteBill&id='+id);
			}
			function refund(id){
				if(!window.confirm("ȷ���˿�?")){
					return;
				}
				gotoUrl('/generalBill.do?action=refund&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/generalBill.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								�տ�����
							</td>
							<td>
								<html:text property="startChargeDate" styleId="startChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endChargeDate" styleId="endChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								�ؼ���
							</td>
							<td>
								<html:text property="keyword" styleId="keyword" maxlength="20"/>
							</td>
							<td class="formlabel nes">״̬</td>
						    <td>
						    	<html:select property="state" styleId="state">
						    		<html:option value="">---ȫ��---</html:option>
						    		<html:options collection="billStates" property="value" labelProperty="name" />
						    	</html:select>
						    </td>
					    </tr>
						<tr>
							<td class="formlabel">
								�˵���
							</td>
							<td>
								<html:text property="id" styleId="id" maxlength="20"/>
							</td>
							<td class="formlabel">
								���
							</td>
							<td>
								<html:text property="year" styleId="year" onclick="WdatePicker({dateFmt:'yyyy'})"/>
							</td>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" id="btnClear" />&nbsp;
								<input type="button" value="����" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- ������Ϣ -->
			 
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>������Ϣ</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">�ܱ���</td>
							<td align="center">�ܽ�Ԫ��</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="totalAmt" format="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >�˵���</td>
						    <td >�շ���Ŀ</td>
						    <td >����</td>
						    <td >����</td>
						    <td >�ܶ�</td>
						    <td >ʵ�ս��</td>
						    <td >������</td>
						    <td >����ʱ��</td>
						    <td >�տ���</td>
						    <td >״̬</td>
						    <td >��ע</td>
						    <td>����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td><f:type className="TradeType" value="${element.tradeType}"/> </td>
								<td><bean:write name="element" property="unitPrice" format="##0.00"/></td>
								<td>${element.num}</td>
								<td><bean:write name="element" property="totalAmt" format="##0.00"/></td>
								<td><bean:write name="element" property="paidAmt" format="##0.00"/></td>
								<td>${element.payerName}</td>
								<td><bean:write name="element" property="chargeDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${element.chargeUser}</td>
								<td>
							    	<f:state className="BillState" value="${element.state}" />
							    </td>
								<td>${element.remark}</td>
								<td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<a href="javascript:charge('${element.id}')">�շ�</a>
							    		<a href="javascript:delRecord('${element.id}')">ɾ��</a>
							    	</logic:equal>
							    	<logic:equal value="01" name="element" property="state">
							    		<a href="javascript:openReport('${element.id}')">��ӡ</a>
							    	</logic:equal>
							    </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
