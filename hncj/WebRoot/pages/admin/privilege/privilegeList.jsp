<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>Ȩ�޹���ҳ��</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
			 $(function(){
				$("#btnQry").click(function(){
					$("#privilegeFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#privilegeFrm");
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/privilegeAction.do?action=toAddPage");
				});
				
			 });
			
			 function deletePrivilege(limitId){
				 var url="/privilegeAction.do?action=deletePrivilege&limitId="+limitId;
				 gotoUrl(url);
			 }
			 
			 function gotoPrivilegeResource(limitId){
				 var url="/privilegeResourceAction.do?action=listPrivilegeResource&limitId="+limitId;
				 gotoUrl(url);
			 }
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg"/>
		<html:form action="/privilegeAction.do?action=listPrivilege" styleId="privilegeFrm" >
			<div class="userbox">
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" >
						 <caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">Ȩ������</td>
							<td><html:text property="limitName"></html:text></td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="button" value="��ѯ"  id="btnQry"/>&nbsp;
								<input type="button" value="���"  id="btnClear"/>&nbsp;
								<input type="button" value="����"  id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
			
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>Ȩ��ID</td>
						    <td>Ȩ������</td>
						    <td>���ڵ�</td>
						    <td>�˵�(?)</td>
						    <td>˳��</td>
						    <td>����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr>
								<td>${element.limitId}</td>
							    <td>${element.limitName}</td>
							    <td>${element.parent}</td>
							    <td>${element.isMenu}</td>
							    <td>${element.menuOrder}</td>
							    <td align="center">
							       <span class="redlink">
							       		<html:link href="javascript:deletePrivilege('${element.limitId}')" styleId="hrefDelete">ɾ��</html:link>
							 	  		<html:link href="javascript:gotoPrivilegeResource('${element.limitId}')" styleId="hrefPrivilegeResource">��Դ����</html:link>
							 	   </span>
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