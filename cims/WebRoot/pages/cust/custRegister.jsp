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
		<title>�ͻ�ע��</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		
		<script type="text/javascript">
			function encrypt(){
				var loginPwdE = $('#loginPwdE').val();
				if(loginPwdE.length < 6){
					alert('��������6λ');
					return false;
				}
				var confirmPwdE = $('#confirmPwdE').val();
				if(confirmPwdE.length < 6){
					alert('ȷ����������6λ');
					return false;
				}
		 		$('#loginPwd').val(hex_md5(loginPwdE));
		 		$('#confirmPwd').val(hex_md5(confirmPwdE));
		 		return true;
			}
		 	function register(){
		 		if($('#subsEmail').attr('checked')){
		 			var email = $('#email').val();
		 			if(email == ''){
		 				alert('����д�����ַ');
		 				return;
		 			}
		 		}
		 		if(!encrypt()){
		 			return false;
		 		}
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	function getDymCode(){
		 		var mobile = $('#mobile').val();
		 		var pattern = /^1\d{10}$/;
		 		if(mobile == ''){
		 			alert('��������ȷ��ʽ���ֻ���');
		 			return;
		 		}
		 		if(!pattern.test(mobile)){
		 			return;
		 		}
		 		$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/custInfoAction.do?action=sendDymCode',
			     async:true,
			     data:{mobile:mobile},
			     dataType: 'json',
				 success:function(data) {
			    	 if(data.result == true){
			    		 disabledBtn();
			    	 }else{
			    		 alert(data.msg);
			    	 }
				 },
				 error:function(data){   
                     alert("����ʧ��"+data.msg);
                 }       
			 });
		 	}
		 	var task;
		 	function disabledBtn(){
		 		var btn = $('#dymCodeBtn');
		 		btn.val('�ѷ���,10��������Ч');
		 		btn.attr('disabled', true);
		 		i = 5;
		 		task = setInterval(showLeftTime, 1000);
		 	}
		 	var i = 5;
		 	function ableBtn(){
		 		var btn = $('#dymCodeBtn');
		 		btn.attr('disabled', false);
		 		btn.val('���»�ȡ');
		 	}
		 	function showLeftTime(){
		 		i--;
		 		if(i==0){
		 			ableBtn();
		 			clearInterval(task);
		 			return;
		 		}
		 		$('#dymCodeBtn').val(i+'������»�ȡ');
		 		
		 	}
		 	function openAcct(form){
		 		if(!encrypt()){
		 			return false;
		 		}
		 		$(form).attr("action",CONTEXT_PATH+'/custInfoAction.do?action=toSign');
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	var rejectSubEmail = false;
		 	function checkEmail(){
		 		var email = $('#email').val();
		 		if(email != '' && !rejectSubEmail){
		 			$('#subsEmail').attr('checked', true);
		 		} else{
		 			$('#subsEmail').attr('checked', false);
		 		}
		 	}
		 	function checkSubEmail(){
		 		if(!$('#subsEmail').attr('checked')){
		 			rejectSubEmail = true;
		 		}
		 		var email = $('#email').val();
		 	}
		</script>
		<style type="text/css">
			body{margin-left:260px;margin-right: 260px;}
		</style>
	</head>
<body>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=doRegister" styleId="custInfoForm" method="post" styleClass="validate">
	<html:hidden property="loginPwd" styleId="loginPwd"/>
	<html:hidden property="confirmPwd" styleId="confirmPwd"/>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>ע���¿ͻ�(����${branchType.name})</caption>
					  <tr>
						    <td class="formlabel nes">�ֻ���</td>
						    <td>
						    	<html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true,minlength:11,maxlength:11}" maxlength="11"/>
						    	<span class="field_tipinfo">��ȷ��ʽ���ֻ���</span>
						    	<input type="button" value="��ȡ��̬��" onclick="getDymCode()" id="dymCodeBtn"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ֻ���̬��</td>
						    <td>
						    	<html:text property="dymCode"  styleClass="{required:true}" maxlength="6"/>
						    	<span class="field_tipinfo">��������ȷ���ֻ���̬��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��¼����</td>
						    <td>
						    	<input type="password" id="loginPwdE" styleClass="{required:true,minlength:6,maxlength:12,stringNum:true}" maxlength="12"/>
						    	<html:hidden property="loginPwd" styleId="loginPwd"/>
						    	<span class="field_tipinfo">����6λ��ĸ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ٴ�����</td>
						    <td>
						    	<input type="password" id="confirmPwdE" styleClass="{required:true,minlength:6,maxlength:12,stringNum:true}" maxlength="12"/>
						    	<span class="field_tipinfo">����6λ��ĸ������</span>
						    </td>
					   </tr>
					    <tr>
					    	<td class="formlabel">����</td>
						     <td>
								<html:text property="email" styleId="email" styleClass="{email:true}" maxlength="30" onblur="checkEmail()"/>
								<span class="field_tipinfo">��ȷ��ʽ������</span>
							</td>
					   </tr>
					   <tr>
					   		<td class="formlabel">������Ѷ</td>
						     <td>
						     	<input type="checkbox" name="subsPhone" value="Y" checked="checked" id="subsPhone"/>�ֻ�����
						     	<input type="checkbox" name="subsEmail" value="A" id="subsEmail" onclick="checkSubEmail()"/>���䶩��
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="���ע��" onclick="register(this.form)"/>
					 <input type="button"  value="��ͨҵ��" onclick="openAcct(this.form)"/>
					 <input type="button" value="ȡ��" onclick="toLoginPage()" />
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
