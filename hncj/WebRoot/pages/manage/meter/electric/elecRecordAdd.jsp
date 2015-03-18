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
// 		 	  for ( var i = 0; i < content.length; i++) {//汉字len要加1
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
					if(parseInt(curnum, 10) < parseInt(prenum, 10)){
						alert('本期读数不能小于上期读数');
						return;
					}
					$('#num').val(curnum-prenum);
				}
			}
			function getPreRecord(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				$.post(CONTEXT_PATH + '/elecRecord.do?action=getPreRecord&houseSn='+houseSn, function(data) {
					if (data != null) {
						var jsonObj = eval('(' + data + ')');
						$('#prenum').val(jsonObj.prenum);
						var preRecordDate = jsonObj.preRecordDate;
						if(preRecordDate != ''){
							$('#preRecordDate').val(jsonObj.preRecordDate);
						}
					}
					else {
						$('#prenum').val(0);
					}
				});
			}
			/*
			function getRecordDate(){
				var recordMonth = $('#recordMonth').val();
				if(recordMonth == '') {
					return;
				}
				$.post(CONTEXT_PATH + '/elecRecord.do?action=getRecordDate&recordMonth='+recordMonth, function(data) {
					if (data != null) {
						var jsonObj = eval('(' + data + ')');
						var preRecordDate = jsonObj.preRecordDate;
						var curRecordDate = jsonObj.curRecordDate;
						if(preRecordDate != ''){
							$('#preRecordDate').val(jsonObj.preRecordDate);
						}
						if(curRecordDate != ''){
							$('#curRecordDate').val(jsonObj.curRecordDate);
						}
					}
				});
			}*/
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="elecRecord.do?action=doAdd" styleId="elecRecordActionForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<html:text property="houseSn"  styleId="houseSn" styleClass="{required:true}" maxlength="10" onblur="getPreRecord()"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">用电月份</td>
						    <td>
						    	<html:text property="recordMonth" styleId="recordMonth" styleClass="{required:true}" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">上期抄表日期</td>
						    <td>
						    	<html:text property="preRecordDate" styleId="preRecordDate" styleClass="{required:true,digit:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <!--  -->
					   <tr>
						    <td class="formlabel nes">上期读数</td>
						    <td>
						    <html:text property="prenum"  styleId="prenum" styleClass="{required:true,digit:true}" maxlength="8"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr> 
					   <tr>
						    <td class="formlabel nes">本期抄表日期</td>
						    <td>
						    <html:text property="curRecordDate"  styleId="curRecordDate" styleClass="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker();"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">本期读数</td>
						    <td>
						    <html:text property="curnum"  styleId="curnum" styleClass="{required:true,digit:true}" maxlength="8" onblur="checkNum()"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">实际用量</td>
						    <td>
						    <html:text property="num"  styleId="num" styleClass="{required:true}" maxlength="25" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/elecRecord.do?action=list')"/>
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
