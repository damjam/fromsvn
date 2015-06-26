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
		<title>����Ȩ��</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("privilegeForm");
				});
				
				$('#btnReturn').click(function(){
					gotoUrl("/privilegeAction.do?action=listPrivilege");
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
<f:msg styleClass="msg"/>
	<html:form action="/privilegeAction.do?action=addPrivilege" styleId="privilegeForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">Ȩ��ID</td>
						    <td>
						    	<html:text property="limitId"  styleId="limitId" styleClass="{required:true,letter:true}" maxlength="20"/>
						    	<span class="field_tipinfo">��ĸ</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">Ȩ������</td>
						    <td>
						    	<html:text property="limitName" styleId="limitNameId"  styleClass="{required:true}" />
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">���׽ڵ�</td>
						     <td>
								<html:text property="parentName" readonly="true" styleId="parentName"></html:text>
								<img src="${CONTEXT_PATH}/images/search.gif" alt="��ѡ��" id="imgSelectParent"/>
								<html:hidden property="parent" styleId="parentId"  styleClass="{required:true}" />
								<span class="field_tipinfo">����Ϊ��</span>
								
							</td>
					   </tr>
					    <tr>
						     <td class="formlabel nes">�˵�(?)</td>
						     <td>
								<html:select property="isMenu" styleClass="{required:true}">
									<html:option value="N">��</html:option>
									<html:option value="Y">��</html:option>
								</html:select>
								<span class="field_tipinfo">����Ϊ��</span>
							</td>
					   </tr>
					    <tr>
						     <td class="formlabel nes">���</td>
						     <td>
								<html:text property="menuOrder" styleId="menuOrderId"  styleClass="{required:true,num:true}" />
								<span class="field_tipinfo">����</span>
							</td>
					   </tr>
					     <tr>
						     
						     <td colspan="2" align="center">
								 <input type="submit" id="btnSumit" value="�ύ"/>
								 <input type="button" id="btnClear" value="���"/>
								 <input type="button" id="btnReturn" value="����"/>
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
	<!--��Ȩ����-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
