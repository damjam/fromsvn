<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html>
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/validate.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		
		<script type="text/javascript">
			//�޸�����
			function changePassword(){
				var oldLoginPwd = $('#oldLoginPwd').val();
				var flag = true;
				if(oldLoginPwd.length<6){
					$('.error_tipinfo:eq(0)').html('���������').show();
					$('.field_tipinfo:eq(0)').hide();
					flag = false;
				}
				var loginPwd = $('#loginPwd').val();
				if(loginPwd.length<6){
					$('.error_tipinfo:eq(1)').html('����6λ���Ҳ������������ͬ').show();
					$('.field_tipinfo:eq(1)').hide();
					flag = false;
				}else if(loginPwd != '' && oldLoginPwd != '' && loginPwd == oldLoginPwd){
					$('.error_tipinfo:eq(1)').html('�����벻����ԭ������ͬ').css("color","#333").show();
					$('.field_tipinfo:eq(1)').hide();
					flag = false;
				}
				var confirmPwd = $('#confirmPwd').val();
				if(confirmPwd.length<6){
					$('.error_tipinfo:eq(2)').html('����6λ').show();
					$('.field_tipinfo:eq(2)').hide();
					flag = false;
				}else if(loginPwd != confirmPwd){
					$('.error_tipinfo:eq(2)').html('����������һ��').show();
					$('.field_tipinfo:eq(2)').hide();
					flag = false;
				}
				
				if(flag){
					var userInfoForm = document.getElementById('userInfoForm');
					userInfoForm.oldLoginPwd.value = hex_md5(userInfoForm.oldLoginPwd.value);
					userInfoForm.loginPwd.value = hex_md5(userInfoForm.loginPwd.value);
					userInfoForm.confirmPwd.value = hex_md5(userInfoForm.confirmPwd.value);
					userInfoForm.submit();	
				}
			}
			function save(){
				var oldLoginPwd = $('#oldLoginPwd').val();
				var loginPwd = $('#loginPwd').val();
				var confirmPwd = $('#confirmPwd').val();
				if(oldLoginPwd.length >= 6 && loginPwd.length >= 6 && confirmPwd.length >= 6){
					if(oldLoginPwd == loginPwd){
						alert('�����벻�����������ͬ');
						return;
					}
					if(loginPwd != confirmPwd){
						alert('��������ȷ�����벻һ��');
						return;
					}
				}
				$('#oldLoginPwd').val(hex_md5(oldLoginPwd));
				$('#loginPwd').val(hex_md5(loginPwd));
				$('#confirmPwd').val(hex_md5(confirmPwd));
				FormUtils.submitFirstTokenForm();
			}
			$(document).ready(function(){
			  	$('#oldLoginPwd').val('');
			  	$('#loginPwd').val('');
			  	$('#confirmPwd').val('');
			});
		</script>
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg"/>
		<html:form action="/userInfoAction.do?action=changePassword" styleId="userInfoForm" styleClass="validate">
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			<table class="form_grid">
				<tr>
					<td class="formlabel nes">������</td>
					<td><html:password property="oldLoginPwd" styleClass="userbox_bt {required:true,minlength:6}" styleId="oldLoginPwd"/>
						<span class="field_tipinfo">���������</span>
						<span class="error_tipinfo">���������</span>
					</td>
				</tr>
				<tr>
					<td class="formlabel nes">������</td>
					<td><html:password property="loginPwd" styleId="loginPwd" styleClass="userbox_bt {required:true,minlength:6}"/>
					<span class="field_tipinfo">����6λ���Ҳ������������ͬ</span>
					<span class="error_tipinfo">����6λ���Ҳ������������ͬ</span>
					</td>
				</tr>
				<tr>
					<td class="formlabel nes">ȷ��������</td>
					<td><html:password styleClass="{required:true,minlength:6,equalTo:'#loginPwd'}" property="confirmPwd" styleId="confirmPwd"/>
					<span class="field_tipinfo">����������һ��</span>
					<span class="error_tipinfo">����������һ��</span>
					</td>
				</tr>
				<tr align="center">
				    <td></td>
					<td align="left">
					<input type="button" onclick="changePassword()" value="�ύ"/>&emsp;
					<input type="button" onclick="toHomePage();" value="ȡ��"/>&emsp;
					</td>
				</tr>
			</table>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</html:form>
	</body>
</html>
