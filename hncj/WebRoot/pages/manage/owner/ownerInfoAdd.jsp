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
			function checkNum(){
				var prenum = $('#prenum').val();
				var curnum = $('#curnum').val();
				if(prenum != '' && curnum != ''){
					if(curnum < prenum){
						alert('���ڶ�������С�����ڶ���');
						return;
					}
					$('#num').val(curnum-prenum);
				}
			}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="ownerInfo.do?action=doAdd" styleId="ownerInfoActionForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">���ݱ��</td>
						    <td>
						    	<html:text property="houseSn"  styleId="houseSn" styleClass="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">ҵ������</td>
						    <td>
						    	<html:text property="ownerName" styleId="ownerName" styleClass="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�Ա�</td>
						    <td>
						    	<html:select property="gender" style="width:166px">
									<html:options collection="sexTypes" property="value" labelProperty="name" />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">֤������</td>
						    <td>
						    <html:text property="idCard"  styleId="idCard"  maxlength="20"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�ֻ���</td>
						    <td>
						    <html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">��������ȷ���ֻ���</span>
						    </td>
					   </tr>
					   	<tr>
						    <td class="formlabel">����</td>
						    <td>
						    <html:text property="email"  styleId="email" styleClass="{email:true}" />
						    	<span class="field_tipinfo">��������ȷ��ʽ������</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��Ҫ����</td>
						    <td>
						    	<html:select property="grade" style="width:166px">
									<html:options collection="ownerGrades" property="value" labelProperty="name" />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">��ס����</td>
						    <td>
						    	<html:text property="checkinDate" styleId="checkinDate" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">������</td>
						    <td>
						    	<html:select property="carNum" style="width:166px">
						    		<html:option value="0">0</html:option>
									<html:option value="1">1</html:option>
									<html:option value="2">2</html:option>
									<html:option value="3">3</html:option>
									<html:option value="4">4</html:option>
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">��ע</td>
						    <td>
						    	<html:text property="remark" styleId="remark" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="����" onclick="save()"/>
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/ownerInfo.do?action=list')"/>
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
