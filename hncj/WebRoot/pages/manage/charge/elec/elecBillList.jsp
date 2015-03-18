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
		
		<title>ˮ���˵�</title>
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
				$('#btnPrint').click(function(){
					var startCreateDate = $('#startCreateDate').val();
					var endCreateDate = $('#endCreateDate').val();
					var buildingNo = $('#buildingNo').val();
					if(startCreateDate.trim() == '' || endCreateDate.trim() == ''){
						alert('��ѡ�񴴽�����');
						return;
					}
					if(buildingNo.trim() == ''){
						alert('��ѡ��¥��');
						return;
					}
					window.open(CONTEXT_PATH+'/reportAction.do?action=elecBillDetail&buildingNo='+buildingNo+"&startCreateDate="+startCreateDate+"&endCreateDate="+endCreateDate);
				});
				
			});
			function charge(id, amount, balance){
				balance = parseFloat(balance); 
				amount = parseFloat(amount);
				
				var flag= false;
				var tip = "ȷ���շ�?";
				if(balance > 0){
					if(balance >= amount){
						tip = "ҵ��Ԥ���˻��������"+balance+"Ԫ,ˮ�ѽ����п۳���ȷ�ϲ���?";
					}else{
						var lackAmt = amount-balance;
						tip = "����ҵ��Ԥ���˻��п۳�"+balance+"Ԫ,���貹��"+lackAmt+"Ԫ��ȷ�ϲ���?";
					}
				}
				
				if(window.confirm(tip)){
					//var url = 
					//gotoUrl('/elecBill.do?action=charge&id='+id);
					$('#queryForm').attr('action', CONTEXT_PATH+'/elecBill.do?action=charge&id='+id);
					$('#queryForm').submit();
					$('#queryForm').attr('action', CONTEXT_PATH+'/elecBill.do?action=list');
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=elecBill&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/elecBill.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								��������
							</td>
							<td>
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								���ݱ��
							</td>
							<td>
								<html:text property="houseSn" styleId="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">״̬</td>
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
								¥��
							</td>
							<td>
								<html:select property="buildingNo" style="width:166px" styleId="buildingNo">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="buildingNos" property="key" labelProperty="value" />
								</html:select>
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
								<input type="button" value="��ӡǷ���˵�" id="btnPrint"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
			<!-- ������Ϣ -->
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>������Ϣ</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">Ӧ�ɱ���</td>
							<td align="center">Ӧ�ɽ�Ԫ��</td>
							<td align="center">�ѽɱ���</td>
							<td align="center">�ѽɽ�Ԫ��</td>
							<td align="center">�����ѽɱ���</td>
							<td align="center">�����ѽɽ�Ԫ��</td>
							<td align="center">δ�ɱ���</td>
							<td align="center">δ�ɽ�Ԫ��</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="totalAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.paidCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="paidAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.partPaidCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="partPaidAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.unpayCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="unpayAmt" format="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >�˵���</td>
						 	<td >���ݱ��</td>
						 	<td >ҵ������</td>
						 	<td >��ˮ�·�</td>
						    <td >���ڶ���</td>
						    <td >���ڶ���</td>
						    <td >ʵ������</td>
						    <td >����</td>
						    <td >Ӧ�ɽ��</td>
						    <td >�ѽɽ��</td>
						    <td >�ɷ�ʱ��</td>
						    <td >�˵�״̬</td>
						    <td >��ע</td>
						    <td >����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td>${element.recordMonth}</td>
								<td>${element.prenum}</td>
								<td>${element.curnum}</td>
								<td>${element.num}</td>
								<td><bean:write name="element" property="price" format="##0.00"/></td>
								<td><bean:write name="element" property="amount" format="##0.00"/></td>
								<td><bean:write name="element" property="paidAmt" format="##0.00"/></td>
								<td width="120"><bean:write name="element" property="chargeDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>
							    	<f:state className="BillState" value="${element.state}" />
							    </td>
							    <td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" >${element.remark}</td>
							    <td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<a href="javascript:charge('${element.id}', '${element.amount}', '${element.balance}')">�շ�</a>
							    	</logic:equal>
							    	<logic:equal value="01" name="element" property="state">
							    		<a href="javascript:openReport(${element.id})">��ӡ</a>
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
