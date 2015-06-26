<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			var parent = '${param.parent}';
			function goBack(){
				//gotoUrl('/privilegeAction.do?action=listPrivs&parent='+);
			}
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg"/>
		<html:form action="/privilegeAction.do?action=listPrivs" styleId="searchForm">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0" style="line-height:30px">
						<tr>
							<td class="formlabel" align="left">Ȩ��ID</td>
							<td><html:text property="limitId" maxlength="50"/></td>
							<td class="formlabel" align="left">
								Ȩ������
							</td>
							<td>
								<html:text property="limitName" styleId="limitName" styleClass="userbox_bt"/>
							</td>
							<td class="formlabel" align="left">
								����Ȩ��ID
							</td>
							<td>
								<html:text property="parent" styleId="parent" styleClass="userbox_bt"/>
							</td>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5" align="left">							
							<input type="submit" value="��ѯ" id="query" name="ok" />
							<input style="margin-left:20px;" type="button" value="����"  onclick="FormUtils.reset('searchForm')" />&nbsp;&nbsp;
							${privilege.parent}
							<c:if test="${!empty privilege.parent}">
								<input type="button" onclick="javascript:history.go(-1)" value="����"/>
							</c:if>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
		</div>
		<!-- �����б��� -->
		<div class="tablebox">
			<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					 <tr align="center" class="titlebg">
					    <td>Ȩ��ID</td>
					    <td>Ȩ������</td>
					    <td>����Ȩ��ID</td>
					    <td>�Ƿ�˵�</td>
					    <td>����</td>
					 </tr>
				</thead>
				
				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<logic:iterate id="element" name="list">
						<tr align="center">
							<td>${element.limitId}</td>
							<td>${element.limitName}</td>
							<td>${element.parent}</td>
							<td>
								<c:if test="${element.isMenu eq 'Y'}">��</c:if>
								<c:if test="${element.isMenu eq 'N'}">��</c:if>
							</td>
							<td class="redlink">
								<c:if test="${element.hasSubPris eq 'Y'}">
									<html:link action="/privilegeAction.do?action=listPrivs&parent=${element.limitId}">��Ȩ��</html:link>
								</c:if>
								
								<html:link action="/privilegeAction.do?action=toEditPrivilege&parent=${element.limitId}">�����Ȩ��</html:link>
								<c:if test="${!(element.hasSubPris eq 'Y')}">
									<html:link action="/privilegeAction.do?action=listPrivRes&limitId=${element.limitId}">Ȩ����Դ</html:link>
									<html:link action="/privilegeAction.do?action=toEditPriRes&limitId=${element.limitId}">���Ȩ����Դ</html:link>
								</c:if>
								<!-- 
								<html:link action="/privilegeAction.do?action=toEditPrivilege&limitId=${element.limitId}">�޸�</html:link>
								<c:if test="${element.hasSubPris ne 'Y'}">
									<html:link action="/privilegeAction.do?action=delPri&limitId=${element.limitId}">ɾ��</html:link>
								</c:if> -->
							</td>
						</tr>
					</logic:iterate>
				</f:showDataGrid>
			</table>
			<f:paginate/>
		</div>
		</html:form>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>