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
		<title>新增字典</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("sysDictForm");
				});
				
				$('#btnReturn').click(function(){
					gotoUrl("/sysDictAction.do?action=listSysDict");
				});
				
			});
		 
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="/sysDictAction.do?action=addSysDict" styleId="sysDictForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="40%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">值</td>
						    <td>
						    	<html:text property="dictValue"  styleId="dictId" styleClass="{required:true,stringNum:true}" maxlength="20"/>
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">名称</td>
						    <td>
						    	<html:text property="dictName" styleId="dictName"  styleClass="{required:true}" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">类型</td>
						     <td>
								<html:select property="dictType" styleClass="{required:true}">
									<html:option value="">---请选择---</html:option>
									<html:options collection="dictTypes" labelProperty="name" property="value" />
								</html:select>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">显示</td>
						     <td>
								<html:select property="display" styleClass="{required:true}">
									<html:option value="">---请选择---</html:option>
									<html:option value="Y">是</html:option>
									<html:option value="N">否</html:option>
								</html:select>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					    <tr>
						     <td align="right">备注</td>
						     <td>
								<html:text property="remark" maxlength="100"></html:text>
								<span class="field_tipinfo"></span>
							</td>
					   </tr>
					     <tr>
						     
						     <td colspan="2" align="center">
								 <input type="submit" id="btnSumit" value="提交"/>
								 <input type="button" id="btnClear" value="清除"/>
								 <input type="button" id="btnReturn" value="返回"/>
							</td>
					   </tr>
				  </table>
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
