<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
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
					if(curnum < prenum){
						alert('本期读数不能小于上期读数');
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
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<html:text property="houseSn"  styleId="houseSn" styleClass="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">业主姓名</td>
						    <td>
						    	<html:text property="ownerName" styleId="ownerName" styleClass="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">性别</td>
						    <td>
						    	<html:select property="gender" style="width:166px">
									<html:options collection="sexTypes" property="value" labelProperty="name" />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">证件号码</td>
						    <td>
						    <html:text property="idCard"  styleId="idCard"  maxlength="20"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">手机号</td>
						    <td>
						    <html:text property="mobile"  styleId="mobile" styleClass="{required:true,digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">请输入正确的手机号</span>
						    </td>
					   </tr>
					   	<tr>
						    <td class="formlabel">邮箱</td>
						    <td>
						    <html:text property="email"  styleId="email" styleClass="{email:true}" />
						    	<span class="field_tipinfo">请输入正确格式的邮箱</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">重要级别</td>
						    <td>
						    	<html:select property="grade" style="width:166px">
									<html:options collection="ownerGrades" property="value" labelProperty="name" />
								</html:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">入住日期</td>
						    <td>
						    	<html:text property="checkinDate" styleId="checkinDate" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车辆数</td>
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
						    <td class="formlabel">备注</td>
						    <td>
						    	<html:text property="remark" styleId="remark" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/ownerInfo.do?action=list')"/>
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
