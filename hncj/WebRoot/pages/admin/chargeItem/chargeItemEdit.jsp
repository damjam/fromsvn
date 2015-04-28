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
			function getOwnerInfo(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/parkingBill.do?action=getOwnerName',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 if(data.error){
				    		 alert(data.error);
				    		 $('#houseSn').val('');
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 var ownerCel = data.mobile;
				    	 $('#ownerName').val(ownerName);
				    	 $('#ownerCel').val(ownerCel);
					 },
					 error:function(data){   
	                     alert("���ӷ�����ʧ��");
	                 }   
				});
			}
			$(document).ready(function(){
				var height = document.body.scrollHeight;
				parent.adjustHeight(height, 0);
			});
			$(document).ready(function(){
				changeWay($('#way').val());
			});
			function changeWay(way){
				if(way == '00'){
					$('.unit').show();
					$('.seg').hide();
					$('.step').hide();
				}else if(way == '01'){
					$('.unit').hide();
					$('.seg').show();
					$('.step').hide();
				}else if(way == '02'){
					$('.unit').hide();
					$('.seg').hide();
					$('.step').show();
				}
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="chargeItem.do?action=doEdit" method="post" styleClass="validate">
		<html:hidden property="id" />
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
						    <td class="formlabel nes">�Ʒ�������</td>
						    <td>
						    	<html:text property="itemName" styleId="itemName" maxlength="10" styleClass="{required:true}"></html:text>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�Ʒ���Ŀ</td>
						    	<td>
						    	<html:select property="item">
						    		<html:options collection="chargeTypes" property="value" labelProperty="name" />
						    	</html:select>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�Ʒѷ�ʽ</td>
						    <td>
						    	<html:select property="way" onchange="changeWay(this.value);" styleId="way">
						    		<html:options collection="chargeWays" property="value" labelProperty="name" />
						    	</html:select>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr class="unit">
						    <td class="formlabel nes">�Ʒѵ���</td>
						    <td>
						    	<html:text property="unitPrice" styleId="unitPrice" maxlength="10" styleClass="{num:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">�𲽼�</td>
						    <td>
						    	<html:text property="basePrice" styleId="basePrice" maxlength="10" styleClass="{num:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">��¥��</td>
						    <td>
						    	<html:text property="baseFloor" styleId="baseFloor" maxlength="10" styleClass="{digit:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">������</td>
						    <td>
						    	<html:text property="stepPrice" styleId="stepPrice" maxlength="10" styleClass="{num:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr class="step">
						    <td class="formlabel nes">�ⶥ��</td>
						    <td>
						    	<html:text property="capPrice" styleId="capPrice" maxlength="10" styleClass="{num:true}"></html:text>
						    	<span class="field_tipinfo">��������ȷ������</span>
						    </td>
					   </tr>
					   <tr class="seg">
						    <td class="formlabel nes">�ֶμ۸�</td>
						    <td>
						    	<html:text property="segRule" styleId="segRule" maxlength="30"></html:text>
						    	<span class="field_tipinfo">��ʽ��:1-18:10;19-32:20</span>
						    	<span class="error_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
					   		<td class="formlabel">��ע</td>
						    <td>
						    	<html:text property="remark" styleId="remark" maxlength="25"></html:text>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="����" onclick="save()"/>
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/chargeItem.do?action=list')"/>
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
