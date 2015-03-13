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
		<title>修改客户基本信息</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			function encrypt(){
				var loginPwdE = $('#loginPwdE').val();
				var confirmPwdE = $('#confirmPwdE').val();
		 		$('#loginPwd').val(hex_md5(loginPwdE));
		 		$('#confirmPwd').val(hex_md5(confirmPwdE));
			}
		 	function register(){
		 		encrypt();
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	function getDymCode(){
		 		var mobile = $('#mobile').val();
		 		var pattern = /^1\d{10}$/;
		 		if(mobile == ''){
		 			alert('请输入正确格式的手机号');
		 			return;
		 		}
		 		if(!pattern.test(mobile)){
		 			return;
		 		}
		 		$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/custInfoAction.do?action=sendDymCode',
			     async:false,
			     data:{mobile:mobile,operType:'U'},
			     dataType: 'json',
				 success:function(data) {
			    	 if(data.result == true){
			    		 disabledBtn();
			    	 }else{
			    		 alert(data.msg);
			    		 $('#mobile').focus();
			    	 }
				 },
				 error:function(data){   
                     alert("发送失败"+data.msg);
                 }       
			 });
		 	}
		 	var task;
		 	function disabledBtn(){
		 		var btn = $('#dymCodeBtn');
		 		btn.val('已发送');
		 		btn.attr('disabled', true);
		 		i = 5;
		 		task = setInterval(showLeftTime, 1000);
		 	}
		 	var i = 5;
		 	function ableBtn(){
		 		var btn = $('#dymCodeBtn');
		 		btn.attr('disabled', false);
		 		btn.val('重新获取');
		 	}
		 	function showLeftTime(){
		 		i--;
		 		if(i==0){
		 			ableBtn();
		 			clearInterval(task);
		 			return;
		 		}
		 		$('#dymCodeBtn').val(i+'秒后重新获取');
		 		
		 	}
		 	
		 	function checkMobile(){
		 		var oriMobile = $('#oriMobile').val();
		 		var mobile = $('#mobile').val();
		 		if(oriMobile !== mobile){
		 			$('#dymCodeSpan').show();
		 			$('#dymTr').show();
		 			$('#dymCode').attr('disabled', false);
		 		} else {
		 			$('#dymCodeSpan').hide();
		 			$('#dymTr').hide();
		 			$('#dymCode').attr('disabled', true);
		 		}
		 	}
		 	$().ready(function(){
		 		$('#dymCodeSpan').hide();
		 		$('#dymCode').attr('disabled', 'true');
		 	});
		 	function update(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=doUpdate" styleId="custInfoForm" method="post" styleClass="validate">
	<html:hidden property="oriMobile" styleId="oriMobile"/>
	<html:hidden property="oriEmail" styleId="oriEmail"/>
	<html:hidden property="id"/>
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
						    <td class="formlabel nes">手机号</td>
						    <td>
						    	<html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true,minlength:11,maxlength:11}" maxlength="11" onblur="checkMobile()"/>
						    	<span class="field_tipinfo">正确格式的手机号</span>
						    	<span id="dymCodeSpan">
						    		<input type="button" value="获取动态码" onclick="getDymCode()" id="dymCodeBtn"/>
						    	</span>
						    </td>
					   </tr>
					   <tr id="dymTr" style="display: none">
						    <td class="formlabel nes">手机动态码</td>
						    <td>
						    	<html:text property="dymCode"  styleClass="{required:true}" maxlength="6" styleId="dymCode"/>
						    	<span class="field_tipinfo">必填</span>
						    </td>
					   </tr>
					    <tr>
					    	<td class="formlabel">邮箱</td>
						     <td>
								<html:text property="email"  styleClass="{email:true}" maxlength="30"/>
								<span class="field_tipinfo">邮箱</span>
							</td>
					   </tr>
					   <tr>
					   		<td class="formlabel">订阅资讯</td>
						     <td>
						        <html:multibox property="subsPhone" value="Y" styleId="subsPhone"></html:multibox>手机订阅
						        <html:multibox property="subsEmail" value="Y" styleId="subsEmail"></html:multibox>邮箱订阅
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="提交 " onclick="update(this.form)"/>
					 <input type="button" value="取消" onclick="toHomePage()" />
				</div>
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
