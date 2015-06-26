<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增角色</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/dtree/wtree.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("roleInfoForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/roleInfoAction.do?action=listRoleInfo";;
					gotoUrl(url);
				});
				$('#btnSubmit').click(function(){
					if(!FormUtils.hasSelected('limitIds')){
						alert('请选择权限点');
						return;
					}
					/*
					if($('#roleName').val != '' && $('#limitGroupId') != '' && FormUtils.hasSelected('limitIds')){
						FormUtils.submitFirstTokenForm();
					}*/
					
					FormUtils.submitFirstTokenForm();
				});
				$("#limitGroupId").change(function(){
					var limitGroupId = $('#limitGroupId').val();
					if(limitGroupId == ''||limitGroupId==null){return;}
					$('#limitTree').show().html(LOAD_IMAGE).load(CONTEXT_PATH + '/roleInfoAction.do?action=loadTree&limitGroupId='+limitGroupId, function() {
						SysStyle.setDataGridStyle();
					});
					
					//$('#limitTree').html($('#limitTree').html()+'<span class="filed_tipinfo">必选</span>');
				});
			});
		 	function change(){
		 		jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
				var length = value.length;
				for ( var i = 0; i < value.length; i++) {
					if (value.charCodeAt(i) > 127) {
						length++;
					}
				}
				return this.optional(element) || (length >= param);
				}, $.validator.format("长度不能小于{0}!"));
			 }
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="roleInfoAction.do?action=addRoleInfo" styleId="roleInfoForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">角色名称</td>
						    <td>
						    	<html:text property="roleName"  styleClass="{required:true}" maxlength="40" style="roleName"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					    <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<html:text property="remark"  maxlength="100"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">权限组</td>
						     <td>
						     	<html:select property="limitGroupId" styleId="limitGroupId" styleClass="{required:true}">
						     		<html:option value="">---请选择---</html:option>
						     		<html:options collection="limitGroupInfoCollections" property="limitGroupId" labelProperty="limitGroupName"/>
						     	</html:select>
						     	<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					    <tr>
					    	<td class="formlabel nes">权限节点</td>
					 		<td id="limitTree">
					 		
					 		</td>
					   </tr>
					   <tr>
						     <td colspan="2" align="center">
								 <input type="button" id="btnSubmit" value="提交"/>&nbsp;
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
	</html:form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
