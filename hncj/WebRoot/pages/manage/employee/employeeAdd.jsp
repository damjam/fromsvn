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
		 	
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
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
						    <td class="formlabel nes">姓名</td>
						    <td>
						    	<s:textfield name="name" id="name" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">性别</td>
						    <td>
						    	<s:select name="gender" id="gender" list="#request.sexTypes" listKey="value" listValue="name" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">机构</td>
						    <td>
						    	<s:select name="branchNo" id="branchNo" list="#request.branches" listKey="key" listValue="value" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">职位</td>
						    <td>
						    	<s:select name="position" id="position" list="#request.positionTypes" listKey="key" listValue="value" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">入职日期</td>
						    <td>
						    	<s:textfield name="hireDate" id="hireDate" maxlength="8" class="{required:true}" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">证件号码</td>
						    <td>
						    <s:textfield name="idCard"  id="idCard" class="{digitOrLetter:true}" maxlength="20"/>
						    	<span class="field_tipinfo">请输入正确格式的证件号码</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">出生日期</td>
						    <td>
						    <s:textfield name="birthday"  id="birthday" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">手机号</td>
						    <td>
						    <s:textfield name="tel"  id="tel" class="{required:true,digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">请输入正确的手机号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备用手机号</td>
						    <td>
						    <s:textfield name="spareTel"  id="spareTel" class="{digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">请输入正确的手机号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">籍贯</td>
						    <td>
						    <s:textfield name="nativePlace"  id="nativePlace" class="{required:true}" maxlength="25"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">现居住地址</td>
						    <td>
						    <s:textfield name="livePlace"  id="livePlace" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">紧急联系人</td>
						    <td>
						    <s:textfield name="instancyPerson"  id="instancyPerson" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">紧急联系人电话</td>
						    <td>
						    <s:textfield name="instancyTel"  id="instancyTel" class="{required:true,digit:true}" maxlength="11"/>
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
						    <td class="formlabel">QQ</td>
						    <td>
						    <s:textfield name="qq"  id="qq" class="{digits:true}" />
						    	<span class="field_tipinfo">请输入正确格式的QQ号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">微博ID</td>
						    <td>
						    <s:textfield name="weibo" id="weibo" maxlength="25"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">兴趣爱好</td>
						    <td>
						    <s:textfield name="hobby"  id="hobby" maxlength="25"/>
						    	<span class="field_tipinfo"></span>
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
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
