<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增权限资源</title>
		
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
					FormUtils.reset("privilegeResourceForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/privilegeResourceAction.do?action=listPrivilegeResource&limitId="+$('#limitId').val();
					gotoUrl(url);
				});
				
				 
				
			});
		 
		</script>
	</head>
<body>

<f:msg/>
	<form action="privilegeResourceAction.do?action=doAdd" id="privilegeResourceForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td align="right">权限名称</td>
						    <td>
						    	<s:textfield name="limitName"  readonly="true" id="limitName"/>
						    	<s:hidden name="limitId" id="limitId"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes" align="right">URL</td>
						    <td>
						    	<s:textfield name="url"  class="{required:true,letter:true}" maxlength="200"/>
						    	<span class="field_tipinfo">字母</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes" align="right">Method</td>
						     <td>
								<s:textfield name="param" class="{required:true,letter:true}"></s:textfield>
								<span class="field_tipinfo">字母</span>
							</td>
					   </tr>
					    <tr>
					    	<td class="formlabel nes" align="right">是否入口</td>
						     <td >
								<s:select name="isEntry" class="{required:true}" list="#{'Y':'是','N':'否'}" listKey="key" listValue="value"/>
								<span class="field_tipinfo">不能为空</span>
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
