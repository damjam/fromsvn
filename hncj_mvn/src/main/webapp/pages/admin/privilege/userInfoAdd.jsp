<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增用户信息</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("userInfoForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/userInfoAction.do?action=listUserInfo";
					gotoUrl(url);
				});
				
			});
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		</script>
	</head>
<body>

<f:msg />
	<form action="userInfoAction.do?action=addUserInfo" id="userInfoForm" method="post" class="validate">
		<s:token/>
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">登录名</td>
						    <td>
						    	<s:textfield name="loginId" id="loginId" class="{required:true,stringNum:true}" maxlength="20"></s:textfield>
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">用户名</td>
						    <td nowrap="nowrap">
						    	<s:textfield name="userName" class="{required:true,stringCheck:true}" maxlength="20"></s:textfield>
						    	<span class="field_tipinfo">中文字、英文字母、数字和下划线</span>
						    </td>
					   </tr>
					   <tr>
					    	<td class="formlabel nes">用户类型</td>
						     <td>
								<s:select id="userType" name="userType" list="#request.userTypes" listKey="key" listValue="value" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					   <tr>
					    	<td class="formlabel nes">机构</td>
						     <td>
								<s:select id="branchNo" name="branchNo" list="#request.branches" listKey="key" listValue="value" cssClass="{required:true}"></s:select>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					   <tr>
					   		<td></td>
					   		<td>
					   			<input type="button" id="btnSumit" value="提交" onclick="save()"/>
								 <input type="button" id="btnReturn" value="返回"/>
					   		</td>
					   </tr>
				  </table>
			</div>
		</div>	
	</form>	
</body>
</html>
