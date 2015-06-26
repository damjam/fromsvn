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
		<title>��ӿͻ���Ϣ</title>
		
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
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
		 	function register(){
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
			     url:CONTEXT_PATH + '/custInfoAction/sendDymCode.do',
			     async:false,
			     data:{mobile:mobile},
			     //dataType: 'json',
				 success:function(data) {
			    	 disabledBtn();
				 },
				 error:function(data){   
                     alert("����ʧ��"+data);
                     flag = 'error';
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
		 	function openAcct(form){
		 		encrypt();
		 		$(form).attr("action",CONTEXT_PATH+'/custInfoAction.do?action=toSign');
		 		FormUtils.submitFirstTokenForm();
		 	}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=doAddCust" styleId="custInfoForm" method="post" styleClass="validate">
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
						    	<html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true,minlength:11,maxlength:11}" maxlength="11"/>
						    	<span class="field_tipinfo">��ȷ��ʽ���ֻ���</span>
						    	<input type="button" value="��ȡ��̬��" onclick="getDymCode()" id="dymCodeBtn"/>
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
					    	<td class="formlabel">ͨѶ��ַ</td>
						     <td>
								<html:text property="addr" styleId="addr" maxlength="50"></html:text>
							</td>
					   </tr>
					    <tr>
					   		<td class="formlabel nes">��ʵ����</td>
					   		<td><html:text property="name"  styleClass="{required:true}" maxlength="30"/>
					   			<span class="field_tipinfo">����</span>
					   		</td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">֤������</td>
						    <td>
						    	<html:select property="cardType" styleClass="{required:true}" >
									<html:options collection="cardTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel nes">֤������</td>
					   		<td><html:text property="idCard"  styleClass="{required:true,stringNum:true,minlength:10}" maxlength="20"/>
					   			<span class="field_tipinfo">����</span>
					   		</td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">֧������</td>
						    <td>
						    	<html:select property="payChnlType" styleClass="{required:true}" >
									<html:options collection="payChnlTypes" labelProperty="value" property="key"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">֧���˺�/����</td>
						    <td>
						    	<html:text property="acctNo"  styleClass="{required:true,stringNum:true}" maxlength="32"/>
						    	<span class="field_tipinfo">��ĸ������</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">������Ѷ</td>
						     <td>
						     	<input type="checkbox" name="subsPhone" value="Y" checked="checked"/>�ֻ�����
						     	<input type="checkbox" name="subsPhone" value="Y" checked="checked"/>���䶩��
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="�ύ" onclick="save(this.form)"/>
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
