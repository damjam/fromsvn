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
		<title>客户签约</title>
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
		 	function sign(form){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	function checkIdCard(){
		 		var idCard = $('#idCard').val();
		 		if(isNaN(idCard)){
		 			return;
		 		}
		 		
		 		var cardType = $('#cardType').val();
		 		if(cardType == '0'){
		 			var birthday = idCard.substr(6, 8);
		 			var isDate = true;
		 			$.ajax({
						 type:'POST',
					     url:CONTEXT_PATH + '/custInfoAction.do?action=checkDate',
					     async:false,
					     data:{date:birthday},
					     dataType: 'json',
						 success:function(data) {
					    	 isDate = data.isDate;
						 },
						 error:function(data){   
		                     alert("发送失败"+data.msg);
		                 }       
				    });
		 			if(!isDate){
		 				return;
		 			}
		 			var year = idCard.substr(0,4);
		 			var month = idCard.substr(4,2);
		 			var day = idCard.substr(6,2);
		 			
		 			$('#birthday').val(birthday);
		 		}
		 	}
		 	function checkAddr(){
		 		var addr = $('#addr').val();
		 	}
		</script>
		<style type="text/css">
			body{margin-left:260px;margin-right: 260px;}
		</style>
	</head>
<body>
<f:msg styleClass="msg"/>
	<html:form action="custInfoAction.do?action=verifySignInfo" method="post" styleClass="validate">
		<html:hidden property="id"/>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>签约信息</caption>
					  <tr>
					   		<td class="formlabel nes">真实姓名</td>
					   		<td><html:text property="name"  styleClass="{required:true}" maxlength="30"/>
					   			<span class="field_tipinfo">必填</span>
					   		</td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">证件类型</td>
						    <td>
						    	<html:select property="cardType" styleClass="{required:true}" styleId="cardType">
									<html:options collection="idCardTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
					    <tr>
					   		<td class="formlabel nes">证件号码</td>
					   		<td><html:text property="idCard"  styleClass="{required:true,stringNum:true,minlength:10}" maxlength="20" onblur="checkIdCard()" styleId="idCard"/>
					   			<span class="field_tipinfo">必填</span>
					   		</td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">出生日期</td>
						    <td>
						    	<html:text property="birthday" maxlength="8" readonly="true" onfocus="WdatePicker({endDate:'%y-%M-01', required:true})" styleId="birthday"/>
						    	<span class="field_tipinfo">必填</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">性别</td>
						    <td>
						    	<html:select property="sex" styleClass="{required:true}">
									<html:options collection="sexTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">支付渠道</td>
						    <td>
						    	<html:select property="payChnlType" styleClass="{required:true}" >
									<html:options collection="payChnlTypes" labelProperty="value" property="key"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">支付账号/卡号</td>
						    <td>
						    	<html:text property="payChnlNo"  styleClass="{required:true,stringNum:true}" maxlength="32"/>
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">开通业务类型</td>
						    <td>
						    	<html:select property="busiType" styleClass="{required:true}" >
									<html:options collection="busiTypes" labelProperty="value" property="key"  />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">是否自动延期</td>
						    <td>
						    	<html:select property="extend" styleClass="{required:true}" >
									<html:options collection="symbolTypes" labelProperty="name" property="value"  />
								</html:select>
						    </td>
					   </tr>
					    <tr>
					   		<td class="formlabel">地区编码</td>
						     <td>
								<html:select property="areaCode" styleClass="{required:true}" >
									<html:options collection="areaCodeTypes" labelProperty="name" property="value"  />
								</html:select>
							</td>
					   </tr>
					   <tr>
					    	<td class="formlabel">通讯地址</td>
						     <td>
								<html:text property="addr" styleId="addr" maxlength="50" onkeyup="checkAddr()"></html:text>
							</td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button"  value="确定" onclick="sign(this.form)"/>
					 <input type="button"  value="取消" onclick="toLoginPage()"/>
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
