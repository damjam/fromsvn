<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>
<html>
	<head>
		
		<title>������Ѷ�б�</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				$('#btnAdd').click(function(){
					var url="/pushMngAction.do?action=toAdd";
					gotoUrl(url);   
				});
			});
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/pushMngAction.do?action=list" styleClass="validate-tip" styleId="dataForm">
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
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker()"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker()"/>
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
						 	<td>���</td>
						    <td >����</td>
						    <td width="120px;" >����</td>
						    <td>�ͻ�����</td>
						    <td>��������</td>
						    <td>ҵ������</td>
						    <td>����״̬</td>
						    <td>��������</td>
						    <td>����״̬</td>
						    <td>����ʱ��</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.subject}</td>
								<td align="left" style="width:120px;word-wrap: break-word; word-break: normal;">${fn:substring(element.content,0, 50)}...</td>
								<td><f:type className="CustType" value="${element.custType}"/> </td>
								<td><f:type className="BranchType" value="${element.branchNo}"/> </td>
								<td><f:type className="BusiType" value="${element.busiType}"/> </td>
								<td><c:if test="${element.subsState eq 'Y'}">�Ѷ���</c:if> </td>
								<td><f:type className="PushType" value="${element.pushType}"/> </td>
								<td>
									<c:if test="${element.state eq 'Y'}">������</c:if>
									<c:if test="${element.state eq 'N'}">δ����</c:if>
								</td>
							    <td><bean:write name="element" property="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
