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
		
		<title></title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			var aipNo = "";
			function cancelAcct(acctNo, state){
				if(state == '1'){
					//����ѽ�Լ��ֱ��ִ������
					gotoUrl("/investAcctAction.do?action=cancelInvestAcct&acctNo="+acctNo+"&state="+state);
				}else if(state == '6'){
					//�������ʱ,����״̬���н�Լ������
					aipNo = acctNo;
					window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelInvestAcct&acctNo='+acctNo+"&state="+state,'','');
		 		 	$.blockUI({ message: $('#block'), css: { width: '275px' } }); 
					
				}
				gotoUrl("/investAcctAction.do?action=cancelInvestAcct&acctNo="+acctNo);
			}
			
			function cancelSign(acctNo, state){
				aipNo = acctNo;
				//if(confirm("�ڱ�������п���ǰ���ԭ���Ž�Լ���Ƿ����?")){
				window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelSign&acctNo='+acctNo+"&state="+state,'','');
		 		$.blockUI({ message: $('#block'), css: { width: '275px' } });
				//}
			}
			
			function modify(acctNo, state){
				aipNo = acctNo;
				if(state == '1'){
					if(window.confirm("���������˻�ǰ��Ե�ǰ�˻���Լ���Ƿ������")){
						window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelInvestAcct&acctNo='+acctNo+"&state="+state,'','');
		 		 		$.blockUI({ message: $('#block'), css: { width: '275px' } }); 
					}
				}else if(state == '6'){
					gotoUrl("/investAcctAction.do?action=toUpdateInvestAcct&acctNo="+acctNo);
				}
			}
			
			function toAdd(){
				gotoUrl("/investAcctAction.do?action=toAddSign");
			}
			
			function checkCancelSign(){
				alert(aipNo);
				gotoUrl("/investAcctAction.do?action=checkCancel&acctNo="+aipNo);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investAcctAction.do?action=list" styleId="dataForm">
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
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="signContracts" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="submit" value="��ѯ"/>&nbsp;
								<input type="button" value="����" onclick="FormUtils.reset(this.form)"/>&nbsp;
								<input type="button" value="����" onclick="toAdd()"/>
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
						    <td>ǩԼ�б�</td>
						    <td>����</td>
						    <td>ǩԼʱ��</td>
						    <td>״̬</td>
						    <td>����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>
									${element.aipNo}
								</td>
							    <td>
							   		��(��ǰ�ӿڲ�֧����ʾ�б�)
							    </td>
								<td>
									${element.accountNo}
								</td>
							    <td>
							   	 	${element.createTime}
							    </td>
							    <td>
							    	${element.acctState}
							    </td>
							    <td align="center">
							       <span class="redlink">
<%-- 							       <a href="javascript:void(0)" onclick="cancel('${element.aip_no.value}',' ${element.account_no.value}');">��Լ</a> --%>
<%-- 							 	   		<c:if test="${element.is_sign.value eq '0'}"> --%>
<%-- 							       			<a href="javascript:void(0)" onclick="cancel('${element.aip_no.value}');">ǩԼ</a> --%>
<%-- 							 	   		</c:if> --%>
							 	   		<c:if test="${element.state eq '1'}">
							 	   			<a href="javascript:void(0)" onclick="modify('${element.aipNo}', '${element.state}');">���������˻��� </a>
							       			<a href="javascript:void(0)" onclick="cancelAcct('${element.aipNo}', '${element.state}');">����</a>
							       			<a href="javascript:void(0)" onclick="cancelSign('${element.aipNo}', '${element.state}');">��Լ</a>
							 	   		</c:if>
							 	   		<c:if test="${element.state eq '6'}">
							 	   			<a href="javascript:void(0)" onclick="modify('${element.aipNo}', '${element.state}');">���������˻��� </a>
							 	   			<a href="javascript:void(0)" onclick="cancelAcct('${element.aipNo}', '${element.state}');">����</a>
							 	   			<a href="javascript:void(0)" onclick="addSign('${element.aipNo}', '${element.state}');">ǩԼ</a>
							 	   		</c:if>
							 	   </span>
							  </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
			</div> 
		</html:form>
		<div id="block" style="display:none; cursor: default;padding: 20px;"> 
	        <input type="button" value="��Լ�ɹ�" onclick="checkCancelSign()"/>&nbsp;&nbsp; 
	        <input type="button" value="��Լʧ��" onclick="checkCancelSign()"/> 
		</div>
	</body>
</html>
