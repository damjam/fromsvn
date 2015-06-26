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
		<title>����������Ϣ</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	function changePushType(){
		 		/*
		 		var pushType = $('#pushType').val();
		 		if(pushType == '0'){
		 			$('#msg').show();
		 			$('#msg').attr('disabled', false);
		 			$('#email').hide();
		 			$('#email').attr('disabled', true);
		 		}else{
		 			$('#msg').hide();
		 			$('#msg').attr('disabled', true);
		 			$('#email').show();
		 			$('#email').attr('disabled', false);
		 		}
		 		*/
		 	}
		 	$().ready(function(){
		 		changePushType();
		 		changeBranch();
		 	});
		 	//�䶯����
		 	function changeBranch(){
		 		var branchNo = '${BRANCH_TYPE.value}';
		 		//ֻ�����ڽ��ڵ��ӽ������ĲŽ��л���ѡ��
		 		if(branchNo != '0000'){
		 			return;
		 		}
		 		var params = 'branchNo='+branchNo;
		 		$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/pushMngAction.do?action=getBusiType',
				     async:true,
				     dataType: "json",
				     data:params,
					 success:function(data) {
				    	var jsonObj = data;
				    	$('#busiType').empty();
				    	$("#busiType").append("<option value=''>����</option>");
				    	for(var i in jsonObj){
							$("#busiType").append("<option value='"+i+"'>"+jsonObj[i]+"</option>");
						}
					 },
					 error:function(data){   
	                     alert("���ӷ�����ʧ��");
	                 }   
				});
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
	<html:form action="pushMngAction.do?action=doAdd" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					  <c:if test="${BRANCH_TYPE.value eq '0000'}">
					    <tr>
						    <td class="formlabel nes">�ͻ�����</td>
						    <td>
						    	<html:select property="custType"  styleId="custType">
									<html:option value="">����</html:option>
									<html:options collection="custTypes" labelProperty="name" property="value"/>
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">��������</td>
						    <td>
						    	<html:select property="branchNo"  styleId="branchNo" onchange="changeBranch();">
									<html:option value="">����</html:option>
									<html:options collection="branchTypes" labelProperty="value" property="key"/>
								</html:select>
						    </td>
					   </tr>
					  </c:if> 
					   <tr>
						    <td class="formlabel nes">ҵ������</td>
						    <td>
						    	<html:select property="busiType"  styleId="busiType">
									<html:option value="">����</html:option>
									<html:options collection="busiTypes" labelProperty="value" property="key"/>
								</html:select>
						    </td>
					   </tr>
					    <tr>
						    <td class="formlabel nes">����״̬</td>
						    <td>
						    	<html:select property="subsState"  styleId="subsState">
									<html:option value="">����</html:option>
									<html:options collection="subsStates" labelProperty="value" property="key"/>
								</html:select>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">���ͷ�ʽ</td>
						    <td>
						    	<html:select property="pushType"  styleId="pushType" onchange="changePushType()">
									<html:options collection="pushTypes" labelProperty="name" property="value"/>
								</html:select>
						    </td>
					   </tr>
					  <tr id="subTr">
						    <td class="formlabel nes">����</td>
						    <td>
						    	<html:text property="subject"  styleId="subject" styleClass="{required:true}" maxlength="25"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">����</td>
						    <td>
						    	<div id="txtNum">��ǰ���������Ϊ��<span id="txtNumLen" style="color: red;">0</span></div>
						    	<html:textarea property="content"  styleId="content" styleClass="{required:true,maxlength:1000}" onkeyup="inputTextNum();" onblur="inputTextNum();" rows="20" cols="50"/>
						    	<span class="field_tipinfo">����Ϊ��,1000������</span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="�ύ" onclick="save()"/>
					 <input type="button" id="btnReturn" value="ȡ��" onclick="gotoUrl('/pushMngAction.do?action=list')"/>
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
