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
		
		<title>�ƽ��˻���ѯ</title>
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
						alert("��ѯ�Ŀ�ʼ���ڻ�������ڶ�����Ϊ�գ�");
						return false;
					}else if(parseInt($("#startDate").val(),10)>parseInt($("#endDate").val(),10)){
						alert("��ѯ�Ŀ�ʼ���ڲ��ܴ��ڽ������ڣ�");
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
		<html:form action="/investQueryAction.do?action=queryGoldAcct" styleClass="validate-tip" styleId="dataForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}A2036(�������Ϣ),2034�齻����ˮ</caption>
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
							<td>
								<html:text property="startDate" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;"/>
								&nbsp;
								-
								&nbsp;
								<html:text property="endDate" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;"/>
							</td>
							<td colspan="4"></td>
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
				<table class="detail_grid" width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15%">�ۼƻ�������(2039)</td>
							<td width="18%">${resMap.totalDeposit}</td>
							<td width="15%">�ۼ��������(2039)</td>
							<td width="18%">${resMap.totalTakeout}</td>
							<td width="15%">��ǰ�ƽ��˻����(2044)</td>
							<td width="18%">${resMap.currentMoney}</td>
						</tr>
						<tr>	
							<td>��ǰ�˻�ʵ�ʶ�Ͷ���</td>
							<td>${resMap.goldRealBal}</td>
							<td>��ǰ�˻���Ͷ����</td>
							<td>${resMap.avgPrice}</td>
							<td>���ն�Ͷ�۸�(2054,��ʼ���ڣ���������������������)</td>
							<td>${resMap.lastDayPrice}</td>
						</tr>
						<tr>
							<td>�ƽ��˻������ֵ</td>
							<td>${resMap.goldVirtualBal}</td>
							<td>����ӯ��</td>
							<td colspan="3">${resMap.accountSurplus}</td>
						</tr>
					</table>
					<br/>
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>�䶯ʱ��</td>
						 	<td>�䶯����</td>
						    <td>�仯ֵ</td>
						    <td>��ǰ����</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>{element.exch_time.value}</td>
								<td>{element.change_type.value}</td>
								<td>{element.exch_weight.value}</td>
								<td></td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
