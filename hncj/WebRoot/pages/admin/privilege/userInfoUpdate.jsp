<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>修改用户信息</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<script type="text/javascript">
			$(function(){
				
				$('#btnReturn').click(function(){
					var url="/userInfoAction.do?action=listUserInfo";
					gotoUrl(url);
				});
				
			});
		 
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg />
	<form action="userInfoAction.do?action=updateUserInfo" id="userInfoForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">登录名</td>
						    <td nowrap="nowrap">
						    	<s:textfield name="loginId"  id="loginId" class="{required:true,stringNum:true}" maxlength="20"/>
						    	<s:hidden name="userId" />
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">用户名</td>
						    <td nowrap="nowrap">
						    	<s:textfield name="userName"  class="{required:true,stringCheck:true}" maxlength="20"/>
						    	<span class="field_tipinfo">中文字、英文字母、数字和下划线</span>
						    </td>
					   </tr>
					   </table>
					     <div class="btnbox">
								 <input type="submit" id="btnSumit" value="提交"/>
								 <input type="button" id="btnReturn" value="返回"/>
						</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
