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
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<style type="text/css">

</style>


	</head>

	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form styleId="timerDo" action="/timerDo.do?action=list" method="post">
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
							<html:text property="beanName" styleId="beanName" maxlength="20" />
						</td>
						<td class="formlabel">
							beanName中文
						</td>
						<td>
							<html:text property="beanNameCh" styleId="beanNameCh"
								maxlength="20" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td height="30" colspan="5" nowrap="nowrap">
							<input type="submit" value="查询" id="input_btn2" />&nbsp;
							<input type="button" value="重置" id="btnClear"
								onclick="FormUtils.reset('timerDo');" />
						</td>
					</tr>
				</table>
			</div>
			<b class="b4"></b>
			<b class="b3"></b>
			<b class="b2"></b>
			<b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class='data_grid' width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<thead>
						<tr align="center" class="titlebg">
							<td>
								类名
							</td>
							<td>
								类中文
							</td>
							<td>
								状态
							</td>
							<td>
								参数1
							</td>
							<td>
								参数2
							</td>
							<td>
								触发时间
							</td>
							<td>
								备注
							</td>
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
									<f:state className="ExecuteState" value="${element.state }" />
								</td>
								<td>
									${element.para1}
								</td>
								<td>
									${element.para2}
								</td>
								<td>
									<fmt:parseDate
										value="${element.triggerDate}${element.triggerTime}"
										pattern="yyyyMMddHHmmss" var="triggerTime"></fmt:parseDate>
									<fmt:formatDate value="${triggerTime}"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									${element.remark }
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
