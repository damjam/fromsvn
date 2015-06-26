<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增权限</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("privilegeForm");
				});
				
				$('#btnReturn').click(function(){
					gotoUrl("/privilegeAction.do?action=listPrivs");
				});
				
				$("#imgSelectParent").click(function(){
					popUp.popUpPrivilege("parentId","parentName",null);
					return false;
				});
				
			});
		 
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg />
	<form action="/privilegeAction.do?action=addPrivilege" id="privilegeForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="40%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">权限ID</td>
						    <td>
						    	<s:textfield name="limitId"  id="limitId" class="{required:true,letter:true}" maxlength="20"/>
						    	<span class="field_tipinfo">字母</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">权限名称</td>
						    <td>
						    	<s:textfield name="limitName" id="limitNameId"  class="{required:true}" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">父亲节点</td>
						     <td>
								<s:textfield name="parentName" readonly="true" id="parentName"></s:textfield>
								<img src="${CONTEXT_PATH}/images/search.gif" alt="请选择" id="imgSelectParent"/>
								<s:hidden name="parent" id="parentId"  class="{required:true}" />
								<span class="field_tipinfo">不能为空</span>
								
							</td>
					   </tr>
					    <tr>
						     <td class="formlabel nes">菜单(?)</td>
						     <td>
								<s:select list="#{'Y':'是','N':否 }" name="isMenu" listKey="key" listValue="value"></s:select>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					    <tr>
						     <td class="formlabel nes">序号</td>
						     <td>
								<s:textfield name="menuOrder" id="menuOrderId"  class="{required:true,num:true}" />
								<span class="field_tipinfo">数字</span>
							</td>
					   </tr>
					     <tr>
						     
						     <td colspan="2" align="center">
								 <input type="submit" id="btnSumit" value="提交"/>
								 <input type="button" id="btnClear" value="清除"/>
								 <input type="button" id="btnReturn" value="返回"/>
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
	</form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
