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
		
		<title>�û��б�</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#userInfoForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("userInfoForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/userInfoAction.do?action=toAddPage";
					gotoUrl(url);   
				});
			});
			
			function updateUserInfo(userId){
				var url="/userInfoAction.do?action=toUpdatePage&userId="+userId;
				gotoUrl(url);
			}
			
			function deleteUserInfo(userId){
				if(confirm("ȷ��Ҫɾ����?")){
					var url="/userInfoAction.do?action=deleteUserInfo&userId="+userId;
					gotoUrl(url);
				}else{
					return false;
				}
			}
			
			function manageUserRole(userId){
				var url="/userRoleAction.do?action=toAssignRole&userId="+userId;
				gotoUrl(url);
			}
			function resetPwd(userId){
				var url = '/userInfoAction.do?action=resetPwd&userId='+userId;
				gotoUrl(url);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/userInfoAction.do?action=listUserInfo" styleId="userInfoForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								��¼��
							</td>
							<td>
								<html:text property="loginId" styleId="loginId"  />
							</td>
							<td class="formlabel">
								�û�����
							</td>
							<td>
								<html:text property="userName" styleId="userName" />
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="���" id="btnClear"/>&nbsp;
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
						    <td>�û����</td>
						    <td>��½��</td>
						     <td>�û���</td>
						    <td>�û�����</td>
						    <td>��������</td>
						    <td>����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.userId}</td>
							    <td>${element.loginId}</td>
							    <td>${element.userName}</td>
							    <td>
							    	${element.userTypeName}
							    </td>
							    <td>${element.branchName}</td>
							    <td align="center">
							       <span class="redlink">
							       		<c:if test="${element.loginId ne 'super'}">
							       			<a href="javascript:void(0)" onclick="return updateUserInfo('${element.userId}');">�޸�</a>
								       		<a href="javascript:void(0)" onclick="return deleteUserInfo('${element.userId}');">ɾ��</a>
								       		<a href="javascript:void(0)" onclick="return manageUserRole('${element.userId}');">�û���ɫ����</a>
							       		</c:if>
							       		<a href="javascript:void(0)" onclick="return resetPwd('${element.userId}');">��������</a>
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
