<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>

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
			//修改密码
			function changePassword(){
				var oldLoginPwd = $('#oldLoginPwd').val();
				var flag = true;
				if(oldLoginPwd.length<6){
					$('.error_tipinfo:eq(0)').html('输入旧密码').show();
					$('.field_tipinfo:eq(0)').hide();
					flag = false;
				}
				var loginPwd = $('#loginPwd').val();
				if(loginPwd.length<6){
					$('.error_tipinfo:eq(1)').html('至少6位，且不能与旧密码相同').show();
					$('.field_tipinfo:eq(1)').hide();
					flag = false;
				}else if(loginPwd != '' && oldLoginPwd != '' && loginPwd == oldLoginPwd){
					$('.error_tipinfo:eq(1)').html('新密码不能与原密码相同').css("color","#333").show();
					$('.field_tipinfo:eq(1)').hide();
					flag = false;
				}
				var confirmPwd = $('#confirmPwd').val();
				if(confirmPwd.length<6){
					$('.error_tipinfo:eq(2)').html('至少6位').show();
					$('.field_tipinfo:eq(2)').hide();
					flag = false;
				}else if(loginPwd != confirmPwd){
					$('.error_tipinfo:eq(2)').html('请与新密码一致').show();
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
						alert('新密码不能与旧密码相同');
						return;
					}
					if(loginPwd != confirmPwd){
						alert('新密码与确认密码不一致');
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
		<f:msg />
		<form action="userInfoAction.do?action=changePassword" id="userInfoForm" class="validate" method="post">
		<div class="userbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
			<table class="form_grid">
				<tr>
					<td class="formlabel nes">旧密码</td>
					<td><s:password name="oldLoginPwd" class="userbox_bt {required:true,minlength:6}" id="oldLoginPwd"/>
						<span class="field_tipinfo">输入旧密码</span>
						<span class="error_tipinfo">输入旧密码</span>
					</td>
				</tr>
				<tr>
					<td class="formlabel nes">新密码</td>
					<td><s:password name="loginPwd" id="loginPwd" class="userbox_bt {required:true,minlength:6}"/>
					<span class="field_tipinfo">至少6位，且不能与旧密码相同</span>
					<span class="error_tipinfo">至少6位，且不能与旧密码相同</span>
					</td>
				</tr>
				<tr>
					<td class="formlabel nes">确认新密码</td>
					<td><s:password class="{required:true,minlength:6,equalTo:'#loginPwd'}" name="confirmPwd" id="confirmPwd"/>
					<span class="field_tipinfo">请与新密码一致</span>
					<span class="error_tipinfo">请与新密码一致</span>
					</td>
				</tr>
				<tr align="center">
				    <td></td>
					<td align="left">
					<input type="button" onclick="changePassword()" value="提交"/>&emsp;
					<input type="button" onclick="toHomePage();" value="取消"/>&emsp;
					</td>
				</tr>
			</table>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</form>
	</body>
</html>
