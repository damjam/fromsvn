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
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	
			function inputTextNum()
		 	{
		 	   var content=$("#content").val();
		 	   var len=content.length;
// 		 	  for ( var i = 0; i < content.length; i++) {//����lenҪ��1
// 					if (content.charCodeAt(i) > 127) {
// 						len++;
// 					}
// 				}
		 	  	$("#txtNumLen").html(len);
		 	}
			
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="houseInfo.do?action=doAdd" styleId="houseInfoActionForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">¥��</td>
						    <td>
						    	<html:select property="buildingNo" style="width:166px">
									<html:options collection="buildingNos" property="key" labelProperty="value" />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��Ԫ</td>
						    <td>
						    	<html:select property="unitNo" style="width:166px">
									<html:options collection="unitNos" property="key" labelProperty="value" />
								</html:select>
						    </td>
					   </tr>
					   
					   <tr>
						    <td class="formlabel nes">����</td>
						    <td>
						    	<html:text property="position" styleId="position" maxlength="4" styleClass="{required:true,digit:true,minlength:3}"></html:text>
						    	<span class="field_tipinfo">��������ȷ�ķ����</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">���</td>
						    <td>
						    	<html:text property="area" styleId="area" maxlength="6" styleClass="{required:true,num:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">��������</td>
						    <td>
						    	<html:text property="deliveryDate" styleId="deliveryDate" maxlength="8" readonly="true" onfocus="WdatePicker();"></html:text>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="����" onclick="save()"/>
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/houseInfo.do?action=list')"/>
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
