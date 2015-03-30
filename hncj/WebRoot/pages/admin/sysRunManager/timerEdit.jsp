<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/common.js" />
		<f:css href="/css/page.css" />

		<style type="text/css">
</style>

		<script type="text/javascript">
	$(function() {
		$('#btnReturn').click(function() {
			window.location.href = CONTEXT_PATH + "/timer.do?action=query";
		});
	});
	function save() {
		FormUtils.submitFirstTokenForm();
	}
</script>
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/timer.do?action=doEdit" method="post" styleClass="validate">
		<html:hidden property="id" />
			<!-- 用户资料修改区 -->
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">
						<table class="form_grid" border="0" cellspacing="3"
							cellpadding="0">
							<caption>
								${ACT.name}
							</caption>
							<tr>
								<td class="formlabel nes">
									beanName
								</td>
								<td>
									<html:text property="beanName" styleId="beanName"
										maxlength="20" styleClass="{required:true,letter:true}"></html:text>
									<span class="field_tipinfo">填写正确的beanName</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									中文名
								</td>
								<td>
									<html:text property="beanNameCh" styleId="beanNameCh"
										maxlength="20" styleClass="{required:true}"></html:text>
									<span class="field_tipinfo">不能为空</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									参数1
								</td>
								<td>
									<html:text property="para1" styleId="para2" maxlength="20"
										styleClass="{required:true}"></html:text>
									<span class="field_tipinfo">不能为空</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									参数2
								</td>
								<td>
									<html:text property="para2" styleId="para2" maxlength="20"
										styleClass="{required:true}"></html:text>
									<span class="field_tipinfo">不能为空</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel nes">
									触发时间
								</td>
								<td>
									<html:text property="triggertime" styleId="triggertime"
										maxlength="6" styleClass="{required:true}"
										onclick="WdatePicker({dateFmt:'HHmmss'})"></html:text>
									<span class="field_tipinfo">格式 hhmmss</span>
								</td>
							</tr>
							<tr>
								<td class="formlabel">
									备注
								</td>
								<td>
									<html:text property="remark" styleId="remark"></html:text>
								</td>
							</tr>
						</table>
						<div class="btnbox">
							<input type="button" id="btnSumit" value="保存" onclick="save()" />
							<input type="button" id="btnReturn" value="取消"
								onclick="gotoUrl('/timer.do?action=query')" />
						</div>
					</div>
					<b class="b4"></b>
					<b class="b3"></b>
					<b class="b2"></b>
					<b class="b1"></b>
				</div>
			</div>
		</html:form>
	</body>
</html>
