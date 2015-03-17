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
		<title>�����û���Ϣ</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
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
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="userInfoAction.do?action=addUserInfo" styleId="userInfoForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">��½��</td>
						    <td>
						    	<html:text property="loginId"  styleId="loginId" styleClass="{required:true,stringNum:true}" maxlength="20"/>
						    	<span class="field_tipinfo">��ĸ������</span>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">�û���</td>
						    <td nowrap="nowrap">
						    	<html:text property="userName"  styleClass="{required:true,stringCheck:true}" maxlength="20"/>
						    	<span class="field_tipinfo">�����֡�Ӣ����ĸ�����ֺ��»���</span>
						    </td>
					   </tr>
					   <tr>
					    	<td class="formlabel nes">�û�����</td>
						     <td>
								<html:select property="userType" styleClass="{required:true}" >
									<html:options collection="userTypes" labelProperty="value" property="key"  />
								</html:select>
								<span class="field_tipinfo">����Ϊ��</span>
							</td>
					   </tr>
					   <tr>
					    	<td class="formlabel nes">��������</td>
						     <td>
								<html:select property="branchNo" styleClass="{required:true}" >
									<html:option value="">---��ѡ��---</html:option>
									<html:options collection="branchTypes" labelProperty="value" property="key"  />
								</html:select>
								<span class="field_tipinfo">����Ϊ��</span>
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="�ύ" onclick="save()"/>
					 <input type="button" id="btnClear" value="���"/>
					 <input type="button" id="btnReturn" value="����"/>
				</div>
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
