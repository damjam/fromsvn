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
		
		<title>�ͻ��б�</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#custInfoActionForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("custInfoActionForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/custMngAction.do?action=toAdd";
					gotoUrl(url);
				});
			});
			
			function detailedCustInfo(id){
				var url="/custMngAction.do?action=detail&id="+id;
				gotoUrl(url);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/custMngAction.do?action=list" styleId="custInfoActionForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								�ֻ���
							</td>
							<td>
								<html:text property="mobile" styleId="mobile"  />
							</td>
							<td class="formlabel">
								֤����
							</td>
							<td>
								<html:text property="idCard" styleId="idCard"  />
							</td>
							<td class="formlabel">����</td>
							<td>
								<html:text property="email" styleId="email"  />
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								�ͻ�����
							</td>
							<td>
								<html:select property="custType">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="custTypes" labelProperty="name" property="value"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" id="btnClear"/>&nbsp;
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
						    <td>�ֻ���</td>
						    <td>��ʵ����</td>
						    <td>�Ա�</td>
						    <td>֤������</td>
						    <td>֤������</td>
						    <td>�ͻ�����</td>
						    <td>ͨѶ��ַ</td>
						    <td>����</td>
						    <td>����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.mobile }</td>
							    <td>${element.name }</td>
								<td>
									<f:type className="SexType" value="${element.sex}"/>
								</td>
								<td>
									<f:type className="IdCardType" value="${element.cardType}"/>
								</td>
							    <td>${element.idCard }</td>
							    <td><f:type className="CustType" value="${element.custType}"/></td>
							    <td>${element.addr}</td>
							    <td>${element.email}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:void(0)" onclick="return detailedCustInfo('${element.id}');">�鿴��ϸ</a>
							 	   </span>
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
