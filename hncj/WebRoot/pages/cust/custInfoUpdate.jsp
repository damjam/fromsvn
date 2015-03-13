<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title>修改客户信息</title>

		<f:css href="/css/page.css" />
		<f:js src="/dtree/wtree.js" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/popUp.js" />
		<style type="text/css">
html {
	overflow-y: scroll;
}
</style>

<script type="text/javascript" src="/js/datepicker/lang/zh-cn.js"></script>
	<script type="text/javascript">
	jQuery(function() {
		jQuery('btnUpdate').click(function() {
			var flag = true;
			if(flag) {
				FormUtils.submitFirstTokenForm();				
			}
		});
		jQuery('btnCancel').click(function() {
			
		});
		jQuery('#btnReturn').click(function() {
			var url = "/custInfoAction.do?action=viewCustInfo";
			gotoUrl(url);
		});
	});
</script>
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="custInfoAction.do?action=doUpdateInfo" method="post" styleClass="validate">
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3"
						cellpadding="0">
						<caption>
							${ACT.name}
						</caption>
						<tr>
							<td class="formlabel nes" align="right">
								用户名
							</td>
							<td>
								<html:text property="name" styleClass="{required:true}"
									maxlength="200" />
								<span class="field_tipinfo">用户名</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel nes" align="right">
								性别
							</td>
							<td>
								<html:select property="sex" styleClass="{required:false}" >
									<html:option value="">---请选择---</html:option>
									<html:options collection="sexVal" labelProperty="name" property="value" />									
								</html:select>
								<span class="field_tipinfo">性别</span>
							</td>
						</tr>
						<tr>
					   		<td class="formlabel nes">出生日期</td>
					   		<td>
					   			<html:text property="birthday" styleClass="{required:false}" />
<%--								<input name="birthday" id="birthday" class="Wdate" type="text" onclick="WdatePicker()" --%>
<%--											onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />--%>
					   			<span class="field_tipinfo">出生日期</span>
					   		</td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">证件类型</td>
						    <td>
						    	<html:select property="cardType" styleClass="{required:true}" >
							    		<html:option value="">---请选择---</html:option>
										<html:options collection="cardTypes" labelProperty="name" property="value" />
<%--									<html:options collection="cardTypes" labelProperty="name" property="value"  />--%>
								</html:select>
						    </td>
					   </tr>
						<tr>
					   		<td class="formlabel nes">证件号码</td>
					   		<td><html:text property="idCard"  styleClass="{required:true,stringNum:true,minlength:10}" maxlength="20"/>
					   			<span class="field_tipinfo">必填</span>
					   		</td>
					   </tr>
						<tr>
							<td class="formlabel nes" align="right">
								地址
							</td>
							<td>
								<html:text property="addr" styleClass="{required:true}"
									maxlength="200" />
								<span class="field_tipinfo">您当前所居住地</span>
							</td>
						</tr>
						<tr align="center">
							<td></td>
							<td align="left">
								<input type="button" id="btnUpdate" value="更新" />
								&emsp;
								<input type="button" id="btnCancel" value="取消" />
								&emsp;
								<input type="button" id="btnReturn" value="返回" />
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
		</html:form>
		<!--版权区域-->
		<div class="bottom">
			<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
		</div>
	</body>
</html>
