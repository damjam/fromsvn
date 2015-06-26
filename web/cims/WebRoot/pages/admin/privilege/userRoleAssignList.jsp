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
		
		<title>�û���ɫ����</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		 
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#userInfoForm').submit();
				});
				
				$('#btnReturn').click(function(){
					var url="/userInfoAction.do?action=listUserInfo";
					gotoUrl(url);   
				});
			});
			
			function assignRole(){
				FormUtils.submitFirstTokenForm();
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/userRoleAction.do?action=assignRole" styleId="userRoleForm">
			<!-- ��ѯ������ -->
			
			<!-- �����б��� -->
			<div class="tablebox">	
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td><input type="checkbox" onclick="FormUtils.selectAll(this, 'roleIds')" />ȫѡ</th></td>
						    <td>��ɫ���</td>
						    <td>��ɫ����</td>
						    <td>Ȩ������</td>
						    <td>Ȩ��������</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td><html:multibox property="roleIds" value="${element.roleId}"/></td>
								<td>${element.roleId}</td>
							    <td>${element.roleName}</td>
							    <td>${element.limitGroupId}</td>
							    <td>${element.limitGroupName}</td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
			<html:hidden property="userId" styleId="userId"/>
		</html:form>
		<dir class="btn_box"><input type="button" value="�ύ" onclick="assignRole()"/>&nbsp;
			<input type="button" value="����" id="btnReturn"/> 
		</dir>
	</body>
</html>
