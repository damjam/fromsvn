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
		
		<title>用户列表</title>
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
				if(confirm("确认要删除吗?")){
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
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								登录名
							</td>
							<td>
								<html:text property="loginId" styleId="loginId"  />
							</td>
							<td class="formlabel">
								用户名称
							</td>
							<td>
								<html:text property="userName" styleId="userName" />
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="清除" id="btnClear"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>用户编号</td>
						    <td>登陆名</td>
						     <td>用户名</td>
						    <td>用户类型</td>
						    <td>所属机构</td>
						    <td>操作</td>
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
							       			<a href="javascript:void(0)" onclick="return updateUserInfo('${element.userId}');">修改</a>
								       		<a href="javascript:void(0)" onclick="return deleteUserInfo('${element.userId}');">删除</a>
								       		<a href="javascript:void(0)" onclick="return manageUserRole('${element.userId}');">用户角色管理</a>
							       		</c:if>
							       		<a href="javascript:void(0)" onclick="return resetPwd('${element.userId}');">密码重置</a>
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
