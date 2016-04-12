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

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doUpdate" id="ownerInfoActionForm" method="post" class="validate">
		<s:hidden name="id" id="id"/>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn"  id="houseSn" class="{required:true}" maxlength="10" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">业主姓名</td>
						    <td>
						    	<s:textfield name="ownerName" id="ownerName" class="{required:true}" maxlength="10" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">性别</td>
						    <td>
						    	<s:select name="gender" id="gender" list="#{'男':'男','女':'女'}" listKey="key" listValue="value" style="width:166px"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">证件号码</td>
						    <td>
						    <s:textfield name="idCard"  id="idCard"  maxlength="20"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">手机号</td>
						    <td>
						    <s:textfield name="mobile"  id="mobile" class="{required:true,digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">请输入正确的手机号</span>
						    </td>
					   </tr>
					   	<tr>
						    <td class="formlabel">邮箱</td>
						    <td>
						    <s:textfield name="email"  id="email" class="{email:true}" />
						    	<span class="field_tipinfo">请输入正确格式的邮箱</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">重要级别</td>
						    <td>
						    	<s:select name="grade" id="grade" list="#request.ownerGrades" listKey="value" listValue="name" style="width:166px"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">入住日期</td>
						    <td>
						    	<s:textfield name="checkinDate" id="checkinDate" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车辆数</td>
						    <td>
						    	<s:select list="#{'0','1','2','3','4'}" name="carNum" style="width:166px;" />
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" id="remark" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</form>	
</body>
</html>
