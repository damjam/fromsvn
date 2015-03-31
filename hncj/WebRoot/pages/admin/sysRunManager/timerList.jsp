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
		<f:js src="/js/common.js" />
		<f:css href="/css/page.css" />
		<f:css href="/css/jquery-ui-1.7.2.custom.css" />

		
</style>

		<script type="text/javascript">
	$(function() {
		//��ʱ�ƻ�����
		$('#btnTimerAdd').click(
			function() {
				var url="/timer.do?action=toEdit";
				gotoUrl(url);
			});

		//��ʱ�ƻ����
		$('#btnTimerClear').click(function() {
			FormUtils.reset();
		});
	});
</script>
	</head>

	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form styleId="timer" action="timer.do?action=query" method="post">
		<div class="userbox">
			<b class="b1"></b>
			<b class="b2"></b>
			<b class="b3"></b>
			<b class="b4"></b>
			<div class="contentb">
				<table class="form_grid" width="100%" border="0" cellspacing="3"
					cellpadding="0">
					<caption>
						${ACT.name}
					</caption>
					<tr>
						<td class="formlabel">
							beanName
						</td>
						<td>
							<html:text property="beanName" maxlength="20"></html:text>
						</td>
						<td class="formlabel">
							beanName������
						</td>
						<td>
							<html:text property="beanNameCh" maxlength="20"></html:text>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="5" nowrap="nowrap">
							<input type="submit" value="��ѯ" id="input_btn2" />&nbsp;
							<input type="button" value="����" id="btnClear"
								onclick="FormUtils.reset('timer');" />&nbsp;
							<input id="btnTimerAdd" type="button" value="����" />
						</td>
					</tr>
				</table>
			</div>
			<b class="b4"></b>
			<b class="b3"></b>
			<b class="b2"></b>
			<b class="b1"></b>
			<!-- �����б��� -->
		</div>
			<div class="tablebox">
				<table class="data_grid" width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<thead>
						<tr align="center" class="titlebg">
							<th align="center" nowrap="nowrap" class="titlebg">
								bean
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								bean������
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								����1
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								����2
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								����ʱ��
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								��ע
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								����
							</th>
						</tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>
									${element.beanName }
								</td>
								<td>
									${element.beanNameCh }
								</td>
								<td>
									${element.para1}
								</td>
								<td>
									${element.para2}
								</td>
								<td>
									${element.triggertime}
								</td>
								<td>
									${element.remark}
								</td>
								<td>
									<span class="redlink"> <a
										href="timer.do?action=toEdit&id=${element.id}">�޸�</a> <a
										onclick="return confirm('ȷ��ɾ��?');"
										href="timer.do?action=delete&id=${element.id }">ɾ��</a> </span>
								</td>
							</tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate />
			</div>
			</html:form>
	</body>
</html>