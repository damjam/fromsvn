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
		<title>����¿ͻ�</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		
		<script type="text/javascript">
			$(document).ready(function() {
				$('#bankno').keyup(function() {
					// ���п����Զ����ո�
					var t_bankno = $('#bankno').val().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");
					$('#bankno').val(t_bankno);
				});
			});
			function checkChange() {
				// ������ѡ���¼�
				var type = $('#cardType').val();
				if(type == "0") {
					$('#idCard').rules("remove");
					$('#idCard').rules("add", { required: true,idcard: true, messages: { required: "����ȷ�����������֤����"} });
				} else if(type == "1") {
					$('#idCard').rules("remove");
					$('#idCard').rules("add", { required: true,passport: true, messages: { required: "����ȷ�������Ļ���"} });
				}
			}
			
			function encrypt(){
				var loginPwdE = $('#loginPwdE').val();
				var confirmPwdE = $('#confirmPwdE').val();
		 		$('#loginPwd').val(hex_md5(loginPwdE));
		 		$('#confirmPwd').val(hex_md5(confirmPwdE));
			}
		 	function custInfoAdd(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	var task;
		 	function openAcct(form){
		 		encrypt();
		 		$(form).attr("action",CONTEXT_PATH+'/custInfoAction.do?action=toSign');
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	var rejectSubEmail = false;
		 	function checkEmail(){
		 		var email = $('#email').val();
		 		if(email != ''){
		 			if(!rejectSubEmail){
		 				$('#subsEmail').attr('checked', true);
		 			}
		 		} else{
		 			$('#subsEmail').attr('checked', false);
		 		}
		 	}
		 	function checkSubEmail(){
		 		if(!$('#subsEmail').attr('checked')){
		 			rejectSubEmail = true;
		 		} 
		 	}
		</script>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="custMngAction.do?action=doAdd" styleId="custInfoForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>����¿ͻ�</caption>
					  <tr>
						    <td class="formlabel nes">�ֻ���</td>
						    <td>
						    	<html:text property="mobile" styleClass="{required:true,digit:true,mobile:true,minlength:11,maxlength:11}" maxlength="11"/>
						    	<span class="field_tipinfo">��������Ч���ֻ���</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes">��ʵ����</td>
					   		<td>
					   			<html:text property="name" styleClass="required:true" maxlength="30" />
					   			<span class="field_tipinfo">��������ʵ����</span>
					   		</td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes">֤������</td>
					   		<td>
						    	<html:select property="cardType" styleId="cardType" onchange="checkChange()">
									<html:options collection="idCardTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes">֤����</td>
					   		<td>
					   			<html:text property="idCard" styleClass="{required:true,idcard:true}" maxlength="30" styleId="idCard" />
					   			<span class="field_tipinfo">����������Ч��֤����</span>
					   		</td>
					   </tr>
<%--					   <tr>--%>
<%--					   		<td class="formlabel nes">�����������п���</td>--%>
<%--					   		<td>--%>
<%--					   			<html:text property="bankno" styleClass="{required:true,bankno:true}" maxlength="30" styleId="bankno" />--%>
<%--					   			<span class="field_tipinfo">�������������п���</span>--%>
<%--					   		</td>--%>
<%--					   </tr>--%>
					   <tr>
							<td class="formlabel">ͨѶ��ַ</td>
							<td>
					   			<html:text property="addr" maxlength="100" />
					   			<span class="field_tipinfo">ͨѶ��ַ</span>
					   		</td>
						</tr>
					    <tr>
					    	<td class="formlabel">����</td>
						     <td>
								<html:text property="email" styleId="email" styleClass="{email:true}" maxlength="30" onblur="checkEmail()"/>
								<span class="field_tipinfo">��Ч������</span>
							</td>
					   </tr>
					   <tr>
					   		<td class="formlabel">������Ѷ</td>
						     <td>
						     	<input type="checkbox" name="subsPhone" value="Y" checked="checked" id="subsPhone"/>�ֻ�����
						     	<input type="checkbox" name="subsEmail" value="Y" id="subsEmail" onclick="checkSubEmail()"/>���䶩��
							</td>
					   </tr>
					   <tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="�ύ" onclick="custInfoAdd(this.form)"/>&nbsp;
								<input type="button" value="ȡ��" onclick="history.go(-1)"/>&nbsp;
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
