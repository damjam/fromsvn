<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
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
			
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="parkingInfo.do?action=doAdd" styleId="parkingInfoActionForm" method="post" styleClass="validate">
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
						    <td class="formlabel nes">车位编号</td>
						    <td>
						    	<html:text property="sn" styleId="sn" maxlength="10" readonly="true"></html:text>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车位产权人</td>
						    <td>
						    	<html:text property="ownerName" styleId="ownerName" maxlength="10"></html:text>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">产权人电话</td>
						    <td>
						    	<html:text property="ownerCel" styleId="ownerCel" maxlength="10"></html:text>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车位使用人</td>
						    <td>
						    	<html:text property="endUser" styleId="endUser" maxlength="10"></html:text>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">使用人电话</td>
						    <td>
						    	<html:text property="endUserCel" styleId="endUserCel" maxlength="10"></html:text>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车位状态</td>
						    <td>
						    	<html:select property="state" style="width:166px">
									<html:options collection="parkingStates" property="value" labelProperty="name" />
								</html:select>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/parkingInfo.do?action=list')"/>
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