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
		<title>�޸Ŀͻ�������Ϣ</title>
		
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
		 			alert('��������ȷ��ʽ���ֻ���');
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
                     alert("����ʧ��"+data.msg);
                 }       
			 });
		 	}
		 	var task;
		 	function disabledBtn(){
		 		var btn = $('#dymCodeBtn');
		 		btn.val('�ѷ���');
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
						    <td class="formlabel nes">�ֻ���</td>
						    <td>
						    	<html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true,minlength:11,maxlength:11}" maxlength="11" onblur="checkMobile()"/>
						    	<span class="field_tipinfo">��ȷ��ʽ���ֻ���</span>
						    	<span id="dymCodeSpan">
						    		<input type="button" value="��ȡ��̬��" onclick="getDymCode()" id="dymCodeBtn"/>
						    	</span>
						    </td>
					   </tr>
					   <tr id="dymTr" style="display: none">
						    <td class="formlabel nes">�ֻ���̬��</td>
						    <td>
						    	<html:text property="dymCode"  styleClass="{required:true}" maxlength="6" styleId="dymCode"/>
						    	<span class="field_tipinfo">����</span>
						    </td>
					   </tr>
					    <tr>
					    	<td class="formlabel">����</td>
						     <td>
								<html:text property="email"  styleClass="{email:true}" maxlength="30"/>
								<span class="field_tipinfo">����</span>
							</td>
					   </tr>
					   <tr>
					   		<td class="formlabel">������Ѷ</td>
						     <td>
						        <html:multibox property="subsPhone" value="Y" styleId="subsPhone"></html:multibox>�ֻ�����
						        <html:multibox property="subsEmail" value="Y" styleId="subsEmail"></html:multibox>���䶩��
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="�ύ " onclick="update(this.form)"/>
					 <input type="button" value="ȡ��" onclick="toHomePage()" />
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
