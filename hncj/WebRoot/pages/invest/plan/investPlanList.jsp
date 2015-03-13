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
		
		<title>��Ͷ�ƻ��б�</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				$('#btnAdd').click(function(){
					var url="/investPlanAction.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			function oper(type, planNo,orderNo){
				if(type == 'U'){
					gotoUrl('/investPlanAction.do?action=toUpdate&acctNo='+planNo+"&orderNo="+orderNo);
				}
				if(type == 'P'){
					if(confirm("ȷ��Ҫ��ͣ��")){
						gotoUrl('/investPlanAction.do?action=doPause&acctNo='+planNo+"&orderNo="+orderNo);
						return true;
					}else{
					 	return false;
					 }
					}
				if(type == 'R'){
					if(confirm("ȷ��Ҫ�ָ���")){
						gotoUrl('/investPlanAction.do?action=doRecover&acctNo='+planNo+"&orderNo="+orderNo);
						return true;
					}else{
					 	return false;
					 }
				}
				if(type == 'C'){
					if(confirm("ȡ��֮�����ٻָ���ȷ��Ҫȡ����?")){
						gotoUrl('/investPlanAction.do?action=doCancel&acctNo='+planNo+"&orderNo="+orderNo);
					 	return true;
					}else{
					 	return false;
					 }
				}
				if(type == 'B'){
// 					gotoUrl('/investPlanAction.do?action=doCancel&planNo='+planNo+"&orderNo="+orderNo);
				alert('������δʵ�֣�');
				}
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investPlanAction.do?action=list" styleClass="validate-tip" styleId="dataForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								��Ͷ�˺�
							</td>
							<td>
								<html:select property="acctNo">
									<html:options collection="signContracts" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
							<td class="formlabel">
								״̬
							</td>
							<td>
								<html:select property="planState">
									<html:option value="">--ȫ��--</html:option>
									<html:options collection="planStates" labelProperty="name" property="value"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="submit" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" onclick="FormUtils.reset('dataForm');"/>&nbsp;
								<input type="button" value="����" id="btnAdd"/>
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
						 	<td width="8%">�ƻ���</td>
						    <td>��Ͷģʽ</td>
<!-- 						    <td>��Ͷ���</td> -->
						    <td>��Ͷ����</td>
						    <td>��Ͷ���/����</td>
						    <td>֧������</td>
						    <td>״̬</td>
						    <td>����</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.order_no.value}</td>
								<td>
									<f:type className="AipMode" value="${element.aip_mode.value}" />
								</td>
<!-- 								<td> -->
<%-- 									${element.aip_type.value} --%>
<!-- 								</td> -->
								<td>
									<f:type className="AipPeriod" value="${element.aip_periods.value}" />
								</td>
							    <td>${element.aip_amount.value}</td>
							    <td>${element.account_no.value}</td>
							    <td>${element.plan_stat.value}</td>
							    <td class="redlink">
							    	<c:if test="${element.plan_stat.value eq '��Ч'}">
							    		<a href="javascript:void(0)" onclick="oper('U', '${element.aip_no.value}','${element.order_no.value}')">���</a>
							    		<a href="javascript:void(0)" onclick="oper('P', '${element.aip_no.value}','${element.order_no.value}')">��ͣ</a>
							    	</c:if>
							    	<c:if test="${element.plan_stat.value eq '��ͣ'}">
							    		<a href="javascript:void(0)" onclick="oper('R', '${element.aip_no.value}','${element.order_no.value}')">�ָ�</a>
							    	</c:if>
							    	<c:if test="${element.plan_stat.value eq '��Ч' or element.plan_stat.value eq '��ͣ'}">
							    	<!-- 
							    		<a href="javascript:void(0)" onclick="oper('G'), '${element.aip_no.value}','${element.order_no.value}'">ʵ�����</a>
							    		<a href="javascript:void(0)" onclick="oper('M'), '${element.aip_no.value}','${element.order_no.value}'">�ֽ����</a>
							    	 -->	
							    		<a href="javascript:void(0)" onclick="oper('C', '${element.aip_no.value}','${element.order_no.value}')">ȡ��</a>
							    	</c:if>
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
